package com.wgd.ecg.game.gameobjects.entities.heroes;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.Input;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.Damagebox;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.Projectile;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

/**
 * This class creates the warrior
 *
 */

public class Warrior extends Hero{
    private double lastFiredBasic; //Maybe put this in hero
    private double lastFiredTwo;
    private double lastFiredOne;
    private boolean debugMode = false; //Set to true if you want to see hitboxes :)
    private boolean forceApplied;
    private Damagebox chargeBox;

    public Warrior(){
        super("warrior");
        baseDamage = 75;
        temporaryBaseDamage = baseDamage;
        this.moveOneCooldown = 1500;
        this.moveTwoCooldown = 2500;
    }

    /**
     * Strikes in the direction of the mouse
     */
    public void basicAttack(){
        //Cool down
        if((GameClock.timeInMS() - lastFiredBasic) < 200){
            return;
        }
        GameUtils.getPlayer().getWeapon().playOne(0.05f);

        //Get mouse position, player position, and angle of mouse in relation to player
        Vector2f mousePos = GameEngine.getMousePosition();
        Vector2f playerPos = GameUtils.playerPosition();
        double angle = GameUtils.getMouseAngle();

        //Gets a position between the mouse and player (value = distance)
        Vector2f instancePos = Vector2f.mul(Entity.normaliseVector(Vector2f.sub(mousePos, playerPos)), 70);
        instancePos = Vector2f.add(instancePos, playerPos);

        Damagebox damageBox = new Damagebox("damagebox.png", instancePos.x,  instancePos.y + 25, 3.5f, 3f, temporaryBaseDamage*2);
        damageBox.sprite.rotate(-1* (int) angle);
        damageBox.updateCollisions();

        //Shows hit boxes
        if(debugMode){
            GameEngine.add(damageBox);
        }
        lastFiredBasic = GameClock.timeInMS();
        Audio.playRandomSwoosh();
    }

    /**
     * This method implements warrior move one
     */
    public void moveOne(){
        //Do nothing if move is on cooldown
        if(GameClock.timeInMS() - lastFiredOne < 1500){
            return;
        }

        moveBar.cooldown(0);

        Player player = GameUtils.getPlayer();
        Vector2f playerPos = player.getPosition();
        chargeBox = new Damagebox("chargebox_invis.png", playerPos.x,  playerPos.y, 4f, 4f, temporaryBaseDamage*4);
        GameEngine.add(chargeBox);

        //Remove players current movement
        player.setMaxVelocity(0.1f);
        GameUtils.getPlayer().setMaxVelocity(GameUtils.getPlayer().getMaxVelocity()*2);
        Input.getInstance().setAttackingOne(true);
        lastFiredOne = GameClock.timeInMS();
        forceApplied = false;
        player.setMovementEnabled(false);
        Audio.playRandomSwoosh();
    }

    //Continues the first moves gradual attack
    public void moveOneContinue(){
        Vector2f playerPos = GameUtils.playerPosition();
        assert playerPos != null;
        chargeBox.sprite.setPosition(playerPos);
        chargeBox.updateCollisions();
        //Apply the force
        if(!forceApplied){
            Vector2f mousePos = GameEngine.getMousePosition();
            Vector2f chargeDirection = Entity.normaliseVector(Vector2f.sub(mousePos, playerPos));
            System.out.println("Velocity:" + GameUtils.getPlayer().getVelocity());
            Vector2f playerVel = GameUtils.getPlayer().getVelocity();
            GameUtils.getPlayer().addForce(chargeDirection, 5000);
            forceApplied = true;
        }



        //Reset at end
        if(GameClock.timeInMS() - lastFiredOne > 500){
            Player player = GameUtils.getPlayer();
            GameUtils.getPlayer().setMaxVelocity(GameUtils.getPlayer().getMaxVelocity());
            player.setMovementEnabled(true);
            GameEngine.remove(chargeBox);
        }
    }

    /**
     * Throws knives at opponents
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

        int spaces = 20;
        int amount = 3;

        //Determine the starting point
        int start = ((int) angle) - ((amount/2) * spaces);

        for(int i = start; i < (start + (spaces*amount)); i+= spaces){
            Vector2f radiusPos = new Vector2f((float) Math.sin(Math.toRadians(i)) * 200,  (float) Math.cos(Math.toRadians(i)) * 200);
            Vector2f position = Vector2f.add(playerPos, radiusPos);
            Entity knife = new Projectile("knife.png", position.x,  position.y, 2f, 2f, (int) (baseDamage*0.75));
            knife.setMaxVelocity(1);
            knife.setFriction(0.1f);
            knife.sprite.setRotation(i*-1);
            knife.addForce(Entity.normaliseVector(Vector2f.sub(mousePos, playerPos)), 200f);
            GameEngine.add(knife);
        }
        lastFiredTwo = GameClock.timeInMS();
        Audio.playRandomSwoosh();
    }

}
