package com.wgd.ecg.game.components.UI;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.UI.GameUI;
import com.wgd.ecg.engine.UI.GameUISprite;
import com.wgd.ecg.engine.UI.GameUIText;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class creates a volume indicator
 */
public class VolumeIndicator extends GameUISprite {
    IntRect Max_Scale;
    GameUISprite backplate;
    GameUIText text;
    public VolumeIndicator(float posX,float posY, boolean addToEngine){
        super("greenbar.png",posX,posY,5F, 10F);
        this.Max_Scale = this.sprite.getTextureRect();
        backplate  = new GameUISprite("redbar.png", posX, posY, 5f,10f);
        text = addLabel("Volume", posX, posY);
        if(addToEngine)
            GameEngine.add(backplate);
            GameEngine.add(this);
            GameEngine.add(text);
    }

    /**
     * This methods adds label to the buton
     * @param ButtonText  text
     * @param x  x position
     * @param y  y position
     * @return  the label text
     */
    private GameUIText addLabel(String ButtonText, float x, float y){
        Font temp = new Font();
        try {
            temp.loadFromFile(Paths.get("src/com/wgd/ecg/res/fonts/NewRocker.ttf"));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return new GameUIText(ButtonText, temp, x-640, y-366, 25); //366 to offset the y
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        float remainder = ((float)Audio.getMaster_Volume() / 100);
        this.sprite.setTextureRect(new IntRect(0, 0, (int) (remainder * 80), 4));
        window.draw(sprite);
        //followPlayer();
    }
}
