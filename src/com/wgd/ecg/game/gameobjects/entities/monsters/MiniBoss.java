package com.wgd.ecg.game.gameobjects.entities.monsters;


import java.util.Random;
/**
 * This class creates a miniboss
 */
public class MiniBoss extends Monster {
    Random rand = new Random();
    public int minionsAlive = 0;
    public MiniBoss(int xPos, int yPos){
        super(2000, 80, 60, 100000, 100, 100, xPos, yPos, 4, 4, "MiniBoss_animation", false, 8,  2,  0.3f);

        setBrain("miniboss");

        if(rand.nextInt(2) == 0) {
            playAnimation(1);
        }
    }
}
