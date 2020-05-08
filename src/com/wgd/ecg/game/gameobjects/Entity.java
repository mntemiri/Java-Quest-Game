package com.wgd.ecg.game.gameobjects;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.components.Collider;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * Entity class used for objects that have collision and physics, extend upon this.
 */
public class Entity extends GameSprite {
    public Collider collider; // Instances collider class for collisions
    protected float mass; // Mass of entity
    protected Vector2f velocity; // Velocity vector of entity
    protected Vector2f accelerationVector; // Acceleration vector of entity
    protected Vector2f frictionVector; // Friction vector of entity

    private boolean isMaxVelocity; // Sees if object has maximum velocity

    private float acceleration; // Acceleration magnitude of vector
    protected float friction; // Friction magnitude of vector
    private float maxVelocity; // Max velocity value of entity, if boolean is enabled

    private float minMultipliedDelta; // Value used to multiply delta times'd, to create larger units of distance on force.

    float accumulator = 0f;


    /**
     * Constructor used to pass in coords and scale of image.
     * @param spriteTexture type name of texture in res folder
     * @param x x position of sprite in world space
     * @param y y position of sprite in world space
     * @param scaleX scale of image on x axis
     * @param scaleY scale of image on y axis
     */
    public Entity(String spriteTexture, float x, float y, float scaleX, float scaleY) {
        super(spriteTexture, x, y, scaleX, scaleY);
        collider = new Collider(this, sprite);

        mass = 5f;
        acceleration = 0f;
        friction = 1f;

        velocity = Vector2f.ZERO;
        accelerationVector = Vector2f.ZERO;
        frictionVector = Vector2f.ZERO;

        isMaxVelocity = false;
        maxVelocity = 0;

        minMultipliedDelta = 1000f;
    }

    public Entity(Texture spriteTexture, float x, float y, float scaleX, float scaleY) {
        super(spriteTexture, x, y, scaleX, scaleY);
        collider = new Collider(this, sprite);

        mass = 5f;
        acceleration = 0f;
        friction = 1f;

        velocity = Vector2f.ZERO;
        accelerationVector = Vector2f.ZERO;
        frictionVector = Vector2f.ZERO;

        isMaxVelocity = false;
        maxVelocity = 0;

        minMultipliedDelta = 1000f;
    }

    public Entity(Texture spriteTexture, float x, float y, float rotation) {
        super(spriteTexture, x, y, 1, 1);
        collider = new Collider(this, sprite);

        setRotation(rotation);


        mass = 1000000f;
        acceleration = 0f;
        friction = 1f;

        velocity = Vector2f.ZERO;
        accelerationVector = Vector2f.ZERO;
        frictionVector = Vector2f.ZERO;

        isMaxVelocity = false;
        maxVelocity = 0;

        minMultipliedDelta = 1000f;
    }

    public Entity(String spriteTexture, float x, float y, float scaleX, float scaleY , int columnCount, int rowCount, float animationSpeedInSeconds){
        super(spriteTexture, x, y, scaleX, scaleY, columnCount, rowCount, animationSpeedInSeconds);
        collider = new Collider(this, sprite);

        mass = 5f;
        acceleration = 0f;
        friction = 1f;

        velocity = Vector2f.ZERO;
        accelerationVector = Vector2f.ZERO;
        frictionVector = Vector2f.ZERO;

        isMaxVelocity = false;
        maxVelocity = 0;

        minMultipliedDelta = 1000f;
    }

    public Entity(Texture spriteTexture, float x, float y, float scaleX, float scaleY , int columnCount, int rowCount, float animationSpeedInSeconds){
        super(spriteTexture, x, y, scaleX, scaleY, columnCount, rowCount, animationSpeedInSeconds);
        collider = new Collider(this, sprite);

        mass = 5f;
        acceleration = 0f;
        friction = 1f;

        velocity = Vector2f.ZERO;
        accelerationVector = Vector2f.ZERO;
        frictionVector = Vector2f.ZERO;

        isMaxVelocity = false;
        maxVelocity = 0;

        minMultipliedDelta = 1000f;
    }

    /**
     * @param vel If set below 0 then maxVelocity disabled, if above that will be the new max velocity
     */
    public void setMaxVelocity(float vel) {
        if(vel < 0)
            isMaxVelocity = false;
        else
            isMaxVelocity = true;
        maxVelocity = vel;
    }



    /**
     * Finds the magnitude of a vector
     * @param vector to find the magnitude from
     * @return the magnitude
     */
    public static float findMagnitude(Vector2f vector) {
        return (float)Math.sqrt((Math.pow(vector.x, 2) + (Math.pow(vector.y, 2))));
    }

    /**
     * normalises a vector in order to get its direction
     * @param vector to normalise
     * @return the normalised vector
     */
    public static Vector2f normaliseVector(Vector2f vector) {
        float magnitudeVelocity = findMagnitude(vector);
        if(magnitudeVelocity > 0)
            return Vector2f.div(vector, magnitudeVelocity);
        else
            return Vector2f.ZERO;
    }

