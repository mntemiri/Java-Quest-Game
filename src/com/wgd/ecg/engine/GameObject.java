package com.wgd.ecg.engine;

import org.jsfml.graphics.RenderWindow;

import java.util.UUID;

/**
 * Base class of all entities in game
 * Only contains two things
 * ZDepth = Sets the rendering layer, so whats rendered first and last
 * ID = Automatically set when instanced or extended, will be unique ID to GameObject to remove it and for other means
 */
public class GameObject {
    public int ZDepth;
    public final UUID ID;
    public boolean removeOnClear;

    public static int BACKGROUND = 0;
    public static int FOREGROUND = 1;
    public static int UI = 99;

    /**
     * Constructor to set ZDepth to 1 by default
     */
    public GameObject() {
        ZDepth = 1;
        ID = generateUUID();
        removeOnClear = true;
    }

    /**
     * Constructor to set ZDepth
     * @param ZDepth
     */
    public GameObject(int ZDepth) {
        this.ZDepth = ZDepth;
        ID = generateUUID();
    }

    /**
     * used to generate random ID of
     * @return random generated UUID
     */
    private static UUID generateUUID() {
        UUID idOne = UUID.randomUUID();
        return idOne;
    }

    /**
     * updates every frame
     * @param window used to draw sprite
     */
    public void update(RenderWindow window) {}

    public void onRemove(){

    }
}
