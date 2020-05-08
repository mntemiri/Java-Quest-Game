package com.wgd.ecg.game.gameobjects;

import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class Projectile extends Entity{
    private int damage;
    private boolean evil;
    private boolean expires;
    private double timeToLive;
    private double timeAdded;

    /**
     * Place an evil projectile belonging to an enemy
     * @param spriteTexture
     * @param x
     * @param y
     * @param scaleX
     * @param scaleY
     * @param damage
     * @param evil
     */
    public Projectile(String spriteTexture, float x, float y, float scaleX, float scaleY, int damage, boolean evil){
        super(spriteTexture,  x,  y,  scaleX,  scaleY);
        this.damage = damage;
        this.evil = evil;
        this.expires = false;
        this.timeAdded = GameClock.timeInMS();
    }

    /**
     * Place a projectile which has a time to live in ms
     * @param spriteTexture
     * @param x
     * @param y
     * @param scaleX
     * @param scaleY
     * @param damage
     * @param ttlms
     */
    public Projectile(String spriteTexture, float x, float y, float scaleX, float scaleY, int damage, boolean evil, double ttlms){
        super(spriteTexture,  x,  y,  scaleX,  scaleY);
        this.damage = damage;
        this.evil = evil;
        this.expires = true;
        this.timeToLive = ttlms;
    }

    /**
     * Place a projectile belonging to the player
     * @param spriteTexture
     * @param x
     * @param y
     * @param scaleX
     * @param scaleY
     * @param damage
     */
    public Projectile(String spriteTexture, float x, float y, float scaleX, float scaleY, int damage){
        super(spriteTexture,  x,  y,  scaleX,  scaleY);
        this.damage = damage;
        this.evil = false;
        this.expires = false;
    }



    @Override
    public void update(RenderWindow window) {
        super.update(window);
        Vector2f playerPos = GameUtils.getPlayer().getPosition();
        double xDistance = playerPos.x - getPosition().x;
        double yDistance = playerPos.y - getPosition().y;

        if(Math.abs(xDistance) > 1000 || Math.abs(yDistance) > 1000 ){
            GameEngine.remove(this);
        }

        //Check if projectile has hit a monster
        ArrayList<GameSprite> collidingWith = GameUtils.checkCollisionAllGameSprites(this);
        for (GameSprite collidingSprite : collidingWith)
        {
            //Damage enemy and remove sprite if hit a monster
            if(collidingSprite instanceof Monster){
                ((Monster) collidingSprite).hit();
                ((Monster) collidingSprite).damage(this);
                ((Monster) collidingSprite).updateHealthBar();
            }
        }

        //Check if hit player if evil
        if(evil && GameUtils.isCollidingWithPlayer(this)){
            GameUtils.getPlayer().hit();
            GameUtils.getPlayer().health.dealDamage(damage);
        }

        if(this.timeToLive != 0.0){
            if(GameClock.timeInMS() - timeAdded > timeToLive){
                GameEngine.remove(this);
            }
        }
    }

    @Override
    public void OnCollision(Entity collider, Entity colliding, FloatRect colliderRect) {
        collider.collider.skipPhysics = true;
        if(colliding instanceof Projectile) {
            collider.collider.skipPhysics = true;
        }else{
            GameEngine.remove(this);
        }
    }

    public int getDamage() {
        return damage;
    }

    public boolean isEvil() {
        return evil;
    }

}
