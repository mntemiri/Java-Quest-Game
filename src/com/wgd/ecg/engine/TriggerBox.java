package com.wgd.ecg.engine;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.engine.GameStateManager;
import com.wgd.ecg.game.GameUtils;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;

public class TriggerBox extends GameSprite {
    /**
     * This class creates a trigger box to check for ollision when changing levels
     * @param x x pos
     * @param y y pos
     * @param sizeX size x
     */

    public TriggerBox(float x, float y, float sizeX, float sizeY){
        super("greenbar.png" , x , y , sizeX, sizeY);
        setBoundingBox(new FloatRect(x, y, sizeX, 1.5f));

    }

    public void OnTrigger() {

    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        if (GameUtils.isCollidingWithPlayer(this)) {
            OnTrigger();
        }
    }

}