package com.wgd.ecg.game.components;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameObject;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * Collider used to calcaulate if Entities are touching and apply force back if so.
 */
public class Collider {
    Entity gameEntity;
    FloatRect colliderRect;
    public boolean skipPhysics = false;
    private boolean isCollisionPoint = false;

    /**
     * Sets collider to size of sprite and position and width
     * @param sprite
     */
    public Collider(Entity gameEntity, Sprite sprite) {
        this.gameEntity = gameEntity;
        colliderRect = new FloatRect(sprite.getGlobalBounds().left, sprite.getGlobalBounds().top, sprite.getGlobalBounds().width, sprite.getGlobalBounds().height);
    }

    /**
     * Manually set collision box scale
     * @param w
     * @param h
     */
    public Collider(float w, float h) {
        colliderRect = new FloatRect(0, 0, w, h);
    }

    /**
     * Manually set collision box scale and position
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public Collider(float x, float y, float w, float h) {
        colliderRect = new FloatRect(x, y, w, h);
    }

    /**
     * updates colliders scale, position, and shape
     * @param sprite
     */
    public void updateCollider(Sprite sprite) {
        colliderRect = new FloatRect(sprite.getGlobalBounds().left, sprite.getGlobalBounds().top, sprite.getGlobalBounds().width, sprite.getGlobalBounds().height);
    }

    public void OnCollision(Entity collider, Entity colliding, FloatRect colliderRect) {
        gameEntity.OnCollision(collider, colliding, colliderRect);
    }

    public double vectorAngle(Vector2f direction, Vector2f normal) {
        double dot = direction.x*normal.x + direction.y*normal.y;     // dot product
        double det = direction.x * normal.y - direction.y*normal.x;      // determinant
        double angle = Math.toDegrees(Math.atan2(det, dot));  // atan2(y, x) or atan2(sin, cos)

        return angle;
    }

    public boolean isCollisionPoint() {
        return isCollisionPoint;
    }

    public void setCollisionPoint(boolean collisionPoint) {
        isCollisionPoint = collisionPoint;
    }

    /**
     * checks if colliding with an entity, then applies force on object to repel it
     * @param collider
     */
    public void isCollidingApplyForce(Entity collider) {
        FloatRect collisionRect;
        for (GameObject entity : GameEngine.getDrawList()) { // For each GameObject in scene
            if (entity instanceof Entity && entity.ID != collider.ID) { // Check if object is itself, if not check if is Entity.
                collisionRect = colliderRect.intersection((((Entity) entity).collider.colliderRect)); //
                if (collisionRect != null) {
                    OnCollision(collider, (Entity) entity, colliderRect); // Updates function that's overridable

                    if(skipPhysics) { skipPhysics = false; continue; }

                    Vector2f normalisedDir = Entity.normaliseVector(Vector2f.sub(collider.sprite.getPosition(), ((Entity) entity).sprite.getPosition()));

                    // Not accurate form of the physics with some bullshit constants, but a lot cheaper calculations wise
                    if(isCollisionPoint) {
                        collider.addForce(normalisedDir, (((((Entity) entity).getAcceleration()) + 0.001f * ((Entity) entity).getMass()) + 100f)); // Adds force of colliding objects mass * acceleration, with some extra permanent force to push static objects away.
                    } else {
                        Vector2f collisionRectCenter = new Vector2f(collisionRect.left + (collisionRect.width / 2), collisionRect.top + (collisionRect.height / 2));

//                        if (collider instanceof Player)
//                            System.out.println(Entity.normaliseVector(Vector2f.sub(collider.getPosition(), collisionRectCenter)));
                        collider.addForce(Entity.normaliseVector(Vector2f.sub(collider.getPosition(), collisionRectCenter)), (((((Entity) entity).getAcceleration()) + 0.001f * ((Entity) entity).getMass()) + 100f));
                    }
                    // Forces sprite to move without consideration to physics, will screw with physics a bit but actually improves upon the effect overall
                    // Divided the size of the collision rect by collision divided by collision
                    // COULD CAUSE PROBLEMS WITH HIGH MASS OBJECTS MOVING WHEN THEY SHOULDN'T LATER ON
//                    ((Entity) entity).sprite.move(Vector2f.mul(normalisedDir,  -Entity.findMagnitude(Entity.normaliseVector(new Vector2f(collisionRect.width, collisionRect.height))) / (((Entity) entity).getMass()) / ((Entity) entity).getMass()));
//
//                    // A LOT MORE ACCURATE SIMULATION OF FORCES IN COLLISIONS, BUT MORE COSTLY
//                    float velocitySqr = (float)Math.pow(GameUtils.findMagnitude(((Entity) entity).getVelocity()) * GameEngine.deltaTime, 2);
//                    float mass = ((Entity) entity).getMass();
//                    float distance = GameUtils.findMagnitude(normalisedDir);
//
////                       Some constants added to stop entity from being stuck inside other entity, cause no physical collision to allow for bounciness
//                    float output = ((0.5f * mass * (velocitySqr + 0.001f)) / distance) + 10f;
//
//                    collider.addForce(normalisedDir, output);
                    // END OF ACCURATE SIMULATION CODE
                }
            }
        }
    }
}
