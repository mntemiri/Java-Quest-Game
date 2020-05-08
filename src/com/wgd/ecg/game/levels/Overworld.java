package com.wgd.ecg.game.levels;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.engine.Level;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.SaveSystem;
import com.wgd.ecg.game.gameobjects.*;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;
import com.wgd.ecg.game.gameobjects.entities.monsters.*;
import com.wgd.ecg.game.levels.proceduralgen.ProceduralGeneration;

/**
 * Overworld class map used for main world generation.
 */
public class Overworld extends Level {
    @Override
    public void load() {
        super.load();

        //BufferedImage img = null;
        GameEngine.add(new Pathway(-300, 100, 2f, 2f, "ground1.png"));
        GameEngine.add(new Pathway(-70, 100, 2f, 2f, "ground1.png"));
        GameEngine.add(new Pathway(190, 30, 0.8f, 0.8f, "thelake.png"));

        GameEngine.add(new FPSCounter());

        GameSprite bg = new GameSprite("tile1.png", 0, 0, 1000f,1000f);
        bg.ZDepth = GameSprite.BACKGROUND;
        GameEngine.add(bg);
        Entity rock = new Entity("rock.png" , -300, -100, 0.2f , 0.2f);
        //Entity cave = new Entity("castle.png" , 300 , -300, 2.3f , 2.3f);
//        Castle castle = new Castle( -400 , -400, 1.5f , 1.5f ,"castle1.png" , 4);
//        Cave c = new Cave( 1000 , 30, 2.7f , 2.7f , "cave.png" , 2);
//        GameEngine.add(castle);
//        GameEngine.add(c);
//        GameEngine.add(new Tree(-100, -140, 1.5f, 1.5f, "newbush.png"));
        rock.setMass(2000000f);
        GameEngine.add(rock);

        ProceduralGeneration generation = new ProceduralGeneration(GameUtils.generateSeed());
        GameEngine.add(generation);

        GameUtils.buildSpawn();
    }
}
