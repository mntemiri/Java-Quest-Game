package com.wgd.ecg.game.gameobjects.entities.monsters;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.components.MiniBossAI;
import com.wgd.ecg.game.gameobjects.Collectible;
import org.jsfml.system.Vector2f;

import java.util.Random;


/**
 * This class creates a bat
 */
public class Bat extends Monster{
    MiniBossAI master = null;
    public Bat(int xPos, int yPos, MiniBossAI master){
        super(50, 10, 150, 100000, 100, 1000, xPos, yPos, 4, 4, "Bat_animation", false, 3,  1,  0.05f);
        this.master = master;
    }

    public Bat(int xPos, int yPos){
        super(30, 10, 150, 500, 100, 1000, xPos, yPos, 4, 4, "Bat_animation", false, 3,  1,  0.05f);
    }

    @Override
    public void die(){
        Audio.playSFX("bossDeath");
        GameEngine.remove(this);

        //Drop 25% of the time
        boolean drop = new Random().nextInt(5)>3;
        if(drop){
            //Select random potion
            String[] potions = {"health", "strength", "speed"};
            String type = potions[new Random().nextInt(2)];
            String texture = "potion" + type + ".png";

            //Add to monsters position
            Vector2f pos = sprite.getPosition();
            GameEngine.add(new Collectible(texture, type, pos.x, pos.y, 2, 2));
        }
        addXP();

        if(master != null) {
            if(master.minionsAlive > 0) {
                master.minionsAlive--;
            }
        }
    }
}
