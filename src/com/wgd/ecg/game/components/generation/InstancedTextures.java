package com.wgd.ecg.game.components.generation;

import com.wgd.ecg.engine.Audio;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This class creates instanced textures
 */
public class InstancedTextures {
    private static final InstancedTextures instance = new InstancedTextures();

    public ArrayList<Texture> textures = new ArrayList<Texture>();

    public static int TREE = 0;
    public static int STAGE1BACKGROUND = 1;
    public static int STAGE2BACKGROUND = 4;
    public static int STAGE3BACKGROUND = 5;
    public static int WATERCORNER = 2;
    public static int WATEREDGE = 3;
    public static int KNIGHTSPRITE = 6;
    public static int SKELETONSPRITE = 7;
    public static int SLIMESPRITE = 8;
    public static int GOBLINSPRITE = 9;
    public static int GHOSTSPRITE = 10;
    public static int DEVILSPRITE = 11;
    public static int ZOMBIESPRITE = 12;

    private InstancedTextures() {
        init();
    }

    /**
     * This method loads the texture to be randomby spawn
     * @param texture
     * @return
     */
    private Texture loadTexture(String texture) {
        Texture temp = new Texture();
        try {
            temp.loadFromFile(Paths.get(texture)); // Loads background texture
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    void init() {
        textures.add(loadTexture("src/com/wgd/ecg/res/images/tree1.png")); // Loads tree texture

        textures.add(loadTexture("src/com/wgd/ecg/res/images/Stage1_Enviroment_Tile.png")); // Loads background texture

        textures.add(loadTexture("src/com/wgd/ecg/res/background/Ocean_Edge_Corner.png")); // Loads background texture

        textures.add(loadTexture("src/com/wgd/ecg/res/background/Water_Edge_Full.png")); // Loads background texture

        textures.add(loadTexture("src/com/wgd/ecg/res/background/Stage_2_Envirmoment_Tile.png")); // Loads background texture

        textures.add(loadTexture("src/com/wgd/ecg/res/background/Stage_3_Enviroment_Tile.png")); // Loads background texture

        textures.add(loadTexture("src/com/wgd/ecg/res/spritesheets/knight_animation.png"));

//        textures.add(loadTexture("src/com/wgd/ecg/res/spritesheets/knight_animation.png"));

        textures.add(loadTexture("src/com/wgd/ecg/res/spritesheets/skeleton_animation.png"));

        textures.add(loadTexture("src/com/wgd/ecg/res/spritesheets/slime_animation.png"));

        textures.add(loadTexture("src/com/wgd/ecg/res/spritesheets/allgoblins.png"));

        textures.add(loadTexture("src/com/wgd/ecg/res/spritesheets/allghosts.png"));

        textures.add(loadTexture("src/com/wgd/ecg/res/spritesheets/alldevils.png"));

        textures.add(loadTexture("src/com/wgd/ecg/res/spritesheets/allzombies.png"));
    }

    /**
     * This method is used to get the texture
     * @param i   number of texture
     * @return  texture instance
     */
    public static Texture getTexture(int i) {
        return getInstance().textures.get(i);
    }

    /**
     * This method is used to get the instance of the class
     * @return  the instance of the class
     */
    public static InstancedTextures getInstance() {
        return instance;
    }
}
