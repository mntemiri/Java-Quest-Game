package com.wgd.ecg.game.levels;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.engine.GameStateManager;
import com.wgd.ecg.engine.Level;
import com.wgd.ecg.game.GameUtils;
//import com.wgd.ecg.game.components.LevelLoader;
import com.wgd.ecg.game.components.Doorway;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;

import java.util.Random;

/**
 * Dungeon class used for dungeon maps.
 */
public class CastleLevel extends Level {
    @Override
    public void load() {
        super.load();
        //Menu_Button NewGame = new Menu_Button("Go Back (This is the Dungeon)", Centre_X, Centre_Y - 200, true, 0);


        GameSprite bg = new GameSprite("cavestone.png", 0, 0, 2f,2f);
        bg.ZDepth = GameSprite.BACKGROUND;
        GameEngine.add(bg);
        Entity yuh = new Entity("png.png", 0,  -1600, 2f,2f);
        yuh.setMass(2000000f);
        GameEngine.add(yuh);

        Entity wallr = new Entity("cavewallright.png", 1600,  0, 2f,2f);
        wallr.setMass(2000000f);
        GameEngine.add(wallr);
        Entity walll = new Entity("cavewallleft.png", -1600,  0, 2f,2f);
        walll.setMass(2000000f);
        GameEngine.add(walll);

        Entity wallb1 = new Entity("cavewallbottom.png", -960,  1600, 0.85f,2f);
        wallb1.setMass(2000000f);
        GameEngine.add(wallb1);

        Entity wallb2 = new Entity("cavewallbottom.png", 960,  1600, 0.85f,2f);
        wallb2.setMass(2000000f);
        GameEngine.add(wallb2);

        for(int i = 0 ; i < 10 ; i++){
            Entity barrel = new Entity("water.png", new Random().nextInt(1500+1500+1)-1500,  new Random().nextInt(1500+1500+1)-1500, 0.2f,0.2f);
            barrel.setMass(2000000f);
            GameEngine.add(barrel);
        }

        for(int i = 0 ; i < 5 ; i++){
            Entity barrel = new Entity("fire.png", new Random().nextInt(1500+1500+1)-1500,  new Random().nextInt(1500+1500+1)-1500, 0.17f,0.17f);
            barrel.setMass(2000000f);
            GameEngine.add(barrel);
        }

        for(int i = 0 ; i < 30 ; i++){
            Entity barrel = new Entity("boulder.png", new Random().nextInt(1500+1500+1)-1500,  new Random().nextInt(1500+1500+1)-1500, 1.4f,1.4f);
            barrel.setMass(2000000f);
            GameEngine.add(barrel);
        }

    }
}
