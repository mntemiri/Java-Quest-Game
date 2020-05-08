package com.wgd.ecg.game.gameobjects.entities.monsters;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.Collectible;

/**
 * This class creates the boss 3
 */
public class BossThree extends Monster{
    public BossThree(int xPos, int yPos) {
        super(20000, 200, 100,10000, 200, 300, xPos, yPos, 10, 10, "BossThree_animation",false, 6, 4, 0.8f);
        setBrain("bossthree");

        playAnimation(1);
    }

    @Override
    public void die(){
        GameEngine.remove(this);
        Audio.resetBGM();
        Audio.playSFX("bossDeath");
        Audio.playBGM("victory");
        brain.removeAll();
        if(GameUtils.getPlayer().hasBeatBossThree()){
            return;
        }else{
            GameUtils.getPlayer().getInventory().addItem("bean");
            GameEngine.add(new Collectible("bean.png", "bean", getXPos(), getYPos(), 1f, 1f, true));
            GameUtils.getPlayer().setBeatBossThree(true);
        }
    }
}
