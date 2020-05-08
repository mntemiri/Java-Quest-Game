package com.wgd.ecg.game.gameobjects.entities.monsters;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.components.*;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.Collectible;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.components.player.DamageSystem;
import com.wgd.ecg.game.gameobjects.Projectile;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.util.Random;

/**
 * This class creates the monster
 */
public class Monster extends Entity {
//    public static float spawnRate = 40f;
    public static int packSize = 2;
    private int damage;
    private float maxSpeed;
    private int sight; //Range how far the monster is supposed to see (detect the player). ~Simon
    private int range; //Range of hitting or shooting. ~Simon
    protected boolean alive;
    protected DamageSystem health;
    protected AI brain;
    private int cooldown = 2000;
    private int level;
    protected MobHealthBar healthBar = null;
    private boolean facingRight = true;
    private int startXPosition, startYPosition;
    private double lastHit;

    /**
     * Called upon the monster being damage in combat
     * @param projectile The projectile that hit the creature
     */
    public void damage(Projectile projectile){
        //I added the DamageSystem so the monsters damage is handled properly. ~Simon
        health.dealDamage(projectile.getDamage());

        if(health.getHealth() <= 0) {
            alive = false;
            die();
            if(healthBar != null){
                healthBar.remove();
            }
        }
    }

    /**
     * @param damage The damage to deal
     */
    public void damage(int damage){
        //I added the DamageSystem so the monsters damage is handled properly. ~Simon
        health.dealDamage(damage);

        if(health.getHealth() <= 0) {
            alive = false;
            die();
            if(healthBar != null){
                healthBar.remove();
            }
        }
    }

    public Monster(int maxHealth, int damage, float speed, int sight, int range , int cooldown, int xPos, int yPos, float scaleX, float scaleY, Texture type, boolean ranged){
        super(type, xPos, yPos, scaleX, scaleY);
        this.level = 1 + new Random().nextInt(10);
        this.damage = (int)(damage * (1 + level/10) * GameUtils.getDifficultyModifier());
        this.maxSpeed = (speed*0.01f) * 0.2f * (1 + level/10);
        this.sight = sight* (1 + level/10);
        this.range = range;
        this.cooldown = cooldown;
        health = new DamageSystem((int)(maxHealth * GameUtils.getDifficultyModifier()));
        brain = new AI(this);
        alive = true;
        setMass(70f);
        setFriction(1f);
        setMaxVelocity((float) maxSpeed);

        if(ranged){
            brain = new RangedAI(this);
        }

        startXPosition = xPos;

        startYPosition = yPos;
    }

    public Monster(int maxHealth, int damage, float speed, int sight, int range , int cooldown,int xPos, int yPos, float scaleX, float scaleY, String type, boolean ranged){
        super(type + ".png", xPos, yPos, scaleX, scaleY);
        this.level = 1 + new Random().nextInt(10);
        this.damage = (int)(damage * (1 + level/10) * GameUtils.getDifficultyModifier());
        this.maxSpeed = (speed*0.01f) * 0.2f * (1 + level/10);
        this.sight = sight* (1 + level/10);
        this.range = range;
        this.cooldown = cooldown;
        health = new DamageSystem((int)(maxHealth * GameUtils.getDifficultyModifier()));
        brain = new AI(this);
        alive = true;
        setMass(70f);
        setFriction(1f);
        setMaxVelocity((float) maxSpeed);

        if(ranged){
            brain = new RangedAI(this);
        }

        startXPosition = xPos;

        startYPosition = yPos;
    }

    public Monster(int maxHealth, int damage, float speed, int sight, int range , int cooldown, int xPos, int yPos, float scaleX, float scaleY, String type, boolean ranged, int columnCount, int rowCount, float animationSpeedInSeconds){
        super(type + ".png", xPos, yPos, scaleX, scaleY, columnCount, rowCount, animationSpeedInSeconds);
        this.level = 1 + new Random().nextInt(10);
        this.damage = (int)(damage * (1 + level/10) * GameUtils.getDifficultyModifier());
        this.maxSpeed = (speed*0.01f) * 0.2f * (1 + level/10);
        this.sight = sight* (1 + level/10);
        this.range = range;
        this.cooldown = cooldown;
        health = new DamageSystem((int)(maxHealth * GameUtils.getDifficultyModifier()));
        brain = new AI(this);
        alive = true;
        setMass(70f);
        setFriction(1f);
        setMaxVelocity((float) maxSpeed);

        if(ranged){
            brain = new RangedAI(this);
        }

        startXPosition = xPos;

        startYPosition = yPos;
    }

