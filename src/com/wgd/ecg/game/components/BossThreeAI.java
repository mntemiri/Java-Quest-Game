package com.wgd.ecg.game.components;

import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.Bomb;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.Projectile;
import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;
import org.jsfml.system.Vector2f;

import java.util.Random;

/**
 * This class creats the boss 3
 */
public class BossThreeAI extends AI {
    private int battleStage = 0;
    private int healthThreshold = host.getMaxHP()/4 * 3;
    private double lastAction = GameClock.timeInSEC(), attackTimer = GameClock.timeInSEC();
    private boolean inAttack = false;
    private Vector2f direction;
    private int orientation = 0;
    private int spiralCount = 2;
    private int spiralLength = 30;
    private int spiralAdded = 0;
    private long lastAdded;
    private double angle = 0;
    private int radius = 0;
    private Projectile[][] missiles = new Projectile[spiralCount][spiralLength];

    public BossThreeAI(Monster host) {
        super(host);

        host.playAnimation(1);
    }

    @Override
    public void brainUpdate() {
        battleStageManager();

        switch (battleStage) {
            case 0:
                if(GameClock.timeInSEC() - lastAction > 2) {
                    if(inRange() || inAttack) {
                        attackStageManager();
                    }
                    else {
                        chase(true);
                    }
                }
                else {
                    runAway();
                }
                break;
            case 1:
                if(GameClock.timeInSEC() - lastAction > 4 || inAttack) {
                    attackStageManager();
                }
                break;
            case 2:
                if(GameClock.timeInSEC() - lastAction > 1 || inAttack) {
                    chase(false);
                    attackStageManager();
                }
                else {
                    chase(true);
                }
                break;
            case 3:
                if(GameClock.timeInSEC() - lastAction > 5 || inAttack) {
                    attackStageManager();
                }
                else {
                    runAway();
                }
                break;
        }
    }


    /**
     * This method creates the battle stage manager
     */
    private void battleStageManager() {
        if(host.getHP() < healthThreshold) {
            battleStage++;
            healthThreshold -= host.getMaxHP()/4;

            inAttack = false;

            host.playAnimation(1);
            host.setAnimationSpeedInSeconds(0.5f);
        }
    }

    /**
     * This method creates the attack stage manager
     */
    private void attackStageManager() {
        switch (battleStage) {
            case 0://Slam Attack
                if(!inAttack) {
                    inAttack = true;

                    attackTimer = GameClock.timeInSEC();

                    host.playAnimation(2);
                    host.setAnimationSpeedInSeconds(0.1f);
                }

                if(GameClock.timeInSEC() - attackTimer > 0.5) {
                    slamAttack();
                }
                break;
            case 1://Charge Attack
                if(!inAttack) {
                    host.playAnimation(3);
                    host.setAnimationSpeedInSeconds(0.2f);

                    direction = new Vector2f(GameUtils.playerPosition().x - host.getPosition().x, GameUtils.playerPosition().y - host.getPosition().y);
                    direction = GameUtils.normaliseVector(direction);
                    checkDirection(direction);

                    lastAction = GameClock.timeInSEC();

                    inAttack = true;
                }
                else if(GameClock.timeInSEC() - lastAction > 4) {
                    host.playAnimation(4);
                    host.setAnimationSpeedInSeconds(0.8f);

                    lastAction = GameClock.timeInSEC();

                    inAttack = false;
                }
                else {
                    host.addForce(direction, speed * 10);

                    chargeAttack();
                }
                break;
            case 2://Beam Circle
                inAttack = true;
//                if(!inAttack) {
//                    host.playAnimation(1);
//                    host.setAnimationSpeedInSeconds(0.8f);
//
//                    lastAction = GameClock.timeInSEC();
//
//                    inAttack = true;
//                }
//                else if(GameClock.timeInSEC() - lastAction > 6) {
//                    host.playAnimation(1);
//                    host.setAnimationSpeedInSeconds(0.8f);
//
//                    lastAction = GameClock.timeInSEC();
//
//                    inAttack = false;
//                }
//                else {
                    beamAttack();
                //}
                break;
            case 3://Directional Beam
                if(!inAttack) {
                    host.playAnimation(1);
                    host.setAnimationSpeedInSeconds(0.8f);

                    orientation = new Random().nextInt(2);

                    lastAction = GameClock.timeInSEC();

                    inAttack = true;
                }
                else if(GameClock.timeInSEC() - lastAction > 5) {
                    host.playAnimation(1);
                    host.setAnimationSpeedInSeconds(0.8f);

                    lastAction = GameClock.timeInSEC();

                    inAttack = false;
                }
                else {
                    bombAttack();

                    lastAction = GameClock.timeInSEC();

                    inAttack = false;
                }
                break;
        }
    }

    /**
     * This method is used to perform the slam attack animation
     */
    private void slamAttack() {
        if(inRange()) {
            GameUtils.getPlayer().health.dealDamage(host.getDamage());
        }

        inAttack = false;

        lastAction = GameClock.timeInSEC();

        host.playAnimation(1);
        host.setAnimationSpeedInSeconds(0.8f);
    }


    /**
     * This method is used to keep some time to charge the attack
     */
    private void chargeAttack() {
        if(inRange() && GameClock.timeInSEC() - attackTimer > 0.5) {
            GameUtils.getPlayer().health.dealDamage((int)(host.getDamage()));

            attackTimer = GameClock.timeInSEC();
        }
    }


    /**
     * This method creates the beam attack
     */
    private void beamAttack(){
        if(spiralAdded < spiralLength && System.currentTimeMillis() - lastAdded > 100 ){
            for(int j = 0; j<spiralCount; j++){
                missiles[j][spiralAdded] = new Projectile("ice_bolt.png", 20, 20, 0.5f,0.5f, 10, true);
                GameEngine.add(missiles[j][spiralAdded]);
            }
            spiralAdded++;
            lastAdded = System.currentTimeMillis();
        }

        //Advance each existing spiral
        for(int j = 0; j<spiralCount; j++){
            for(int i = 0; i<spiralAdded; i++){
                int direction;
                if(j == 0){
                    direction = 1;
                }else{
                    direction = -1;
                }
                Vector2f radiusPos = new Vector2f((float) Math.sin(Math.toRadians(angle * direction)) * (radius + (i * 200) + (j * 50)),  (float) Math.cos(Math.toRadians(angle * direction)) * (radius + (i * 200)+ (j * 50)));
                Vector2f position = Vector2f.add(host.getPosition(), radiusPos);
                missiles[j][i].sprite.setPosition(position.x, position.y);
            }
            angle -= 1;
            if(radius > 600){
                radius = 0;
            }
        }
    }

    /**
     * This method creates the bomb attack
     */
    private void bombAttack() {
        if(inRange() && GameClock.timeInSEC() - attackTimer > 1) {
            attackTimer = GameClock.timeInSEC();
            System.out.println("Bomb");
            Vector2f playerPos = GameUtils.getPlayer().getPosition();
            Vector2f monPos = host.getPosition();
            Vector2f instancePos = Vector2f.mul(Entity.normaliseVector(Vector2f.sub(playerPos, monPos)), 150);
            instancePos = Vector2f.add(instancePos, monPos);
            GameEngine.add(new Bomb(instancePos.x, instancePos.y, playerPos.x, playerPos.y, 5000));
        }
    }

    /**
     * This method removes the spiral attack
     */
    private void removeAllSpirals(){
        for(int j = 0; j<spiralCount; j++){
            for(int i = 0; i<spiralAdded; i++){
                GameEngine.remove(missiles[j][i]);
            }
        }
        spiralAdded = 0;
    }
}
