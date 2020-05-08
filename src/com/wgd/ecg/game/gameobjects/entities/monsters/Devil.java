package com.wgd.ecg.game.gameobjects.entities.monsters;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.generation.InstancedTextures;

import java.util.Random;

/**
 * This class creates a devil
 */
public class Devil extends Monster{
    public static float spawnRate = 40f;
    public Devil(int xPos, int yPos, float scaleX, float scaleY){
        super(200, 20, 75, 500, 100, 1500, xPos, yPos, scaleX, scaleY, InstancedTextures.getTexture(InstancedTextures.DEVILSPRITE), false, 8,  3,  0.1f);

        playAnimation(new Random().nextInt(3) + 1);
    }
}
