package com.wgd.ecg.game.gameobjects.entities.monsters;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.generation.InstancedTextures;
/**
 * This class creates a goblin
 */
public class Goblin extends Monster{
    public static float spawnRate = 40f;
    public Goblin(int xPos, int yPos, float scaleX, float scaleY){
        super(30, 10, 75, 500, 100, 1500, xPos, yPos, scaleX, scaleY, InstancedTextures.getTexture(InstancedTextures.GOBLINSPRITE), false, 8,  3,  0.1f);

        playAnimation(GameUtils.getPlayer().getInventory().beanCount() + 1);
    }
}
