package com.wgd.ecg.game.components;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;
import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2f;

public class MobHealthBar{
    GameSprite greenOverlay;
    GameSprite base;
    Monster target;
    int width = 80;
    int height = 4;
    float targetHeight;
    float yPos;
    float xPos;

    Vector2f position;

    float interpolationSpeed = 20f;

    /**
     * Creates the health bar
     * @param target The monster this health bar is for
     */
    public MobHealthBar(Monster target){
        //Check target is alive
        if(target.getHP() <= 0){
            return;
        }
        this.targetHeight = (float) (target.getBoundingBox().height * 0.6);
        this.xPos = target.getXPos();
        this.yPos = target.getYPos() - targetHeight;

        position = new Vector2f(target.getXPos(), target.getYPos() - targetHeight);
        base = new GameSprite("redbar.png", this.xPos, this.yPos , 1, 1);
        GameEngine.add(base);
        greenOverlay = new GameSprite("greenbar.png", this.xPos, this.yPos , 1, 1);
        GameEngine.add(greenOverlay);
        this.target = target;

        update();
    }

    /**
     * Updates how much of the red overlay to show based off the percentage of health remaining.
     * Called only when taking damage
     */
    public void update(){
        //Update display based on remaining health
        try {
            float remainder = ((float)target.getHP()) / target.getMaxHP();
            greenOverlay.sprite.setTextureRect(new IntRect(0, 0, (int) (remainder * width), 4));
            updatePos();
        }
        catch(Exception e) {
            ;
        }
    }

    /**
     * Updates the position of the health bar.
     * Called every update by the monster.
     */
    public void updatePos(){
        Vector2f targetPos = new Vector2f(target.getXPos(), target.getYPos() - targetHeight);

//        position = new Vector2f(target.getXPos(), target.getYPos() - targetHeight);

        position = (Vector2f.add(position, Vector2f.mul(Vector2f.sub(targetPos, position), GameEngine.deltaTime * interpolationSpeed)));

        greenOverlay.sprite.setPosition(position);
        base.sprite.setPosition(position);
    }

    /**
     * Removes the health bar from the display
     */
    public void remove(){
        GameEngine.remove(base);
        GameEngine.remove(greenOverlay);
    }
}
