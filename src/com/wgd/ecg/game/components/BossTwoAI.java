package com.wgd.ecg.game.components;

import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.entities.monsters.BossTwoMinion;
import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;
import org.jsfml.system.Vector2f;

/**
 * This class creates the boss 2
 */
public class BossTwoAI extends AI{
    private int combatStage = 1, minionSpawnCounter = 0;
    private double combatStageTimer = GameClock.timeInSEC();
    private double minionSpawnTimer = GameClock.timeInSEC();
    public int minionsAlive = 0;

    public BossTwoAI(Monster host) {
        super(host);

        host.playAnimation(2);
    }

    /**
     * This method checks health of the final boss and also does the animations
     */
    @Override
    public void brainUpdate() {
        if(minionSpawnCounter < 5 && host.getHP() < host.getMaxHP()/2 && minionsAlive == 0 && GameClock.timeInSEC() - minionSpawnTimer > 10) {
            minionSpawnCounter++;
            for(int i = 0; i < 5; i++) {
                GameEngine.add(new BossTwoMinion(this));
                minionsAlive++;
            }

            minionSpawnTimer = GameClock.timeInSEC();
        }

        switch(combatStage) {
            case 1://wandering
                if(com.wgd.ecg.game.components.GameUtils.isCollidingWithPlayer(host) && GameClock.timeInSEC() - combatStageTimer > 4) {
                    host.playAnimation(4);

                    host.setAnimationSpeedInSeconds(0.1f);

                    combatStageUpdate();
                }
                else {
                    //wander sprite
                    chase(true);
                }
                break;
            case 2://first hit instance
                //first sprite
                if(GameClock.timeInSEC() - combatStageTimer > 0.2) {
                    //second sprite

                    combatStageUpdate();
                }
                break;
            case 3://second hit instance
                //second sprite
                if(GameClock.timeInSEC() - combatStageTimer > 0.2) {

                    combatStageUpdate();
                }
                break;
            case 4://third hit instance
                //third sprite
                if(GameClock.timeInSEC() - combatStageTimer > 0.2) {
                    if(GameUtils.distanceFromPlayer(host) < host.getRange()) {
                        basicAttack();
                    }

                    host.playAnimation(1);

                    host.setAnimationSpeedInSeconds(1f);

                    combatStageUpdate();
                }
                break;
            case 5://short rest instance
                if(GameClock.timeInSEC() - combatStageTimer > 4) {

                    host.playAnimation(3);

                    host.setAnimationSpeedInSeconds(0.1f);

                    combatStageUpdate();
                }
                break;
            case 6://first spin
                //spin sprite

                direction = new Vector2f(GameUtils.playerPosition().x - host.getPosition().x, GameUtils.playerPosition().y - host.getPosition().y);
                direction = GameUtils.normaliseVector(direction);
                host.addForce(direction, speed*8);

                if(GameUtils.isCollidingWithPlayer(host)) {
                    spinAttack();
                }

                if(GameClock.timeInSEC() - combatStageTimer > 1) {
                    combatStageUpdate();
                }

                break;
            case 7://second spin
                direction = new Vector2f(GameUtils.playerPosition().x - host.getPosition().x, GameUtils.playerPosition().y - host.getPosition().y);
                direction = GameUtils.normaliseVector(direction);
                host.addForce(direction, speed*8);

                if(GameUtils.isCollidingWithPlayer(host)) {
                    spinAttack();
                }

                if(GameClock.timeInSEC() - combatStageTimer > 1) {
                    host.playAnimation(1);

                    host.setAnimationSpeedInSeconds(1f);

                    combatStageUpdate();
                }
                break;
            case 8://rest phase
                //resting sprite

                if(GameClock.timeInSEC() - combatStageTimer > 3) {
                    host.playAnimation(2);

                    host.setAnimationSpeedInSeconds(0.1f);

                    combatStageUpdate();
                }

                break;

        }

//        System.out.println(combatStage);
    }

    /**
     * This method creates the combat stage update
     */
    private void combatStageUpdate() {
        if(combatStage > 7) {
            combatStage = 1;
        }
        else {
            combatStage ++;
        }

        combatStageTimer = GameClock.timeInSEC();
    }

    /***
     * This method performs the basic attack
     */
    private void basicAttack() {
        GameUtils.getPlayer().health.dealDamage(host.getDamage());
    }


    /**
     * This method performs the spin attack
     */
    private void spinAttack() {
        GameUtils.getPlayer().health.dealDamage(host.getDamage() / 100);
    }
}
