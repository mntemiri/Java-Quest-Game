package com.wgd.ecg.game.components.UI;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.UI.GameUIButton;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.GameUtils;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;

import java.io.IOException;
import java.nio.file.Paths;

public class Menu_VolumeButton extends GameUIButton {


    private boolean HoverDelay = true;
    private boolean In_Engine = false;
    private GameUIText text;
    private boolean isStatic = true;
    private boolean Increase;
    private String ButtonText;

    public Menu_VolumeButton(Boolean increase, float x, float y, boolean addToEngine, boolean isStatic) {
        super("VolumeButton.jpg", x, y, 1f, 1f);
        this.In_Engine = addToEngine;
        this.isStatic = isStatic;
        this.Increase = increase;
        ButtonText = "-";
        if(Increase)
            ButtonText = "+";

        setHoverColour(new Color(128, 128, 128, 255));

        if (!this.isStatic)
        {
            toggleFollowView();
            text = addButtonText(ButtonText,x+640,y+360); //offstets to recentre text
        }
        else {
            text = addButtonText(ButtonText,x,y);
        }
        if(In_Engine) {
            GameEngine.add(this);
            GameEngine.add(text);
        }
    }

    @Override
    public void onHover(){
        if(HoverDelay & isHovering){
            Audio.playSFX("Standard_Select");
            HoverDelay = false;
        }
        if(!isHovering)
            HoverDelay = true;
    }

    @Override
    public void onReleased(){
        if(Increase)
            Audio.setMasterVolume(Audio.getBGM_Volume() + 10);
        else
            Audio.setMasterVolume(Audio.getBGM_Volume() - 10);
        Audio.playSFX("Retro_Selection");
    }

    private GameUIText addButtonText(String ButtonText, float x, float y){
        Font temp = new Font();
        try {
            temp.loadFromFile(Paths.get("src/com/wgd/ecg/res/fonts/NewRocker.ttf"));
        } catch(IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }
        return new GameUIText(ButtonText, temp, x-640, y-366, 25); //366 to offset the y
        //return  new GameUIText("New Game", x-640, y-360, 20);
    }

    /**
     * This method adds the buttons to the game engine
     */
    public void addToEngine(){
        if(!In_Engine) {
            In_Engine = true;
            GameEngine.add(this);
            GameEngine.add(text);
        }
    }

    /**
     * This method remove the buttons from the game engine
     */
    public void remove(){
        if(In_Engine){
            GameEngine.remove(this);
            GameEngine.remove(text);
            In_Engine = false;
        }
    }

    /**
     * This method sets the toggle follow view and sets the text pos
     */
    public void setPlay() {
        this.toggleFollowView();
        text.setPosition(GameUtils.getPlayer().getPosition());
    }


}
