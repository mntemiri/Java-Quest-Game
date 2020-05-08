package com.wgd.ecg.engine.UI;

import com.wgd.ecg.engine.GameEngine;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class creates a GameUIText
 */
public class GameUIText extends GameUI{
    String displayText;
    public Text text;
    float x, y;

    public GameUIText(String displayText, Font font, float x, float y, int size) {
        this.displayText = displayText;
        //Create some texts with different colors, sizes and styles
        text = new Text(displayText, font, size);
        text.setPosition(x, y);
        ZDepth = UI;
        this.x = x;
        this.y = y;

        text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height / 2);
    }
    public GameUIText(String displayText, String fontName, float x, float y, int size) { //James
        this.displayText = displayText;
        //Font uses string
        Font font = new Font();
        try {
            if(!fontName.contains(".ttf"))
                font.loadFromFile(Paths.get("src/com/wgd/ecg/res/fonts/" + fontName + ".ttf"));
            else
                font.loadFromFile(Paths.get("src/com/wgd/ecg/res/fonts/" + fontName));
        } catch(IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }
        text = new Text(displayText, font, size);
        text.setPosition(x, y);
        ZDepth = UI;
        this.x = x;
        this.y = y;

        text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height / 2);
    }

    public GameUIText(String displayText, float x, float y, int size) {
        Font freeSans = new Font();
        try {
            freeSans.loadFromFile(Paths.get("src/com/wgd/ecg/res/fonts/FreeSans.ttf"));
        } catch(IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }
        this.displayText = displayText;
        //Create some texts with different colors, sizes and styles
        text = new Text(displayText, freeSans, size);
        text.setPosition(x, y);
        ZDepth = UI;
        this.x = x;
        this.y = y;

        text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height / 2);
    }

    public GameUIText(String displayText, int size) {
        Font freeSans = new Font();
        try {
            freeSans.loadFromFile(Paths.get("src/com/wgd/ecg/res/fonts/FreeSans.ttf"));
        } catch(IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }
        this.displayText = displayText;
        //Create some texts with different colors, sizes and styles
        text = new Text(displayText, freeSans, size);
        text.setPosition(0, 0);
        ZDepth = UI;
        this.x = 0;
        this.y = 0;
    }

    public GameUIText(String displayText, float x, float y) {
        Font freeSans = new Font();
        try {
            freeSans.loadFromFile(Paths.get("src/com/wgd/ecg/res/fonts/FreeSans.ttf"));
        } catch(IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }
        this.displayText = displayText;
        //Create some texts with different colors, sizes and styles
        text = new Text(displayText, freeSans, 12);
        text.setPosition(x, y);
        ZDepth = UI;
        this.x = x;
        this.y = y;

        text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height / 2);
    }

    public GameUIText(String displayText) {
        Font freeSans = new Font();
        try {
            freeSans.loadFromFile(Paths.get("src/com/wgd/ecg/res/fonts/FreeSans.ttf"));
        } catch(IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }

        this.displayText = displayText;
        //Create some texts with different colors, sizes and styles
        text = new Text(displayText, freeSans, 12);
        text.setPosition(0, 0);
        ZDepth = UI;
        this.x = 0;
        this.y = 0;

        text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height / 2);
    }

    public GameUIText() {
        Font freeSans = new Font();
        try {
            freeSans.loadFromFile(Paths.get("src/com/wgd/ecg/res/fonts/FreeSans.ttf"));
        } catch(IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }

        this.displayText = "";
        //Create some texts with different colors, sizes and styles
        text = new Text(displayText, freeSans, 18);
        text.setPosition(0, 0);
        ZDepth = UI;
        this.x = 0;
        this.y = 0;
    }

    /**
     * this method sets the x and y position of text ui
     * @param x x position
     * @param y y position
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }


    /**
     * This method sets the Vector position of text
     * @param pos
     */
    public void setPosition(Vector2f pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    /**
     * This method sets the string of the text
     * @param string
     */
    public void setString(String string) {
        text.setString(string);
        displayText = string;
        //text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height / 2); //James added to stop text going off center when updated
    }

    /**
     * This method creates the text in centre
     * @param string
     */
    public void setStringCenter(String string) { //James - added this so i didnt mess with the rest of the games text (i needed to center when setString is called)
        text.setString(string);
        displayText = string;
        text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height / 2); //James added to stop text going off center when updated
    }

    /**
     * This method updates the position of text
     */
    private void updatePosition() {
        if (followView)
            text.setPosition(Vector2f.add(new Vector2f(GameEngine.boundingBox.left + GameEngine.boundingBox.width / 2, GameEngine.boundingBox.top + GameEngine.boundingBox.height / 2), new Vector2f(x, y)));
        else
            text.setPosition(new Vector2f(x, y));
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        updatePosition();
        window.draw(text);
    }
}
