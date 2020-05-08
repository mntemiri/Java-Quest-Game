package com.wgd.ecg.game.gameobjects.generation;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.engine.GameStateManager;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.entities.monsters.*;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;

import java.util.Random;


/**
 * This class is use to spaun sprites around the map
 */
public class Spawner extends GameSprite {
    float spawnDistanceFromPlayer = 1500;
    boolean canSpawn = true;

    private float randX;
    private float randY;
    private float mapDimensions;
    private float centerMap;
    Random rand;
    int x, y;

    public Spawner(String texture, float randX, float randY, int x, int y, float mapDimensions, float centerMap, Random rand) {
        super(texture, x, y, 0.0001f, 0.0001f);
        this.randX = randX;
        this.randY = randY;
        this.mapDimensions = mapDimensions;
        this.centerMap = centerMap;

        this.rand = rand;
        this.x = x;
        this.y = y;

        setPos(randX, randY);
    }

    /**
     * THis method generats random position of sprites in chunk
     * @param x x pos
     * @param y y pos
     * @return  the array of sprites
     */
    private float[] generateRandPosInChunk(int x, int y) {
        float result[] = new float[2];
        result[0] = (x * mapDimensions) - (mapDimensions / 2) + (rand.nextInt((int)mapDimensions / 2)) + centerMap;
        result[1] = (y * mapDimensions) - (mapDimensions / 2) + (rand.nextInt((int)mapDimensions)) + centerMap;
        return result;
    }

    /**
     * This method checks for collision between the spawned items
     */
    void spawnCheck() {
        if(GameUtils.distanceFromPlayer(this) < spawnDistanceFromPlayer) {
            spawning();
            canSpawn = false;
        }
    }

    /**
     * this method spawns the gamesprites around the map
     */
    void spawning() {
        if(GameUtils.getPlayer().getInventory().getBeans() == 0) {//NO BEANS
            if (rand.nextInt(100) < Slime.spawnRate) {
                float[] randPos = generateRandPosInChunk(x, y);
                Slime slime = new Slime((int) randPos[0], (int) randPos[1], 5, 5);
                GameEngine.add(slime);
            }

            if (rand.nextInt(100) < Zombie.spawnRate) {
                float[] randPos = generateRandPosInChunk(x, y);
                Zombie zombie = new Zombie((int) randPos[0], (int) randPos[1], 5, 5);
                GameEngine.add(zombie);
            }

            float[] randPos = generateRandPosInChunk(x, y);
            setPos(randPos[0], randPos[1]);
        }
        else if(GameUtils.getPlayer().getInventory().getBeans() == 1) {//ONE BEAN
            if (rand.nextInt(100) < Devil.spawnRate) {
                float[] randPos = generateRandPosInChunk(x, y);
                Devil devil = new Devil((int) randPos[0], (int) randPos[1], 5, 5);
                GameEngine.add(devil);
            }
        }
        else if(GameUtils.getPlayer().getInventory().getBeans() == 2) {//TWO BEANS
            if (rand.nextInt(100) < Ghost.spawnRate) {
                float[] randPos = generateRandPosInChunk(x, y);
                Ghost ghost = new Ghost((int) randPos[0], (int) randPos[1], 5, 5);
                GameEngine.add(ghost);
            }
        }



        //STANDARD SPAWNS
        if (rand.nextInt(100) < Skeleton.spawnRate) {
            for (int i = 0; i < Skeleton.packSize; i++) {
                float[] randPos = generateRandPosInChunk(x, y);
                float offset = (rand.nextInt(100));
                Skeleton skeleton = new Skeleton((int) (randPos[0] + offset), (int) (randPos[1] + offset), 5, 5);
                GameEngine.add(skeleton);
            }
        }

        if (rand.nextInt(100) < Knight.spawnRate) {
            for (int i = 0; i < Knight.packSize; i++) {
                float[] randPos = generateRandPosInChunk(x, y);
                float offset = (rand.nextInt(100));
                Knight knight = new Knight((int) (randPos[0] + offset), (int) (randPos[1] + offset), GameStateManager.OVERWORLDSTATE);
                GameEngine.add(knight);
            }
        }

        if (rand.nextInt(100) < Goblin.spawnRate) {
            float[] randPos = generateRandPosInChunk(x, y);
            Goblin goblin = new Goblin((int) randPos[0], (int) randPos[1], 5, 5);
            GameEngine.add(goblin);
        }
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        if (canSpawn)
            spawnCheck();
    }
}
