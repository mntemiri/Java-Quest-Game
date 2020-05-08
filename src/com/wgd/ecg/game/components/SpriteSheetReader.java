package com.wgd.ecg.game.components;

import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameObject;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class creates a spriteshet reader
 */
public class SpriteSheetReader extends GameObject {

    private String theSprite;
    private int row ;
    private int col;
    private int width;
    private int height;

    int rowCount = 0;
    int columnCount = 0;
    int maxFrameCount = 0;
    int frame = 0 ;
    double lastFrameTime = 0;

    public SpriteSheetReader(String thesprite , int row , int col , int width , int height){
        this.theSprite = thesprite;
        this.row = row;
        this.col = col;
        this.width = width;
        spriteSheetReader();
    }

    void OnAnimationFinished() {

    }


    /**
     * This method reads the spritesheet  to do the animations , rows and columns
     */
    private void spriteSheetReader() {
        Texture texture = new Texture();
        try {
            texture.loadFromFile(Paths.get("src/com/wgd/ecg/res/images/" + theSprite));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sprite sprite = new Sprite(texture);
        sprite.setTextureRect(new IntRect(row, col, width, height));

        if(GameEngine.window.isOpen()) {
            if (GameClock.timeInSEC() - lastFrameTime >= 1) {
                //Restart the clock
                lastFrameTime = GameClock.timeInSEC();
                frame++;
                if (frame > maxFrameCount) {
                    OnAnimationFinished();
                    frame = 0;
                }
                int frameRow = frame / rowCount;
                int frameCol = frame % columnCount;
                sprite.setTextureRect(new IntRect(frameCol * width, frameRow * height, width, height));
            }
        }
    }
}