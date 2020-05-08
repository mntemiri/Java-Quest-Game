package com.wgd.ecg.game.gameobjects.entities.heroes;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.Input;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.Projectile;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import javax.swing.text.Position;

/**
 * This class creates the wizard
 */
public class Wizard extends Hero{
    public Wizard(){
        super("wizard");
        baseDamage = 100;
        temporaryBaseDamage = baseDamage;
        this.moveOneCooldown = 1500;
        this.moveTwoCooldown = 1000;
    }

    private double lastFiredBasic;    //Last time basicAttack was triggered
    private double lastFiredOne;      //Last time moveOne was triggered
    private double lastFiredTwo;      //Last time moveTwo was triggered
    private int shardCount = 0;
    private int maxShards = 22;
    private Projectile[] shards = new Projectile[maxShards];
    private int radius = 75;

    /**
     * Fires an ice bolt at the cursor
     */
    public void basicAttack(){

        //Cool down
        if(GameClock.timeInMS() - lastFiredBasic < 1000){
            return;
        }

        //Some vector magic
        Vector2f mousePos = GameEngine.getMousePosition();
        Vector2f playerPos = GameUtils.playerPosition();
        double angle = GameUtils.getMouseAngle();


        //Gets a position between the mouse and player (value = distance)
        Vector2f instancePos = Vector2f.mul(Entity.normaliseVector(Vector2f.sub(mousePos, playerPos)), 90);
        instancePos = Vector2f.add(instancePos, playerPos);

        //Add bolt and the force
        Projectile iceBolt = new Projectile("red_bolt.png", instancePos.x,  instancePos.y, 0.7f, 0.7f, temporaryBaseDamage);
        iceBolt.setMaxVelocity(1f);
        iceBolt.setFriction(0.1f);
        iceBolt.sprite.rotate(-1* (int) angle);
        iceBolt.addForce(Entity.normaliseVector(Vector2f.sub(mousePos, playerPos)), 200f);
        GameEngine.add(iceBolt);

        lastFiredBasic = GameClock.timeInMS();
        Audio.playRandomSwoosh();

    }

    /**
     * This method creates the move one attack of the wizard
     */
    public void moveOne(){
        //Do nothing if move is on cooldown
        if(GameClock.timeInMS() - lastFiredOne < moveOneCooldown){
            return;
        }

        moveBar.cooldown(0);

        for(shardCount = 0; shardCount<maxShards; shardCount++){
            addShard(GameEngine.getMousePosition());
        }
        Input.getInstance().setAttackingOne(true);
        lastFiredOne = GameClock.timeInMS();
    }

    //Continues the first moves gradual attack
    public void moveOneContinue(){
        if(GameClock.timeInMS() - lastFiredOne > 500){
            for(int i = 0; i<shardCount; i++){
                GameEngine.remove(shards[i]);
            }
            shardCount = 0;
            Input.getInstance().setAttackingOne(false);
        }
    }

    //Adds a shard to the explosion
    private void addShard(Vector2f mousePos){
        double placeAngle = shardCount * (360/maxShards);
        Vector2f radiusPos = new Vector2f((float) Math.sin(Math.toRadians(placeAngle)) * radius,  (float) Math.cos(Math.toRadians(placeAngle)) * radius);
        Vector2f position = Vector2f.add(mousePos, radiusPos);
        shards[shardCount] = new Projectile("purple_bolt.png", position.x, position.y, 0.3f, 0.3f, (int) (temporaryBaseDamage*0.5));


        shards[shardCount].setMaxVelocity(1f);
        Vector2f force = Entity.normaliseVector(Vector2f.sub(shards[shardCount].getPosition(), mousePos));
        shards[shardCount].addForce(force, 200f);

        double angle = GameUtils.getAngleBetween(shards[shardCount].getPosition(), mousePos);

        shards[shardCount].sprite.rotate(-1 * (int) angle);
        GameEngine.add(shards[shardCount]);
    }

    /**
     * Fires ice shards in a cone as second move attack
     */

    public void moveTwo(){
        //Do nothing if move is on cooldown
        if(GameClock.timeInMS() - lastFiredTwo < moveTwoCooldown){
            return;
        }

        moveBar.cooldown(1);

        //Some vector magic
        Vector2f mousePos = GameEngine.getMousePosition();
        Vector2f playerPos = GameUtils.playerPosition();
        assert playerPos != null;

        double angle = GameUtils.getMouseAngle();

        int spaces = 10;
        int amount = 4;

        //Determine the starting point
        int start = ((int) angle) - ((amount/2) * spaces);

        for(int i = start; i < (start + (spaces*amount)); i+= spaces){
            Vector2f radiusPos = new Vector2f((float) Math.sin(Math.toRadians(i)) * 200,  (float) Math.cos(Math.toRadians(i)) * 200);
            Vector2f position = Vector2f.add(playerPos, radiusPos);
            Projectile iceBolt = new Projectile("ice_boltD.png", position.x,  position.y, 0.5f, 0.5f, (int) temporaryBaseDamage);
            iceBolt.setMaxVelocity(1);
            iceBolt.setFriction(0.1f);
            iceBolt.sprite.setRotation(i*-1);
            iceBolt.addForce(Entity.normaliseVector(Vector2f.sub(mousePos, playerPos)), 200f);
            GameEngine.add(iceBolt);
        }
        lastFiredTwo = GameClock.timeInMS();
        Audio.playRandomSwoosh();
    }

}
