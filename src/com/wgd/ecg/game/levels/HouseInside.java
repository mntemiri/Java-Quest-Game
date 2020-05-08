package com.wgd.ecg.game.levels;

import com.wgd.ecg.engine.*;
import com.wgd.ecg.game.components.Doorway;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.entities.heroes.OldMan;
import com.wgd.ecg.game.gameobjects.entities.monsters.BossOne;

import java.util.Random;

/**
 * This class creates the old man house level
 */
public class HouseInside extends Level {
    @Override
    public void load() {
        super.load();
//        float widthTwo = new GameSprite("houseright.png", 0, 0, 5f, 5f).getBoundingBox().width;
//        float heightTwo = new GameSprite("dungeon_2.png", 0, 0, 5f, 5f).getBoundingBox().height;
//        float widthOne = new GameSprite("dungeon_1.png", 0, 0, 5f, 5f).getBoundingBox().width;
//        float heightOne = new GameSprite("dungeon_1.png", 0, 0, 5f, 5f).getBoundingBox().height;
//        float backHeight = new GameSprite("dungeon_back.png", 0, 0, 5f, 5f).getBoundingBox().height;
//
//        Entity back = new Entity("dungeon_back.png", 0, -1* (heightOne + heightTwo - (backHeight)), 5f, 5f);
//        Entity left = new Entity("dungeon_1.png", -1 * (back.getBoundingBox().width/2) + widthOne/2, 0, 5f, 5f);
//        Entity right = new Entity("dungeon_1_f.png", (back.getBoundingBox().width/2) - widthOne/2, 0, 5f, 5f);
//        Entity left2 = new Entity("dungeon_2.png", -1 * (back.getBoundingBox().width/2 - widthTwo/2), 0 - left.getBoundingBox().height/2- (heightTwo/2), 5f, 5f);
//        Entity right2 = new Entity("dungeon_2_f.png", (back.getBoundingBox().width/2) - widthTwo/2, 0 - left.getBoundingBox().height/2 - (heightTwo/2), 5f, 5f);
        String[] cups = {"cupwhite.png", "cupblue.png"};
        Random random = new Random();
        for(int i = 0; i<10; i++){
            int x = random.nextInt(400) - 200;
            int y = random.nextInt(600);
            Entity cup = new Entity(cups[i%2], x, -1 * y, 5f, 5f);
            cup.setMass(50f);
            GameEngine.add(cup);
            cup.ZDepth = 2;
        }

        Entity back = new Entity("housefront.png", 0, -420, 5f, 5f);
        Entity right = new Entity("houseright.png", 500, 0, 5f, 5f);
        Entity left = new Entity("houseleft.png", -510, 0, 5f, 5f);
        GameSprite carpet = new GameSprite("carpet.png", 0, 0, 5f, 5f);
        Entity stand = new Entity("stand.png", -200, -200, 5f, 5f);
        GameSprite background = new GameSprite("tile.png", 0, 0, 5f, 5f);
        background.ZDepth = GameSprite.BACKGROUND;
        back.ZDepth = 1;
        right.ZDepth = 2;
        left.ZDepth = 2;
        carpet.ZDepth = 1;
        stand.ZDepth = 1;

        stand.setMass(100f);
        left.setMass(2000000f);
        right.setMass(2000000f);
        back.setMass(2000000f);
        GameEngine.add(left);
        GameEngine.add(right);
        GameEngine.add(back);
        GameEngine.add(background);
        GameEngine.add(carpet);
        GameEngine.add(stand);

        GameEngine.add(new OldMan(0f, 0f));


        GameEngine.add(new Doorway(0, 500, 6f, 2f, false));
    }
}
