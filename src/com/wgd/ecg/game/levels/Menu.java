package com.wgd.ecg.game.levels;

import com.wgd.ecg.engine.*;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.UI.Menu_Button;
import com.wgd.ecg.game.components.UI.Menu_QuitButton;
import com.wgd.ecg.game.gameobjects.generation.Background;
import com.wgd.ecg.game.gameobjects.generation.Tree;
import com.wgd.ecg.game.levels.proceduralgen.ProceduralGeneration;

/**
 * This class creates the menu
 */
public class Menu extends Level {
    @Override
    public void load() {
        super.load();
        GameUtils.resetSeed();
        GameEngine.setTimeScale(1);
        Camera camera = new Camera();
        camera.setPos(Centre_X,Centre_Y);

        GameSprite bg = new GameSprite("bg.png", Centre_X, Centre_Y, 1,1);
        GameEngine.add(bg);

        GameSprite logo = new GameSprite("Temp_Logo.png", Centre_X, Centre_Y -195, 1f,1f);
        GameEngine.add(logo);

        Menu_Button NewGame = new Menu_Button("New Game", Centre_X, Centre_Y, true, GameStateManager.HOUSEINSIDE);
        //Menu_Button Continue  = new Menu_Button("Continue Game", Centre_X, Centre_Y + 75, true, GameStateManager.MENUSTATE);
        Menu_Button Options = new Menu_Button("Options", Centre_X, Centre_Y + 75, true, 3);
        Menu_Button Controls = new Menu_Button("Controls", Centre_X, Centre_Y + 150, true, 9);
        Menu_QuitButton exit = new Menu_QuitButton(Centre_X, Centre_Y + 225, true);
    }
}
