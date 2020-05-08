package com.wgd.ecg.game.components;

import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.entities.monsters.Bat;
import com.wgd.ecg.game.gameobjects.entities.monsters.MiniBoss;
import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;
import org.jsfml.system.Vector2f;

import java.util.Random;

/**
 * This class is used to create the minibosses
 */
public class MiniBossAI extends AI{
    public int minionsAlive = 0;
    private Monster host;
    private Random rand = new Random();
    private double minionSpawnTimer = GameClock.timeInSEC();
    public MiniBossAI(Monster host) {
        super(host);
        this.host = host;
    }

    /**
     * This method is used to keep track of health and attacks
     */
    @Override
    public void brainUpdate() {
        if(inRange()) {
            hitPlayer();
        }
        else if(host.getHP() < host.getMaxHP()/3){
            runAway();
            spawnMinions();
        }
        else {
            chase(true);
        }
    }


    /**
     * This method is used to spawn minions
     */
    private void spawnMinions() {
        if(minionsAlive == 0 && GameClock.timeInSEC() - minionSpawnTimer > 5) {
            GameEngine.add(new Bat((int) (rand.nextInt(200) + host.getXPos()), (int)(rand.nextInt(200) + host.getYPos()),  this));
            GameEngine.add(new Bat((int) (rand.nextInt(200) + host.getXPos()), (int)(rand.nextInt(200) + host.getYPos()),  this));
            GameEngine.add(new Bat((int) (rand.nextInt(200) + host.getXPos()), (int)(rand.nextInt(200) + host.getYPos()),  this));
            minionsAlive += 3;
            minionSpawnTimer = GameClock.timeInSEC();
        }
    }
}
