package com.wgd.ecg.game.gameobjects.entities.heroes;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.Input;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.Projectile;
import org.jsfml.system.Vector2f;

/**
 * This class creates the hunter
 */
public class Hunter extends Hero{
    public Hunter(){
        super("hunter");
        baseDamage = 25;
        temporaryBaseDamage = baseDamage;
        this.moveOneCooldown = 2000;
        this.moveTwoCooldown = 1000;
    }

    private double lastFiredBasic;    //Last time basicAttack was triggered
    private double lastFiredOne;      //Last time moveOne was triggered
    private double lastFiredTwo;
    private boolean attacking;


    /**
     * This method creates the basic attack of the hunter
     */
    public void basicAttack(){
        //Cool down
        if(GameClock.timeInMS() - lastFiredBasic < 200 || attacking){
            return;
        }

        //Some vector magic
        Vector2f mousePos = GameEngine.getMousePosition();
        Vector2f playerPos = GameUtils.playerPosition();
        assert playerPos != null;
        double angle = GameUtils.getMouseAngle();

        //Gets a position between the mouse and player (value = distance)
        Vector2f instancePos = Vector2f.mul(Entity.normaliseVector(Vector2f.sub(mousePos, playerPos)), 90);
        instancePos = Vector2f.add(instancePos, playerPos);

        //Add bolt and the force
        Projectile arrow = new Projectile("arrow.png", instancePos.x,  instancePos.y, 2f, 2f, temporaryBaseDamage);
        arrow.setMaxVelocity(1);
        arrow.setFriction(0.1f);
        arrow.sprite.rotate(-1* (int) angle);
        arrow.addForce(Entity.normaliseVector(Vector2f.sub(mousePos, playerPos)), 200f);
        GameEngine.add(arrow);

        lastFiredBasic = GameClock.timeInMS();
        Audio.playRandomSwoosh();
    }


    /**
     * This method creates the move one attack
     */
    public void moveOne(){
        //Do nothing if move is on cooldown
        if(GameClock.timeInMS() - lastFiredOne < moveOneCooldown){
            return;
        }
        GameUtils.getPlayer().setMaxVelocity(0.01f);
        Input.getInstance().setAttackingOne(true);
        lastFiredOne = GameClock.timeInMS();
        attacking = true;

        moveBar.cooldown(0);
    }

    //Continues the first moves gradual attack
    public void moveOneContinue(){
        //Charge time
        if(GameClock.timeInMS() - lastFiredOne > 1000){
            //Some vector magic
            Vector2f mousePos = GameEngine.getMousePosition();
            Vector2f playerPos = GameUtils.playerPosition();
            assert playerPos != null;
            double angle = GameUtils.getMouseAngle();

            //Gets a position between the mouse and player (value = distance)
            Vector2f instancePos = Vector2f.mul(Entity.normaliseVector(Vector2f.sub(mousePos, playerPos)), 150);
            instancePos = Vector2f.add(instancePos, playerPos);

            //Add bolt and the force
            Entity arrow = new Projectile("arrow.png", instancePos.x,  instancePos.y, 3.5f, 3.5f, temporaryBaseDamage * 5);
            arrow.setMaxVelocity(1);
            arrow.setFriction(0.1f);
            arrow.sprite.rotate(-1* (int) angle);
            arrow.addForce(Entity.normaliseVector(Vector2f.sub(mousePos, playerPos)), 200f);
            GameEngine.add(arrow);

            lastFiredBasic = GameClock.timeInMS();
            Input.getInstance().setAttackingOne(false);
            attacking = false;
            GameUtils.getPlayer().setMaxVelocity(GameUtils.getPlayer().getMaxVelocity());
            Audio.playRandomSwoosh();
        }
    }

    /**
     * Fires arrows in a cone
     */

    public void moveTwo(){
        //Do nothing if move is on cooldown
        if(GameClock.timeInMS() - lastFiredTwo < moveTwoCooldown || attacking){
            return;
        }

        /**
         * Some vector magic
         */
        Vector2f mousePos = GameEngine.getMousePosition();
        Vector2f playerPos = GameUtils.playerPosition();
        assert playerPos != null;

        double angle = GameUtils.getMouseAngle();

        int spaces = 10;
        int amount = 8;

        //Determine the starting point
        int start = ((int) angle) - ((amount/2) * spaces);

        for(int i = start; i < (start + (spaces*amount)); i+= spaces){
            Vector2f radiusPos = new Vector2f((float) Math.sin(Math.toRadians(i)) * 200,  (float) Math.cos(Math.toRadians(i)) * 200);
            Vector2f position = Vector2f.add(playerPos, radiusPos);
            Entity arrow = new Projectile("arrow.png", position.x,  position.y, 1.2f, 0.7f, (int) (temporaryBaseDamage*0.5));
            arrow.setMaxVelocity(1);
            arrow.setFriction(0.1f);
            arrow.sprite.setRotation(i*-1);
            arrow.addForce(Entity.normaliseVector(Vector2f.sub(mousePos, playerPos)), 200f);
            GameEngine.add(arrow);
        }
        moveBar.cooldown(1);
        lastFiredTwo = GameClock.timeInMS();
        Audio.playRandomSwoosh();
    }
}
