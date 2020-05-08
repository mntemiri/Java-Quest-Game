package com.wgd.ecg.game.gameobjects;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.GameUtils;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

/**
 * This class creates the fps counter
 */
public class FPSCounter extends GameUIText {

    public FPSCounter() {
        removeOnClear = false;
        setPosition(Vector2f.sub(GameEngine.view.getCenter(), GameEngine.view.getSize()));
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        setString("FPS: " + (int)GameEngine.fps);
    }
}
