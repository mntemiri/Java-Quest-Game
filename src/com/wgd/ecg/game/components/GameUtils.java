package com.wgd.ecg.game.components;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameObject;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

/**
 * Singleton utilities class, used to help with development of game by simplifying the calculations
 */
public class GameUtils {

    private static GameUtils instance = new GameUtils();
    private static Player player;

    /**
     * Singleton constructor
     */
    private GameUtils() {
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
     * Takes in a collider and sees if its colliding with anything
     * @param collider What you want to check against
     * @return ArrayList of what its colliding with, entity only, no collision rect
     */
    public static ArrayList<GameSprite> checkCollisionAllGameSprites(GameSprite collider) {
        ArrayList<GameSprite> colliding = new ArrayList<>();

        FloatRect collisionRect;
        for (GameObject entity : GameEngine.getDrawList()) { // For each GameObject in scene
            if(entity instanceof GameSprite && entity.ID != collider.ID) {
                collisionRect = collider.getBoundingBox().intersection(((GameSprite) entity).getBoundingBox()); // Finds intersection with all sprites
                if(collisionRect != null)
                    colliding.add((GameSprite) entity);
            }
        }

        return colliding;
    }

    /**
     * Checks if two GameSprites are colliding.
     * @param collider One of the colliders to check against
     * @param colliding Other collider to check against
     * @return if they are colliding
     */
    public static boolean checkCollisionWithGameSprite(GameSprite collider, GameSprite colliding) {
        if (collider.getBoundingBox().intersection(colliding.getBoundingBox()) != null)
            return true;
        else
            return false;
    }

    /**
     * Checks if collider is colliding with player
     * @param collider The collider that is being checked against player
     * @return If they are colliding
     */
    public static boolean isCollidingWithPlayer(GameSprite collider) {
        FloatRect collisionRect;
        if (player == null) {
            for (GameObject entity : GameEngine.getDrawList()) { // For each GameObject in scene
                if (entity instanceof Player && entity.ID != collider.ID) {
                    player = (Player) entity;
                    collisionRect = collider.getBoundingBox().intersection(((GameSprite) entity).getBoundingBox()); // Finds intersection with all sprites
                    if (collisionRect != null)
                        return true;
                    else
                        return false;
                }
            }
        } else {
            collisionRect = collider.getBoundingBox().intersection(player.getBoundingBox()); // Finds intersection with all sprites
            if (collisionRect != null)
                return true;
            else
                return false;
        }

        return false;
    }

    /**
     * Returns vector magnitude (Distance) between player and collider
     * @param collider What to check the distance against
     * @return distance between them
     */
    public static float distanceFromPlayer(GameSprite collider) {
        if (player == null) {
            for (GameObject entity : GameEngine.getDrawList()) { // For each GameObject in scene
                if (entity instanceof Player && entity.ID != collider.ID) {
                    player = (Player) entity;
                    return findMagnitude(Vector2f.sub(((Player) entity).getPosition(), collider.getPosition()));
                }
            }
        } else {
            return findMagnitude(Vector2f.sub(player.getPosition(), collider.getPosition()));
        }
        return Float.NaN;
    }

    /**
     * Returns player position
     * @return the players position
     */
    public static Vector2f playerPosition() {
        if (player == null) {
            for (GameObject entity : GameEngine.getDrawList()) { // For each GameObject in scene
                if (entity instanceof Player) {
                    player = (Player) entity;
                    return ((Player) entity).getPosition();
                }
            }
        } else {
            return player.getPosition();
        }
        return null;
    }

    public static boolean isMouseColliding(FloatRect collider) {
        if (collider.intersection(new FloatRect(GameEngine.getMousePosition().x, GameEngine.getMousePosition().y, 1, 1)) != null) {
            return true;
        } else {
            return false;
        }
    }

}
