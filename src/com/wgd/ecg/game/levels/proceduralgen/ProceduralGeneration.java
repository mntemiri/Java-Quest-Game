package com.wgd.ecg.game.levels.proceduralgen;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameObject;
import com.wgd.ecg.engine.GameStateManager;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.generation.InstancedTextures;
import com.wgd.ecg.game.gameobjects.Castle;
import com.wgd.ecg.game.gameobjects.Cave;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.entities.monsters.*;
import com.wgd.ecg.game.gameobjects.generation.Background;
import com.wgd.ecg.game.gameobjects.generation.Spawner;
import com.wgd.ecg.game.gameobjects.generation.Tree;
import org.jsfml.graphics.RenderWindow;

import java.util.Random;

/**
 * This class creates the procedural generation
 */
public class ProceduralGeneration extends GameObject implements Runnable {
//    RoadGen roadGen;

    Random rand;
    int seed;

//    GeneratedObject backgrounds[] = new GeneratedObject[9];
//    List<GameSprite> generatedObjects;

    int lastPlayerPosX, lastPlayerPosY;

    int mapDimensions;
    int mapSize;
    float chunkSize;
    int treeCount;

    boolean mapGenerated;
    float centerMap;

    int stage;

    Castle[] castles;
    Cave[] caves;

    Thread instanceThread;

    public ProceduralGeneration(int seed) {
        mapDimensions = 1088;
        chunkSize = (mapDimensions * mapSize);
        removeOnClear = true;

//        roadGen = new RoadGen(mapDimensions);
//        mapGen = new MapGen(chunkSize, 10);

//        generatedObjects = new CopyOnWriteArrayList();

        lastPlayerPosY = Integer.MAX_VALUE;
        lastPlayerPosY = Integer.MAX_VALUE;

        mapSize = 15;

        treeCount = 20;

        mapGenerated = false;

        stage = 1;

        castles = null;
        caves = null;

        this.seed = seed;
        rand = new Random(seed);

//        instanceThread = new Thread(this);
//        instanceThread.start();
    }


    /**
     * This method is used to generate the map using the generate Map method
     */
    public void generate() {
        if (GameUtils.playerPosition() == null)
            return;

        if (!mapGenerated)
            generateMap();

        if (lastPlayerPosX != (int) (GameUtils.playerPosition().x / mapDimensions) || lastPlayerPosY != (int) (GameUtils.playerPosition().y / mapDimensions)) {
            lastPlayerPosX = (int) (GameUtils.playerPosition().x / mapDimensions);
            lastPlayerPosY = (int) (GameUtils.playerPosition().y / mapDimensions);
//            oneTimeGeneratePerChunk(lastPlayerPosX, lastPlayerPosY);
        } else {
            return;
        }
    }


