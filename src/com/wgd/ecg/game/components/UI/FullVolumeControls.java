package com.wgd.ecg.game.components.UI;
import com.wgd.ecg.engine.GameEngine;

/**
 * This class creates the full volume controls
 */
public class FullVolumeControls {
    protected static final int Centre_X = GameEngine.WIDTH / 2; //James
    protected static final int Centre_Y = GameEngine.HEIGHT / 2; //James
    public FullVolumeControls(float posX, float posY, boolean addToEngine){
        new Menu_VolumeButton(true, posX + 263, posY, addToEngine, true);
        new Menu_VolumeButton(false, posX - 263 , posY, addToEngine, true);
        new VolumeIndicator(posX, posY, addToEngine);
    }
}
