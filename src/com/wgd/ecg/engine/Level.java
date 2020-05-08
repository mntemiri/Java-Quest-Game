package com.wgd.ecg.engine;

/**
 * Base class for Level, used for extending and making levels.
 */
public class Level {
    protected static final int Centre_X = GameEngine.WIDTH / 2; //James
    protected static final int Centre_Y = GameEngine.HEIGHT / 2; //James
    protected GameEngine engine;

    /**
     * initialisation of level
     */
    public Level() {
        engine = GameEngine.getInstance();
    }

    /**
     * used to load level
     */
    public void load() {
        engine.clear();
    }
}
