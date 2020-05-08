package com.wgd.ecg.game.components;

import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.Projectile;
import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;
import org.jsfml.system.Vector2f;

import java.time.Clock;
import java.util.Random;

/**
 * this class creates the boss 1
 */

public class BossOneAI extends RangedAI {
    private boolean spotted; //Has the boss spotted the player?
    private int state;
    private int homingCount = 0;
    private int maxHoming = 10;
    private int spiralCount = 4;
    private int spiralLength = 30;
    private int spiralAdded = 0;
    private double angle = 0;
    private int radius = 0;
    private long lastAdded;
    private long lastStateUpdate;
    private double lastHoming;
    private GameSprite missile;
    private Projectile[][] missiles = new Projectile[spiralCount][spiralLength];
    private Projectile[] homingMissiles = new Projectile[maxHoming];

    public BossOneAI(Monster host){
        super(host);
        state = 0;
        lastStateUpdate = System.currentTimeMillis();
        setProjectile("purple_bolt.png");
    }

    @Override
    public void brainUpdate() {
        if(spotted){
            attack();
        }
        if(chasing) {
            if(inSight()) {
                if(inRange()) {
                    //attack();
                    spotted = true;
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
     * This method creates the attack and keeps it for a certain amount of time
     */
    private void attack(){
        //Update state every 15s
        if(System.currentTimeMillis() - lastStateUpdate > 10000){
            Random random = new Random();
            int nextState = random.nextInt(3);
            if(state == 1){
                host.resetSpeed();
                removeAllSpirals();
            }if(state == 2){
                removeAllHoming();
            }

            //Prevent the same state twice
            while(nextState == state){
                nextState = random.nextInt(3);
            }
            state = nextState;
            lastStateUpdate = System.currentTimeMillis();

        }
        switch (state){
            case 0:
                hitPlayer();
                break;
            case 1:
                spiralAttack();
                break;
            case 2:
                homingAttack();
        }
    }


    /**
     * This method creates the spiral attack
     */
    private void spiralAttack(){
        host.setMaxVelocity(0.000001f);

        //Add new projectile for each spiral
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
            double angle2 = angle + (j*(360/spiralCount));
            int radius2 = radius;
            for(int i = 0; i<spiralAdded; i++){
                Vector2f radiusPos = new Vector2f((float) Math.sin(Math.toRadians(angle2 + i * 5)) * radius2 + i,  (float) Math.cos(Math.toRadians(angle2 + i * 5)) * radius2 + i);
                Vector2f position = Vector2f.add(host.getPosition(), radiusPos);
                missiles[j][i].sprite.setPosition(position.x, position.y);
                angle2 += 2;
                radius2 += 25;
            }
            angle -= 0.2;
            if(radius > 600){
                radius = 0;
            }
        }
    }

    /**
     * Thismethod removes all the spirals
     */
    private void removeAllSpirals(){
        for(int j = 0; j<spiralCount; j++){
            for(int i = 0; i<spiralAdded; i++){
                GameEngine.remove(missiles[j][i]);
            }
        }
        spiralAdded = 0;
    }

    /**
     * This method keeps  the homing attack
     */
    private void homingAttack(){
        //Fire every 0.5 seonds
        if(GameClock.timeInSEC() - lastHoming > 1 && homingCount < maxHoming){
            homingMissiles[homingCount] = createHoming();
            GameEngine.add(homingMissiles[homingCount]);
            homingCount++;
            lastHoming = GameClock.timeInSEC();
        }
        for(int i = 0; i<homingCount; i++){
            Vector2f missilePos = homingMissiles[i].getPosition();
            Vector2f playerPos = GameUtils.playerPosition();
            Vector2f shootDirection = Entity.normaliseVector(Vector2f.sub(playerPos, missilePos));
            homingMissiles[i].setMaxVelocity(0.000000000001f);
            homingMissiles[i].setMaxVelocity(0.001f);
            homingMissiles[i].addForce(Entity.normaliseVector(shootDirection), 100f);
        }
    }

    /**
     * This method creates the homing attack
     * @return
     */
    private Projectile createHoming(){
        Vector2f monPos = host.getPosition();
        Vector2f playerPos = GameUtils.playerPosition();
//        playerPos = Vector2f.add(playerPos, GameUtils.getPlayer().getVelocity());
        Vector2f shootDirection = Entity.normaliseVector(Vector2f.sub(playerPos, monPos));
        direction = GameUtils.normaliseVector(direction);

        //Get spawn location of arrow
        Vector2f instancePos = Vector2f.mul(Entity.normaliseVector(Vector2f.sub(playerPos, monPos)), 150);
        instancePos = Vector2f.add(instancePos, monPos);

        double angle = GameUtils.getAngleBetween(playerPos, monPos);

        Projectile missile = new Projectile("red_bolt.png", instancePos.x,  instancePos.y, 1.5f, 1.5f, 45, true);
        missile.setMaxVelocity(0.001f);
        missile.setFriction(1f);
        missile.sprite.rotate(-1* (int) angle);
        missile.addForce(Entity.normaliseVector(shootDirection), 100f);
        return missile;
    }


    /**
     * This method removes the himing attack
     */
    private void removeAllHoming(){
        for(int i = 0; i<maxHoming; i++){
                GameEngine.remove(homingMissiles[i]);
        }
        homingCount = 0;
    }

    /**
     * Called by Boss when boss is killed
     */
    public void removeAll(){
        removeAllHoming();
        removeAllSpirals();
    }
}

//for(int j = 0; j<spiralCount; j++){
//        double angle2 = angle + (j*(360/spiralCount));
//        int radius2 = radius;
//        for(int i = 0; i<spiralLength; i++){
//        //Create if null
//        if(missiles[j][i] == null){
//        missiles[j][i] = new Entity("ice_bolt.png", 20, 20, 0.5f,0.5f);
//        GameEngine.add(missiles[j][i]);
//        }else{
//        //Vector2f radiusPos = new Vector2f((float) Math.sin(Math.toRadians(angle + i * 2)) * radius2,  (float) Math.cos(Math.toRadians(angle + i * 5)) * radius2); proper odd swirly boy
//        Vector2f radiusPos = new Vector2f((float) Math.sin(Math.toRadians(angle2 + i * 5)) * radius2 + i + (j * 15),  (float) Math.cos(Math.toRadians(angle2 + i * 5)) * radius2 + i + (j * 15));
//        Vector2f position = Vector2f.add(host.getPosition(), radiusPos);
//        missiles[j][i].sprite.setPosition(position.x, position.y);
//        }
//        angle2 += 2;
//        radius2 += 10;
//        }
//        radius++;
//        angle += 0.2;
//        if(radius > 600){
//        radius = 0;
//        }
//        }