package com.wgd.ecg.game.components.UI;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameStateManager;
import com.wgd.ecg.engine.PlayerStateManager;
import com.wgd.ecg.engine.UI.GameUIButton;
import com.wgd.ecg.engine.UI.GameUIText;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;

import java.io.IOException;
import java.nio.file.Paths;

public class Pause_RespawnButton extends GameUIButton {

    /**
     * This class creates a pause respawn button
     */

    private boolean HoverDelay = true;
    private boolean In_Engine = false;
    private GameUIText text;
    protected static final int Centre_X = GameEngine.WIDTH / 2; //James
    protected static final int Centre_Y = GameEngine.HEIGHT / 2; //James

    public Pause_RespawnButton(float x, float y, boolean addToEngine) {
        super("Default_Menu_Button.jpg", x, y, 1f, 1f);
        String ButtonText = "Respawn (1 level Penalty)";
        this.toggleFollowView();
        this.In_Engine = addToEngine;
        setHoverColour(new Color(128, 128, 128, 255));
        text = addButtonText(ButtonText,x + 640, y + 360);
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
        Audio.playSFX("Retro_Selection");
        GameEngine.loadLevel(GameStateManager.HOUSEINSIDE);
        PlayerStateManager.loadPlayer(Centre_X -645 ,Centre_Y-200);
        GameEngine.setTimeScale(1);
    }


    /**
     * This method adds the text to the button
     * @param ButtonText  text to button
     * @param x  x position
     * @param y  y position
     * @return  text
     */
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


}