    /**
     * This method is used to generate the map
     */
    private void generateMap() {
        centerMap = -(mapSize * mapDimensions) / 2;

        setStage(GameUtils.getPlayer().getInventory().getBeans() + 1);

        int cornerCount = 0;
        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                GameObject bg = null;
                if ((x == 0 || x == mapSize - 1) && (y == 0 || y == mapSize - 1)) {
                    if (cornerCount == 0)
                        bg = new Entity(InstancedTextures.getTexture(InstancedTextures.WATERCORNER), x * mapDimensions + centerMap, y * mapDimensions + centerMap, 90);
                    else if (cornerCount == 1)
                        bg = new Entity(InstancedTextures.getTexture(InstancedTextures.WATERCORNER), x * mapDimensions + centerMap, y * mapDimensions + centerMap, 180);
                    else if (cornerCount == 2)
                        bg = new Entity(InstancedTextures.getTexture(InstancedTextures.WATERCORNER), x * mapDimensions + centerMap, y * mapDimensions + centerMap, 0);
                    else if (cornerCount == 3)
                        bg = new Entity(InstancedTextures.getTexture(InstancedTextures.WATERCORNER), x * mapDimensions + centerMap, y * mapDimensions + centerMap, -90);
                    cornerCount++;
                } else if (x == 0) {
                    bg = new Entity(InstancedTextures.getTexture(InstancedTextures.WATEREDGE), x * mapDimensions + centerMap, y * mapDimensions + centerMap, 0);
                } else if (x == mapSize - 1) {
                    bg = new Entity(InstancedTextures.getTexture(InstancedTextures.WATEREDGE), x * mapDimensions + centerMap, y * mapDimensions + centerMap, 180);
                } else if (y == 0) {
                    bg = new Entity(InstancedTextures.getTexture(InstancedTextures.WATEREDGE), x * mapDimensions + centerMap, y * mapDimensions + centerMap, 90);
                } else if (y == mapSize - 1) {
                    bg = new Entity(InstancedTextures.getTexture(InstancedTextures.WATEREDGE), x * mapDimensions + centerMap, y * mapDimensions + centerMap, -90);
                } else {
                    bg = stageGenerate(x, y);
                }


                GameEngine.add(bg);
            }
        }

        for (int y = 1; y < mapSize - 1; y++) {
            for (int x = 1; x < mapSize - 1; x++) {
                generateOnTerrainChunk(x, y);
            }
        }

        generateStructures();

        System.out.println("GENERATED");
        mapGenerated = true;
    }

    /**
     * This methd is used to generate the position in chunk
     * @param x xposition
     * @param y  y position
     * @return the array of results
     */
    private float[] generateRandPosInChunk(int x, int y) {
        float result[] = new float[2];
        result[0] = (x * mapDimensions) - (mapDimensions / 2) + (rand.nextInt(mapDimensions / 2)) + centerMap;
        result[1] = (y * mapDimensions) - (mapDimensions / 2) + (rand.nextInt(mapDimensions)) + centerMap;
        return result;
    }


    /**
     * THis method is use to generate structures such as castles and caves
     */
    private void generateStructures() {
        castles = new Castle[3];
        caves = new Cave[6];

        float baseChunk = (-mapDimensions * mapSize / 2) + mapDimensions;

        for (int i = 0; i < 3; i++) {
            float posX = ((rand.nextFloat() * 1000000000) % ((mapSize - 3) * mapDimensions));
            float posY = ((rand.nextFloat() * 1000000000) % ((mapSize - 3) * mapDimensions));
            boolean isColliding = false;

            // ADDS i TO LEVEL, FOR LEVELS 5-7
            Castle castle = new Castle(baseChunk + posX , baseChunk + posY, 1.5f, 1.5f, 5+i);

            for (Castle temp: castles) {
                if (temp == null) break;
                if (GameUtils.checkCollisionWithGameSprite(temp, castle)) {
                    isColliding = true;
                    castle.onRemove();
                }
            }

            if (isColliding) {
                i--;
                continue;
            }

            castles[i] = castle;
            GameEngine.add(castle);
            System.out.println("Castle POS: " + posX);
        }

        for (int i = 0; i < 6; i++) {
            float posX = ((rand.nextFloat() * 1000000000) % ((mapSize - 3) * mapDimensions));
            float posY = ((rand.nextFloat() * 1000000000) % ((mapSize - 3) * mapDimensions));
            boolean isColliding = false;

            Cave cave = new Cave( baseChunk + posX , baseChunk + posY, 1f , 1f , 2);

            isColliding = cave.checkIfCollision(caves, castles);

            if (isColliding) {
                i--;
                cave.onRemove();
                continue;
            }

            caves[i] = cave;
            GameEngine.add(cave);
            System.out.println("Cave POS: " + posX);
        }

        GameUtils.giveCastles(castles);
    }

    /**
     * This method is used to generate the terrain chunks
     * @param x
     * @param y
     */
    private void generateOnTerrainChunk(int x, int y) {
        for (int i = 0; i < treeCount; i++) {
            float[] randPos = generateRandPosInChunk(x, y);
            Tree tree = new Tree(randPos[0], randPos[1], (int) chunkSize);
            GameEngine.add(tree);
        }

//        for (int i = 0; i < 1; i++) {
//        if (rand.nextInt(100) > Skeleton.spawnRate) {
//            float[] randPos = generateRandPosInChunk(x, y);
//            Skeleton skeleton = new Skeleton((int) randPos[0], (int) randPos[1], 5, 5, GameStateManager.OVERWORLDSTATE);
//            GameEngine.add(skeleton);
//        }
//
//        if (rand.nextInt(100) > Knight.spawnRate) {
//            float[] randPos = generateRandPosInChunk(x, y);
//            Knight knight = new Knight((int) randPos[0], (int) randPos[1], GameStateManager.OVERWORLDSTATE);
//            GameEngine.add(knight);
//        }
//        }

        float[] randPos = generateRandPosInChunk(x, y);
        Spawner spawner = new Spawner("t.png", randPos[0], randPos[1], x, y, mapDimensions, centerMap, rand);
        GameEngine.add(spawner);
    }

    /**
     * This method is used to generate the stages
     * @param x x pos
     * @param y y pos
     * @return result
     */
    private Background stageGenerate(int x, int y) {
        Background bg;
        if (stage == 1)
            bg = new Background(InstancedTextures.STAGE1BACKGROUND, x * mapDimensions + centerMap, y * mapDimensions + centerMap, mapDimensions);
        else if (stage == 2)
            bg = new Background(InstancedTextures.STAGE2BACKGROUND, x * mapDimensions + centerMap, y * mapDimensions + centerMap, mapDimensions);
        else
            bg = new Background(InstancedTextures.STAGE3BACKGROUND, x * mapDimensions + centerMap, y * mapDimensions + centerMap, mapDimensions);

        return bg;
    }

