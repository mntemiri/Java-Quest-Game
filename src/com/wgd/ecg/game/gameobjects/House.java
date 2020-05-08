package com.wgd.ecg.game.gameobjects;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.engine.GameStateManager;
import com.wgd.ecg.game.components.Doorway;
import com.wgd.ecg.game.levels.Dungeon;
import org.jsfml.system.Vector2f;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


/**
 * This class creates the house where the old man will appear
 */
public class House extends Entity {
    public House(float x, float y, float scaleX, float scaleY) {
        super("oldmanhouse.png", x, y, scaleX, scaleY);
        GameEngine.add(this);
        this.setMass(20000f);


        Vector2f entrance = new Dungeon().getDungeonEntrance();
        float height = this.getBoundingBox().height;

        GameEngine.add(new Doorway(x, y + (height / 2), 1.5f, 2f, GameStateManager.HOUSEINSIDE, 0, 300, false));

    }

}
