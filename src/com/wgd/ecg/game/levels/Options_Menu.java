package com.wgd.ecg.game.levels;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.engine.Level;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.UI.*;
import com.wgd.ecg.game.levels.proceduralgen.ProceduralGeneration;

/**
 * Dungeon class used for dungeon maps.
 */
public class Options_Menu extends Level {
    @Override
    public void load() {
        super.load();
        GameSprite bg = new GameSprite("bg.png", Centre_X, Centre_Y, 1,1);
        bg.ZDepth = GameSprite.BACKGROUND;
        GameEngine.add(bg);
        Menu_Button NewGame = new Menu_Button("Go Back", Centre_X, Centre_Y - 75, true, 0);
        Menu_ToggleMuteButton test = new Menu_ToggleMuteButton(Centre_X, Centre_Y, true);
        new FullVolumeControls(Centre_X, Centre_Y + 75, true);
    }
}
