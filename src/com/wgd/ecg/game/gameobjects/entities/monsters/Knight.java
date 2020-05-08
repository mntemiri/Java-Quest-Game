package com.wgd.ecg.game.gameobjects.entities.monsters;


import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.GameUtils;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

/**
 * This class creates a knight
 */
public class Knight extends Monster{
    public static float spawnRate = 10f;
    public Knight(int xPos, int yPos, int level){
        super(800, 30, 30, 500, 100, 1500, xPos, yPos, 4, 4, "knight_animation", false, 6,  2,  0.5f);

        setBrain("knight");

        spawnRate = 1f;
        playAnimation(1);
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        collider.updateCollider(sprite);
        collider.isCollidingApplyForce(this);
        physicsUpdate();
        brain.brainUpdate();
        if(healthBar != null){
            healthBar.update();
        }
    }
}