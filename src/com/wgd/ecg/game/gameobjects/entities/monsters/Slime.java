package com.wgd.ecg.game.gameobjects.entities.monsters;


import com.wgd.ecg.game.components.generation.InstancedTextures;

/**
 * This class creates a slime
 */
public class Slime extends Monster{
    public static float spawnRate = 40f;
    public Slime(int xPos, int yPos, float scaleX, float scaleY){
        super(200, 20, 50,400, 100, 1500, xPos, yPos, scaleX, scaleY, InstancedTextures.getTexture(InstancedTextures.SLIMESPRITE), false, 4, 1, 0.1f);
    }
}
