package com.wgd.ecg.game.gameobjects;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.GameUtils;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;

/**
 * This class creates the pathway
 */
public class Pathway extends GameSprite {
        Color currentColor;
        Color desiredColor;
        float colourAcceleration;
        float colorVelocity;

        boolean isCollidingWithGameObject;

        public Pathway(float x, float y, float scaleX, float scaleY , String spriteTexture ) {
            super(spriteTexture , x, y, scaleX, scaleY);
        }



        @Override
        public void update(RenderWindow window) {
            super.update(window);
            if(isCollidingWithGameObject) {
                if (!GameUtils.isCollidingWithPlayer(this)) {
                    isCollidingWithGameObject = false;
                }
            }
        }
    }
