package com.wgd.ecg.game.gameobjects.entities.monsters;
import com.wgd.ecg.game.components.generation.InstancedTextures;
import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;

import java.util.Random;


/**
 * This class creates a zombie
 */
public class Zombie extends Monster {
    public static float spawnRate = 50f;
    public Zombie(int xPos, int yPos, float scaleX, float scaleY){
        super(150, 20, 75, 400, 100, 1500, xPos, yPos, scaleX, scaleY, InstancedTextures.getTexture(InstancedTextures.ZOMBIESPRITE), false, 8, 3, 0.1f);

        playAnimation(new Random().nextInt(3) + 1);
    }
}
