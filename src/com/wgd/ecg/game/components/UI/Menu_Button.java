package com.wgd.ecg.game.components.UI;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameStateManager;
import com.wgd.ecg.engine.PlayerStateManager;
import com.wgd.ecg.engine.UI.GameUIButton;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class Menu_Button extends GameUIButton {



    /**
     * CODE IS STILL VERY ROUGH ATM I WILL UPDATE - JAMES
     */

    private int LevelToLoad;
    private boolean HoverDelay = true;
    private boolean In_Engine = false;
    private GameUIText text;
    private boolean isStatic = true;
    protected static final int Centre_X = GameEngine.WIDTH / 2; //James
    protected static final int Centre_Y = GameEngine.HEIGHT / 2; //James

    public Menu_Button(String ButtonText, float x, float y, boolean addToEngine, boolean isStatic, int LevelToLoad) {
        super("Default_Menu_Button.jpg", x, y, 1f, 1f);
        this.LevelToLoad = LevelToLoad;
        this.In_Engine = addToEngine;
        this.isStatic = isStatic;
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
    public Menu_Button(String ButtonText, float x, float y, boolean addToEngine, int LevelToLoad) {
        super("Default_Menu_Button.jpg", x, y, 1f, 1f);
        this.LevelToLoad = LevelToLoad;
        this.In_Engine = addToEngine;
        setHoverColour(new Color(128, 128, 128, 255));
        if (!this.isStatic)
        {
            toggleFollowView();
            text = addButtonText(ButtonText,x+640,y+360);
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
        Audio.playSFX("Retro_Selection");
        GameEngine.loadLevel(LevelToLoad);
        if(LevelToLoad == GameStateManager.HOUSEINSIDE) {
            //GameEngine.removeClassObject(Player.class);
//            new ClassSelectOverlay(Centre_X - 645, Centre_Y, true);
            new DifficultySelectOverlay(Centre_X - 645, Centre_Y, true);
        }
        if (LevelToLoad == 0){
            GameUtils.removePlayer();
            PlayerStateManager.clearSave();
            Pause_MenuOverlay.setDisplay_Menu(false);
        }
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
            ex.printStackTrace();
        }
        return new GameUIText(ButtonText, temp, x-640, y-366, 25); //366 to offset the y
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
