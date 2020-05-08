package com.wgd.ecg.game.gameobjects;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.GameUtils;
import org.jsfml.graphics.RenderWindow;

/**
 * This class creates collectible  items suck as potions
 */
public class Collectible extends Entity {
    private String type;
    private boolean fakeCollect;
    public Collectible(String spriteTexture, String type, float x, float y, float scaleX, float scaleY){
        super(spriteTexture, x, y, scaleX, scaleY);
        this.type = type;
    }

    /**
     *
     * @param spriteTexture
     * @param type
     * @param x
     * @param y
     * @param scaleX
     * @param scaleY
     * @param fakeCollect whether or not to actually add the item to inventory on collection used for bean
     */
    public Collectible(String spriteTexture, String type, float x, float y, float scaleX, float scaleY, boolean fakeCollect){
        super(spriteTexture, x, y, scaleX, scaleY);
        this.type = type;
        this.fakeCollect = fakeCollect;
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        //If player has collected add the item to inventory
        if(GameUtils.isCollidingWithPlayer(this)){
            if(fakeCollect == false){
                GameUtils.getPlayer().getInventory().addItem(type);
            }
            GameEngine.remove(this);
        }
    }
}
