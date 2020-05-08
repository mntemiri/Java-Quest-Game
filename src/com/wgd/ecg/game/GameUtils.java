package com.wgd.ecg.game;

import com.wgd.ecg.engine.*;
import com.wgd.ecg.game.components.UI.Pause_DeathOverlay;
import com.wgd.ecg.game.components.player.DamageSystem;
import com.wgd.ecg.game.components.player.PlayerStatSystem;
import com.wgd.ecg.game.gameobjects.Castle;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.House;
import com.wgd.ecg.game.gameobjects.entities.heroes.OldMan;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.Random;

/**
 * Singleton utilities class, used to help with development of game by simplifying the calculations
 */
public class GameUtils {

    private static GameUtils instance = new GameUtils();
    private static Player player;
    private static Castle[] castles;
    private static String difficulty = "Medium";

    private static int seed = Integer.MIN_VALUE;

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

    public static void setPlayer(Player player) {
        GameUtils.player = player;
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

    public static Player getPlayer(){
        return player;
    }

    public static boolean isMouseColliding(FloatRect collider) {
        if (collider.intersection(new FloatRect(GameEngine.getMousePosition().x, GameEngine.getMousePosition().y, 1, 1)) != null) {
            return true;
        } else {
            return false;
        }
    }

    //public static Vector2f posPlayer() { return player.getPosition(); }     //get the players position

    /**
     * Returns the number of degrees between two vectors
     * @param vector1 The first vector to compare
     * @param vector2 The second vector to compare
     * @return The number of degrees between the two vectors in degrees
     */
    public static double getAngleBetween(Vector2f vector1, Vector2f vector2){
        //Some magic to get the angle of the vector direction
        Vector2f direction = Entity.normaliseVector(Vector2f.sub(vector1, vector2));
        Vector2f normal = Entity.normaliseVector(new Vector2f(0, 1 ));

        //Some magic to get the angle of the vector direction
        double dot = direction.x*normal.x + direction.y*normal.y;     // dot product
        double det = direction.x * normal.y - direction.y*normal.x;      // determinant
        double angle = Math.toDegrees(Math.atan2(det, dot));  // atan2(y, x) or atan2(sin, cos)

        if(angle<0){
            angle = 360+angle;
        }
        return angle;
    }

    /**
     * Returns the angle the mouse is at in relation to the players position
     * @return The angle in degrees
     */
    public static double getMouseAngle(){
        //Some vector magic
        Vector2f mousePos = GameEngine.getMousePosition();
        Vector2f playerPos = GameUtils.playerPosition();
        assert playerPos != null;

        //Some magic to get the angle of the vector direction
        Vector2f direction = Entity.normaliseVector(Vector2f.sub(mousePos, playerPos));
        Vector2f normal = Entity.normaliseVector(new Vector2f(0, 1 ));

        //Some magic to get the angle of the vector direction
        double dot = direction.x*normal.x + direction.y*normal.y;     // dot product
        double det = direction.x * normal.y - direction.y*normal.x;      // determinant
        double angle = Math.toDegrees(Math.atan2(det, dot));  // atan2(y, x) or atan2(sin, cos)

        if(angle<0){
            angle = 360+angle;
        }
        return angle;
    }

    /**
     * Called upon the player's health reaching 0
     */
    public static void death(){
        GameEngine.setTimeScale(0);
        PlayerStateManager.deathState();
        Pause_DeathOverlay.clearMenu();
        Pause_DeathOverlay.ShowMenu();
        System.out.println("You have died");
    }

    /**
     * Called on level load, updates the cached Player
     */
    public static void updatePlayer(){
        for (GameObject entity : GameEngine.getDrawList()) { // For each GameObject in scene
            if (entity instanceof Player) {
                player = (Player) entity;
            }
        }
    }

    public static void printPos(){
        System.out.println("out of cave " +player.getPosition().x + "     " + player.getPosition().y );
    }

    public static void buildSpawn(){
        GameEngine.add(new OldMan(10f, 10f));
        GameEngine.add(new House(-200f, 10f, 8f, 8f));
    }

    public static void removePlayer(){
        player = null;
    }

    public static void giveCastles(Castle[] c) {
        castles = c;
    }

    public static void setDifficulty(String temp) {
        switch(temp) {
            case "Easy":
                difficulty = "Easy";
                break;
            case "Medium":
                difficulty = "Medium";
                break;
            case "Hard":
                difficulty = "Hard";
                break;
        }
    }

    public static double getDifficultyModifier() {
        switch(difficulty) {
            case "Easy":
                return 0.5;
            case "Medium":
                return 1;
            case "Hard":
                return 2;
        }

        return 1;
    }

    public static int generateSeed() {
        if(seed == Integer.MIN_VALUE) {
            seed = new Random().nextInt(1000000);
            return seed;
        } else {
            return seed;
        }
    }

    public static void resetSeed() {
        seed = Integer.MIN_VALUE;
    }
}
