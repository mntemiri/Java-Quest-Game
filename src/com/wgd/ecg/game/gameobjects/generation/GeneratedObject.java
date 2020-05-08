package com.wgd.ecg.game.gameobjects.generation;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameObject;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.Entity;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This class is used to generate objects in chunk
 */
public class GeneratedObject extends GameSprite {

    private int chunkSize;

    public GeneratedObject(Texture texture, float x, float y, int chunkSize) {
        super(texture, x, y);

        this.chunkSize = chunkSize;
        checkIfObjectExistsInPlace();
    }

    public GeneratedObject(Texture texture, float x, float y, float scaleX, float scaleY, int chunkSize) {
        super(texture, x, y, scaleX, scaleY);

        this.chunkSize = chunkSize;
        checkIfObjectExistsInPlace();
    }

    void checkIfObjectExistsInPlace() {
        for(GameSprite gSprite : GameUtils.checkCollisionAllGameSprites(this))
            if(gSprite.getPosition().x == getPosition().x && gSprite.getPosition().y == getPosition().y)
                GameEngine.remove(this);
    }

    /**
     * This method removes chunk of sprites  if its position is far from player
     */
    protected void removeSelfIfFarFromPlayer() {
        try {
            if ((Entity.findMagnitude(GameUtils.playerPosition()) - Entity.findMagnitude(this.getPosition())) > chunkSize * 2) {
                GameEngine.remove(this);
            }
        }
        catch (Exception e) {
            System.out.println("Collision when removing Background");
        }
    }
}
