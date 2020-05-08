package com.wgd.ecg.game.gameobjects.entities.monsters;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.BossOneAI;
import com.wgd.ecg.game.gameobjects.Collectible;
import com.wgd.ecg.game.gameobjects.Projectile;


/**
 * This class creates the boss 1
 */
public class BossOne extends Monster{
    public BossOne(int xPos, int yPos){
        super(5000, 20, 60, 700, 450, 100, xPos, yPos, 5, 5, "mageboss_animation", true, 4, 1, 0.2f);
        setBrain("bossone");
    }

    @Override
    public void damage(Projectile projectile){
        //Deal huge damage if evil projectile
        if(projectile.isEvil()){
            health.dealDamage(projectile.getDamage()*10);
        }else{
            health.dealDamage(projectile.getDamage());
        }

        if(health.getHealth() <= 0) {
            alive = false;
            die();
            if(healthBar != null){
                healthBar.remove();
            }
        }
    }

    @Override
    public void die(){
        GameEngine.remove(this);
        Audio.resetBGM();
        Audio.playSFX("bossDeath");
        Audio.playBGM("victory");
        brain.removeAll();
        if(GameUtils.getPlayer().hasBeatBossOne()){
            return;
        }else{
            GameUtils.getPlayer().getInventory().addItem("bean");
            GameEngine.add(new Collectible("bean.png", "bean", getXPos(), getYPos(), 1f, 1f, true));
            GameUtils.getPlayer().setBeatBossOne(true);
        }
    }
}
