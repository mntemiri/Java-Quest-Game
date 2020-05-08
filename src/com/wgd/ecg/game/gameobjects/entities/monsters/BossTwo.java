package com.wgd.ecg.game.gameobjects.entities.monsters;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.BossTwoAI;
import com.wgd.ecg.game.gameobjects.Collectible;

/**
 * This class creates the boss 2
 */
public class BossTwo extends Monster{
    public BossTwo(int xPos, int yPos) {
        super(10000, 200, 100,10000, 300, 300, xPos, yPos, 10, 10, "BossTwo_animation", false, 8, 4, 0.1f);
        setBrain("bosstwo");
    }

    @Override
    public void die(){
        GameEngine.remove(this);
        Audio.playSFX("bossDeath");
        Audio.playBGM("victory");
        if(GameUtils.getPlayer().hasBeatBossTwo()){
            return;
        }else{
            GameUtils.getPlayer().getInventory().addItem("bean");
            GameEngine.add(new Collectible("bean.png", "bean", getXPos(), getYPos(), 1f, 1f, true));
            GameUtils.getPlayer().setBeatBossTwo(true);
        }
    }
}
