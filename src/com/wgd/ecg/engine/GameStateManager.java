package com.wgd.ecg.engine;

import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.levels.*;
import com.wgd.ecg.game.levels.CastleLevel;

/**
 * GameStateManager class used for changing level and storing their states.
 */
public class GameStateManager {
    private Level GAMESTATE; // CURRENT LEVEL
    private static int STATE; // CURRENT LEVEL STATE NUMBER

    // POSSIBLE STATES OF GAME
    public static int MENUSTATE = 0;
    public static int OVERWORLDSTATE = 1;
    public static int DUNGEONSTATE = 2;
    public static int OPTIONS_MENU = 3;
    public static int CASTLE = 4;
    public static int BOSSONE = 5;
    public static int BOSSTWO = 6;
    public static int BOSSTHREE = 7;
    public static int HOUSEINSIDE = 8;
    public static int CONTROLS_MENU = 9;

    // VARIABLE TO SEE IF LEVEL LOADED
    public boolean levelLoaded;

    public GameStateManager() {;
        levelLoaded = false;
    }

    /**
     * Changes game engine level
     * @param state Use GameStateManager static variables for levels
     */
    public void changeGameWorld(int state) {
        if (state == MENUSTATE)
            GAMESTATE = new Menu();
        else if (state == OVERWORLDSTATE)
            GAMESTATE = new Overworld();
        else if (state == DUNGEONSTATE)
            GAMESTATE = new Dungeon();
        else if (state == OPTIONS_MENU)
            GAMESTATE = new Options_Menu();
        else if (state == CASTLE)
            GAMESTATE = new CastleLevel();
        else if (state == BOSSONE)
            GAMESTATE = new BossOneRoom();
        else if (state == BOSSTWO)
            GAMESTATE = new BossTwoRoom();
        else if(state == BOSSTHREE)
            GAMESTATE = new BossThreeRoom();
        else if (state == HOUSEINSIDE)
            GAMESTATE = new HouseInside();
        else if(state == CONTROLS_MENU)
            GAMESTATE = new Controls_Menu();
        this.STATE = state;

        levelLoaded = false;
    }

    /**
     * Gets current stored level
     * @return level
     */
    public Level getCurrentGameWorld() {
        return GAMESTATE;
    }

    /**
     * Gets stored integer state
     * @return integer state of level
     */
    public static int getCurrentState() {
        return STATE;
    }


    /**
     * Loads current GAMESTATE level
     */
    public void loadWorld() {
        GAMESTATE.load();
        GameUtils.updatePlayer();
    }
}
