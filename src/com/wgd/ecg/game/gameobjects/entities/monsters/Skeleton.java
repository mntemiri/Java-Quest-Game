package com.wgd.ecg.game.gameobjects.entities.monsters;


import com.wgd.ecg.game.components.generation.InstancedTextures;


/**
 * This class creates a skeleton
 */
public class Skeleton extends Monster {
    public static float spawnRate = 40f;
    public Skeleton(int xPos, int yPos, float scaleX, float scaleY){
        //super(70, 10, 75, 500, 350, 100, xPos, yPos, scaleX, scaleY, "skeleton", level, true);
        super(70, 10, 75, 500, 350, 1500, xPos, yPos, scaleX, scaleY, InstancedTextures.getTexture(InstancedTextures.SKELETONSPRITE), true, 8,  1,  0.1f);
    }
}