    /**
     * adds force to the entity by using a directional vector and a float value for its magnitude
     * @param direction
     * @param magnitude
     */
    public void addForce(Vector2f direction, float magnitude) {
        acceleration = ((magnitude / mass)) * GameEngine.deltaTime;
        accelerationVector = Vector2f.mul(normaliseVector(direction), acceleration);
    }

    /**
     * updates the physics component of entity every frame
     */
//    protected void physicsUpdate() {
//        //Normalises the velocity vector
//        Vector2f normalisedVelocity;
//        normalisedVelocity = normaliseVector(velocity);
//
//        //multiplies velocity normal with negated friction, to create friction vector
//        frictionVector = Vector2f.mul(normalisedVelocity, (-friction) * GameEngine.deltaTime);
//
//        velocity = Vector2f.add(velocity, accelerationVector);
//
//        if (findMagnitude(frictionVector) * GameEngine.deltaTime > findMagnitude(velocity) * GameEngine.deltaTime)
//            velocity = Vector2f.ZERO;
//        else
//            velocity = Vector2f.add(velocity, frictionVector);
//
////        System.out.println((findMagnitude(velocity)* GameEngine.deltaTime) + " " + (maxVelocity * GameEngine.deltaTime));
//
//        sprite.move(Vector2f.mul(velocity, GameEngine.deltaTime * minMultipliedDelta));
//
//        if(findMagnitude(velocity) > maxVelocity && isMaxVelocity) velocity = Vector2f.mul(normalisedVelocity, maxVelocity);
//
////        if(findMagnitude(velocity) > maxVelocity && isMaxVelocity) velocity = Vector2f.mul(normalisedVelocity, maxVelocity);
//
//        if(acceleration > friction)
//            accelerationVector = Vector2f.sub(accelerationVector, Vector2f.mul(normaliseVector(velocity), (friction) * GameEngine.deltaTime));
//        else
//            accelerationVector = Vector2f.ZERO;
//
////        acceleration = findMagnitude(accelerationVector);
//    }

//    private void physicsUpdate() {
//        //Normalises the velocity vector
//        Vector2f normalisedVelocity;
//        normalisedVelocity = normaliseVector(velocity);
//
//        frictionVector = Vector2f.mul(normalisedVelocity, -friction * GameEngine.deltaTime);
//
//        velocity = Vector2f.add(velocity, accelerationVector);
//
//
//        if (findMagnitude(frictionVector) * GameEngine.deltaTime > findMagnitude(velocity) * GameEngine.deltaTime)
//            velocity = Vector2f.ZERO;
//        else
//            velocity = Vector2f.add(velocity, frictionVector);
//
//        if (findMagnitude(velocity) > maxVelocity) velocity = Vector2f.mul(normaliseVector(velocity), maxVelocity);
//
//        System.out.println(velocity);
//
//        sprite.move(Vector2f.mul(velocity, GameEngine.deltaTime));
//
//        acceleration = findMagnitude(accelerationVector);
//    }

    protected void physicsUpdate() {
        //Normalises the velocity vector
        Vector2f normalisedVelocity;
        normalisedVelocity = normaliseVector(velocity);

        //multiplies velocity normal with negated friction, to create friction vector
        frictionVector = Vector2f.mul(normalisedVelocity, (-friction) * GameEngine.deltaTime);

        velocity = Vector2f.add(velocity, accelerationVector);

        if (findMagnitude(frictionVector) * GameEngine.deltaTime > findMagnitude(velocity) * GameEngine.deltaTime)
            velocity = Vector2f.ZERO;
        else
            velocity = Vector2f.add(velocity, frictionVector);

//        System.out.println((findMagnitude(velocity)* GameEngine.deltaTime) + " " + (maxVelocity * GameEngine.deltaTime));

        sprite.move(Vector2f.mul(velocity, GameEngine.deltaTime * minMultipliedDelta));

        if(findMagnitude(velocity) > maxVelocity && isMaxVelocity) velocity = Vector2f.mul(normalisedVelocity, maxVelocity);

//        if(findMagnitude(velocity) > maxVelocity && isMaxVelocity) velocity = Vector2f.mul(normalisedVelocity, maxVelocity);

        if(acceleration > friction)
            accelerationVector = Vector2f.sub(accelerationVector, Vector2f.mul(normaliseVector(velocity), (friction) * GameEngine.deltaTime));
        else
            accelerationVector = Vector2f.ZERO;

//        acceleration = findMagnitude(accelerationVector);
    }

    /**
     * gets mass of entity
     * @return mass
     */
    public float getMass() {
        return mass;
    }

    /**
     * sets mass of entity
     * @param mass
     */
    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public void setRotation(float rotation) {
        this.sprite.setRotation(rotation);
    }

    public void OnCollision(Entity collider, Entity colliding, FloatRect colliderRect) {

    }

    /**
     * runs every frame
     * @param window used to draw sprite
     */
    @Override
    public void update(RenderWindow window) {
        super.update(window);
        collider.updateCollider(sprite);
        collider.isCollidingApplyForce(this);
        physicsUpdate();
    }

    public void setPosition (float x, float y){
        sprite.setPosition(x, y);
    }
}
