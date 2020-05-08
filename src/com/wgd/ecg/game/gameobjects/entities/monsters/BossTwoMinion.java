package com.wgd.ecg.game.gameobjects.entities.monsters;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.components.BossTwoAI;
import com.wgd.ecg.game.gameobjects.Collectible;
import org.jsfml.system.Vector2f;

import java.util.Random;

public class BossTwoMinion extends Monster{

    /**
     * This class creates the boss 2 minions
     */
    BossTwoAI master;
    public BossTwoMinion(BossTwoAI master){
        super(50, 5, 100, 100000, 50, 100, (int)master.host.getXPos() + 100 + new Random().nextInt(100), (int)master.host.getYPos() + 100 + new Random().nextInt(100), 4, 4, "BossTwoMinion_animation",  false, 8,  1,  0.05f);

        this.master = master;
    }

    @Override
    public void die(){
        super.die();

        if(master.minionsAlive > 0) {
            master.minionsAlive--;
        }
    }
}
