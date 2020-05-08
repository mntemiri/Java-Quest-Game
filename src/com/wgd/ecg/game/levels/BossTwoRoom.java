package com.wgd.ecg.game.levels;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.engine.Level;
import com.wgd.ecg.game.components.Doorway;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.entities.monsters.BossOne;
import com.wgd.ecg.game.gameobjects.entities.monsters.BossTwo;

//import com.wgd.ecg.game.components.LevelLoader;

/**
 * Room for boss one
 */
public class BossTwoRoom extends Level {
    @Override
    public void load() {
        super.load();
        Audio.queueBGM("Through_The_Fire_and_Flames.ogg");
        GameSprite bg = new GameSprite("cavestone.png", 0, 0, 5f,5f);
        bg.ZDepth = GameSprite.BACKGROUND;
        float widthTwo = new GameSprite("dungeon_2.png", 0, 0, 5f, 5f).getBoundingBox().width;
        float heightTwo = new GameSprite("dungeon_2.png", 0, 0, 5f, 5f).getBoundingBox().height;
        float widthOne = new GameSprite("dungeon_1.png", 0, 0, 5f, 5f).getBoundingBox().width;
        float heightOne = new GameSprite("dungeon_1.png", 0, 0, 5f, 5f).getBoundingBox().height;
        float backHeight = new GameSprite("dungeon_back.png", 0, 0, 5f, 5f).getBoundingBox().height;

        Entity back = new Entity("dungeon_back.png", 0, -1* (heightOne + heightTwo - (backHeight)), 5f, 5f);
        Entity left = new Entity("dungeon_1.png", -1 * (back.getBoundingBox().width/2) + widthOne/2, 0, 5f, 5f);
        Entity right = new Entity("dungeon_1_f.png", (back.getBoundingBox().width/2) - widthOne/2, 0, 5f, 5f);
        Entity left2 = new Entity("dungeon_2.png", -1 * (back.getBoundingBox().width/2 - widthTwo/2), 0 - left.getBoundingBox().height/2- (heightTwo/2), 5f, 5f);
        Entity right2 = new Entity("dungeon_2_f.png", (back.getBoundingBox().width/2) - widthTwo/2, 0 - left.getBoundingBox().height/2 - (heightTwo/2), 5f, 5f);


        left.setMass(2000000f);
        right.setMass(2000000f);
        back.setMass(2000000f);
        left2.setMass(2000000f);
        right2.setMass(2000000f);
        GameEngine.add(left);
        GameEngine.add(right);
        GameEngine.add(back);
        GameEngine.add(left2);
        GameEngine.add(right2);

        BossTwo boss = new BossTwo(0, -1000);
        GameEngine.add(boss);
        GameEngine.add(new Doorway(0, 250, 6f, 2f, false));
    }
}