    public Monster(int maxHealth, int damage, float speed, int sight, int range , int cooldown, int xPos, int yPos, float scaleX, float scaleY, Texture type, boolean ranged, int columnCount, int rowCount, float animationSpeedInSeconds){
        super(type, xPos, yPos, scaleX, scaleY, columnCount, rowCount, animationSpeedInSeconds);
        this.level = 1 + new Random().nextInt(10);
        this.damage = (int)(damage * (1 + level/10) * GameUtils.getDifficultyModifier());
        this.maxSpeed = (speed*0.01f) * 0.2f * (1 + level/10);
        this.sight = sight* (1 + level/10);
        this.range = range;
        this.cooldown = cooldown;
        health = new DamageSystem((int)(maxHealth * GameUtils.getDifficultyModifier()));
        brain = new AI(this);
        alive = true;
        setMass(70f);
        setFriction(1f);
        setMaxVelocity((float) maxSpeed);

        if(ranged){
            brain = new RangedAI(this);
        }

        startXPosition = xPos;

        startYPosition = yPos;
    }

    /**
     * this method gets true if player is in sight
     * @return true if player is in sight
     */
    public int getSight() { return sight; }

    /**
     * this method gets true if player is in range
     * @return true if player is in range
     */
    public int getRange() { return range; }

    /**
     * This method returns the monster damage
     * @return the monster damage
     */
    public int getDamage() { return damage; }

    /**
     * This method returns the monster cooldown period
     * @return the monster cooldown period
     */
    public int getCooldown() { return cooldown; }

    //Called by health system on the death of the monster - this can be overrided to make different creatures drop different things
    public void die(){
        Audio.playSFX("bossDeath");
        GameEngine.remove(this);

        //Drop 25% of the time
        boolean drop = new Random().nextInt(5)>3;
        if(drop){
            //Select random potion
            String[] potions = {"health", "strength", "speed"};
            String type = potions[new Random().nextInt(2)];
            String texture = "potion" + type + ".png";

            //Add to monsters position
            Vector2f pos = sprite.getPosition();
            GameEngine.add(new Collectible(texture, type, pos.x, pos.y, 2, 2));
        }
        addXP();
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        collider.updateCollider(sprite);
        collider.isCollidingApplyForce(this);
        physicsUpdate();
        brain.brainUpdate();
        if(healthBar != null){
            healthBar.update();
        }
        Vector2f playerPos = GameUtils.playerPosition();
        if(playerPos != null){
            double xDistance = playerPos.x - getPosition().x;
            double yDistance = playerPos.y - getPosition().y;

            if(GameUtils.distanceFromPlayer(this) > 6000 ){
                System.out.println("Removing...");
                GameEngine.remove(this);
            }
        }
        if(GameClock.timeInMS() - lastHit > 200){
            sprite.setColor(new Color(255, 255, 255, 255));
        }
    }

    /**
     * This method is used to add xp tot he player
     */
    public void addXP(){
        int xp = (20*level);
        GameUtils.getPlayer().getStats().addXP(xp);
        System.out.println("Giving " + xp + " xp");
    }

    public float getXPos(){
        return this.sprite.getPosition().x;
    }

    public float getYPos(){
        return this.sprite.getPosition().y;
    }

    /**
     * Updates the monster's health bar. Called when monster is damaged.
     * Creates the healthbar on first call.
     */
    public void updateHealthBar(){
        if(healthBar == null){
            healthBar = new MobHealthBar(this);
        }else{
            healthBar.update();
        }
    }

    /**
     * Returns the current HP of the monster
     * @return The monster's current HP
     */
    public int getHP(){
        return this.health.getHealth();
    }

    /**
     * Returns the max HP of the monster
     * @return The monster's max HP
     */
    public int getMaxHP(){
        return this.health.getMaxHealth();
    }

    /**
     * Change the monster's brain/AI
     */
    public void setBrain(String type){
        if(type.equals("bossone")){
            brain = new BossOneAI(this);
        }
        else if(type.equals("bosstwo")) {
            brain = new BossTwoAI(this);
        }
        else if(type.equals("knight")) {
            brain = new KnightAI(this);
        }
        else if(type.equals("miniboss")) {
            brain = new MiniBossAI(this);
        }
        else if(type.equals("bossthree")) {
            brain = new BossThreeAI(this);
        }
    }

    public float getMaxSpeed(){
        return this.maxSpeed;
    }

    /**
     * Resests max speed to original value
     */
    public void resetSpeed(){
        setMaxVelocity(maxSpeed);
    }

    /**
     *
     * @return true if its facing right
     */
    public boolean facingRight(){
        return facingRight;
    }

    /**
     * Set it to face right
     * @param value
     */
    public void setFacingRight(boolean value){  facingRight = value;  }

    /**
     * returns the starting x pos
     * @return the starting x pos
     */
    public int getStartingXPosition() { return startXPosition;  }

    /**
     * returns the starting y pos
     * @return the starting y pos
     */
    public int getStartingYPosition() { return startYPosition;  }


    /**
     * keepscount of time to hit the player
     */
    public void hit(){
        lastHit = GameClock.timeInMS();
        sprite.setColor(new Color(255, 100, 100, 255));
    }

}
