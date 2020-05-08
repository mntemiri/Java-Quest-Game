package com.wgd.ecg.game.gameobjects;

import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.GameUtils;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import java.io.Serializable;

/**
 * This class creates the weapons
 */
public class Weapon extends GameSprite implements Serializable {
    int offset;
    public Weapon(String spriteTexture, float x, float y, float scaleX, float scaleY, int offset){
        super(spriteTexture, x, y, scaleX, scaleY);
        this.offset = offset;
        ZDepth = 2;
    }

    public Weapon(String spriteTexture, float x, float y, float scaleX, float scaleY, int offset, int columnCount, int rowCount){
        super(spriteTexture, x, y, scaleX, scaleY, columnCount, rowCount, 1000000000f);
        System.out.println("Animated");
        this.offset = offset;
        ZDepth = 2;
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        rotateTowardsMouse();
        setPosPlayer();
    }

    /**
     * Rotates the weapon towards the mouse
     */
    public void rotateTowardsMouse(){
        double angle = GameUtils.getMouseAngle();
        sprite.setRotation(-1 * (int) angle);
    }

    /**
     * Sets the weapons position to the player
     */
    public void setPosPlayer(){
        Vector2f playerPos = GameUtils.playerPosition();
        Vector2f position = new Vector2f(playerPos.x, playerPos.y + offset);
        sprite.setPosition(position);
    }
}