//    private void oneTimeGeneratePerChunk(float x, float y) {
//        for (Monster enemy : enemiesToGenerate) {
//            enemy.getClass();
//            Monster monter = new Monster((int) x + (rand.nextInt((int) mapDimensions) - mapDimensions / 2), (int) y + (rand.nextInt((int) mapDimensions) - mapDimensions / 2), GameStateManager.OVERWORLDSTATE);
//        }

//        for (int i = 0; i < 1; i++) {
//            Knight knight = new Knight((int)(x * mapDimensions) + (rand.nextInt((int) mapDimensions) - mapDimensions / 2), (int)y + (rand.nextInt((int) mapDimensions) - mapDimensions / 2), GameStateManager.OVERWORLDSTATE);
//            GameEngine.add(knight);
//        }
//    }

//    private void removeDistantObjects() {
//        for (GameSprite object : generatedObjects) {
//            try {
//                if ((Entity.findMagnitude(GameUtils.playerPosition()) - Entity.findMagnitude(object.getPosition())) > mapDimensions * 2) {
//                    generatedObjects.remove(object);
//                    GameEngine.remove(object);
//                }
//            } catch (Exception e) {
//                System.out.println("Thread collision when removing GameObject");
//            }
//        }
//    }

    /**
     *
     * @return the current stage
     */
    public int getStage() {
        return stage;
    }

    /**
     *
     * @param stage to set the stage
     */
    public void setStage(int stage) {
        this.stage = stage;
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
//        removeDistantObjects();
//            removeDistantObjects();
        if (GameStateManager.getCurrentState() == GameStateManager.OVERWORLDSTATE) {
            generate();
        } else {
            mapGenerated = false;
        }
    }

    @Override
    public void run() {
        while (GameEngine.window.isOpen()) {
//            removeDistantObjects();
            if (GameStateManager.getCurrentState() == GameStateManager.OVERWORLDSTATE) {
                generate();
            } else {
                mapGenerated = false;
            }
        }
    }


    /**
     *
     * @return the seed
     */
    public int getSeed() {
        return seed;
    }


    /**
     *
     * @param seed used to set the seed
     */
    public void setSeed(int seed) {
        this.seed = seed;
        this.rand = new Random(seed);
    }
}

//    private class RoadGen {
//        int chunkSize;
//
//        public RoadGen(int chunkSize) {
//            this.chunkSize = chunkSize;
//        }
//    }

//}

//    private class MapGen {
//        int chunkSize;
//        int treeCount;
//
//        public MapGen(int chunkSize, int treeCount) {
//            this.chunkSize = chunkSize;
//            this.treeCount = treeCount;
//        }
//
////        void GenerateObjectRandomly(int x, int y, GeneratedObject GO) {
////            Random rand = new Random(102);
////
////            GO = new GeneratedObject(x + rand.nextInt((int) chunkSize) - chunkSize / 2, y + (rand.nextInt((int) chunkSize) - chunkSize / 2),  chunkSize);
////            GameEngine.add(GO);
////        }
//
//        public void generate(float x, float y, int currentI) {
//            if(backgrounds[currentI] != null)
//                GameEngine.remove(backgrounds[currentI]);
//            Background bg = new Background(x, y, chunkSize);
//            backgrounds[currentI] = (bg);
//            GameEngine.add(bg);
//
//            if (currentI == 0)
//                OneTimeGeneratePerChunk(x, y);
//        }
//
//        private void OneTimeGeneratePerChunk(float x, float y) {
//            for (int i = 0; i < 1; i++) {
//                Knight knight = new Knight((int)x + (rand.nextInt((int) chunkSize) - chunkSize / 2), (int)y + (rand.nextInt((int) chunkSize) - chunkSize / 2), GameStateManager.OVERWORLDSTATE);
//                GameEngine.add(knight);
//            }
//
//            for (int i = 0; i < treeCount; i++) {
//                Tree tree = new Tree(x + (rand.nextInt((int) chunkSize) - chunkSize / 2), y + (rand.nextInt((int) chunkSize) - chunkSize / 2),  chunkSize);
////                generatedObjects.add(tree);
//                GameEngine.add(tree);
//            }
//        }
//    }
//}
