package com.wgd.ecg.game.components;

import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;
import org.jsfml.system.Vector2f;

import java.util.Objects;
import java.util.Random;


/**
 * This class creates the AI
 */
public class AI {
    protected int range;
    protected int sight;
    protected boolean chasing = false;
    protected boolean wandering = true;
    public Monster host;
    protected Vector2f direction = GameUtils.normaliseVector(getRandomVector());
    protected float speed = 150f;
    protected double lastAttackTime = GameClock.timeInMS(), lastTick = GameClock.timeInMS();

    public AI(Monster host) {
        this.host = host;
        sight = host.getSight();
        range = host.getRange();
        this.host = host;
    }

    /**
     * This method decides whether the enemy chases the player or not
     */
    public void brainUpdate() {
        if(chasing) {
            if(inSight()) {
                if(inRange()) {
                    hitPlayer();
                }
                else {
                    chase(true);
                }
            }
            else {
                chase(false);
            }
        }
        else {
            if(inSight()) {
                chase(true);
            }
            else {
                wander();
            }
        }
    }

    /**
     * This method keeps track of the clock ticks
     * @return true or false if time >= 3000
     */

    private boolean tick() {
        if(GameClock.timeInMS() - lastTick >= 3000) {
           lastTick = GameClock.timeInMS();

           return true;
        }

        return false;
    }

    /**
     * Checks the distance of enemy form player
     * @return is its too far it returns false
     */
    protected boolean inRange() {
        if(GameUtils.distanceFromPlayer(host) < range) { return true; }

        return false;
    }


    /**
     * checks if the distance from player is in sight
     * @return true if it is
     */
    protected boolean inSight() {
//        System.out.println(GameUtils.distanceFromPlayer(host));
        if(GameUtils.distanceFromPlayer(host) < sight) { return true; }

        return false;
    }


    /**
     * This method makes sure that the enemy chases the player
     * @param in
     */
    protected void chase(boolean in) {
        try {
            direction = new Vector2f(GameUtils.playerPosition().x - host.getPosition().x, GameUtils.playerPosition().y - host.getPosition().y);
            direction = GameUtils.normaliseVector(direction);
            checkDirection(direction);
            host.addForce(direction, speed);
        }
        catch (Exception e) { ; }

        chasing = in;
        wandering = false;
    }


    /**
     * This method makes the enemy wander around the map and not attack
     */
    protected void wander() {
        if(!tick()) {
            host.addForce(direction, speed-10f);
            checkDirection(direction);
        }
        else {
            direction = GameUtils.normaliseVector(getRandomVector());
            host.addForce(direction, speed-10f);
            checkDirection(direction);
        }

        chasing = false;
        wandering = true;
    }

    /**
     * This method checks the position and decides on enemy attack
     */
    protected void hitPlayer(){
        checkDirection(direction);
        if(GameUtils.isCollidingWithPlayer(host)) {
            direction = new Vector2f(-(GameUtils.playerPosition().x - host.getPosition().x), (GameUtils.playerPosition().y - host.getPosition().y));
            direction = GameUtils.normaliseVector(direction);
            host.addForce(direction, speed+50f);

            //System.out.println("Too close for my taste... ~Monster");
        }

        if(GameClock.timeInMS() - lastAttackTime > host.getCooldown()) {
            GameUtils.getPlayer().health.dealDamage(host.getDamage());
            lastAttackTime = GameClock.timeInMS();
        }
    }


    /**
     * This is used to get the position of enemy
     * @return the position of enemy
     */
    protected Vector2f getRandomVector() {
        Random rand = new Random();

        int x = rand.nextInt(500) - rand.nextInt(1000);
        int y = rand.nextInt(500) - rand.nextInt(1000);

        return new Vector2f(x, y);
    }

    /**
     * This method checks the direction of enemy and player
     * @param direction
     */
    protected void checkDirection(Vector2f direction){
        if(direction.x < 0 && host.facingRight()){
            host.setFacingRight(false);
            host.sprite.scale(-1.f,1.f);
        }else if(direction.x > 0 && !host.facingRight()){
            host.setFacingRight(true);
            host.sprite.scale(-1.f,1.f);
        }
    }

    public void removeAll() {
    }

    /**
     * This method decides when the enemy should run away
     */
    protected void runAway() {
        try {
            direction = new Vector2f(-(Objects.requireNonNull(GameUtils.playerPosition()).x - host.getPosition().x), -(com.wgd.ecg.game.GameUtils.playerPosition().y - host.getPosition().y));
            direction = GameUtils.normaliseVector(direction);
            checkDirection(direction);
            host.addForce(direction, speed);
        }
        catch (Exception e) { ; }
    }
}
