package com.wgd.ecg.game.gameobjects;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;
import org.jsfml.graphics.RenderWindow;

import java.util.ArrayList;

/**
 * This class creates the damage box of the sprites
 */
public class Damagebox extends GameSprite{
    private int damage;
    private ArrayList<Monster> alreadyHit = new ArrayList<Monster>();
    public Damagebox(String spriteTexture, float x, float y, float scaleX, float scaleY, int damage){
        super(spriteTexture, x,  y,  scaleX,  scaleY);
        this.damage = damage;
    }

    /**
     * This method checls for collision to update the damage box of sprites
     */
    public void updateCollisions(){
        //Check not colliding with any monsters
        ArrayList<GameSprite> collidingWith = GameUtils.checkCollisionAllGameSprites(this);
        for (GameSprite collidingSprite : collidingWith)
        {
            if(collidingSprite instanceof Monster){
                if (alreadyHit.contains(collidingSprite)){
                    return;
                }
                //To do: proper damage system
                System.out.println("Hit!");
                ((Monster) collidingSprite).hit();
                ((Monster) collidingSprite).damage(this.damage);
                ((Monster) collidingSprite).updateHealthBar();
                alreadyHit.add((Monster) collidingSprite);
                /**
                 * Add force?
                 */
            }
        }
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        updateCollisions();
    }

}

