package com.wgd.ecg.game.gameobjects.generation;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.generation.InstancedTextures;
import com.wgd.ecg.game.gameobjects.Entity;
import org.jsfml.graphics.RenderWindow;

public class Background extends GeneratedObject {

    private int chunkSize;

    public Background(int backgroundType, float x, float y, int chunkSize) {
        super(InstancedTextures.getTexture(backgroundType), x, y, 1, 1, chunkSize);
        this.ZDepth = BACKGROUND;
        this.chunkSize = chunkSize;
    }

    public Background(int backgroundType, float x, float y, float rotation, int chunkSize) {
        super(InstancedTextures.getTexture(backgroundType), x, y, 1, 1, chunkSize);
        this.sprite.setRotation(rotation);
        this.ZDepth = BACKGROUND;
        this.chunkSize = chunkSize;
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
//        removeSelfIfFarFromPlayer();
    }
}
