package com.wgd.ecg.game.gameobjects.generation;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.generation.InstancedTextures;
import com.wgd.ecg.game.gameobjects.Castle;
import com.wgd.ecg.game.gameobjects.Cave;
import com.wgd.ecg.game.gameobjects.Entity;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;

/**
 * This class creates the tree game sprites
 */
public class Tree extends GeneratedObject {
    Color currentColor;
    Color desiredColor;
    float colourAcceleration;
    float colorVelocity;

    boolean isCollidingWithGameObject;
    private int chunkSize;
    boolean deletionCheck = false;

    public Tree(float x, float y, int chunkSize) {
        super(InstancedTextures.getTexture(InstancedTextures.TREE), x, y, 4f, 4f, chunkSize);
        this.chunkSize = chunkSize;
        desiredColor = currentColor = new Color(255, 255, 255, 255);
        colourAcceleration = 3500f;
        colorVelocity = 0f;
        this.ZDepth = 4;
    }


    /**
     * This method increases the trees transperancy in case of collision
     */
    public void transparencyIncrease() {
        isCollidingWithGameObject = true;
        colorVelocity += colourAcceleration * GameEngine.deltaTime;

        if (currentColor.a > 50) {
            currentColor = new Color(255, 255, 255, currentColor.a - (int) (colorVelocity * GameEngine.deltaTime));
        }
        else {
            currentColor = new Color(255, 255, 255, 50);
            colorVelocity = 0;
        }
        sprite.setColor(currentColor);
    }

    private void transparencyDecrease() {
        colorVelocity += colourAcceleration* GameEngine.deltaTime;

        if (currentColor.a < 255) {
            currentColor = new Color(255, 255, 255, currentColor.a + (int) (colorVelocity * GameEngine.deltaTime));
        }
        else {
            currentColor = new Color(255, 255, 255, 255);
            colorVelocity = 0;
        }
        sprite.setColor(currentColor);
    }


    /**
     * This method checks if there is collision between the trees and other sprites
     */
    public void checkIfCollision() {
        for (GameSprite gamesprite: GameUtils.checkCollisionAllGameSprites(this)) {
            System.out.println("DELETED");
            if((gamesprite instanceof Castle) || (gamesprite instanceof Cave))
                GameEngine.remove(this);
        }
        deletionCheck = true;
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        if(!isCollidingWithGameObject && currentColor != desiredColor)
            transparencyDecrease();

        if(isCollidingWithGameObject) {
            if (!GameUtils.isCollidingWithPlayer(this)) {
                isCollidingWithGameObject = false;
            }
        }
//        if(!deletionCheck)
//            checkIfCollision();
//        removeSelfIfFarFromPlayer();
    }
}
