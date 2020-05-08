package com.wgd.ecg.game.components.UI;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.UI.GameUIButton;
import com.wgd.ecg.engine.UI.GameUIText;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;

import java.io.IOException;
import java.nio.file.Paths;

//import javafx.scene.control.ButtonType;



public class Menu_ToggleMuteButton extends GameUIButton {

//    public Menu_Button(String spriteTexture, float x, float y, float scaleX, float scaleY, boolean addToEngine) {
//        super(spriteTexture, x, y, scaleX, scaleY);
//        setHoverColour(new Color(128, 128, 128, 255));
//        GameUIText text = addButtonText(x,y);
//        if(addToEngine) {
//            GameEngine.add(this);
//            GameEngine.add(text);
//        }
//    }
    private boolean HoverDelay = true;
    private GameUIText text;
    private String Button_Text_String = "NULL"; //Default is "NULL" if text hasn't been updated
    private boolean In_Engine = false;
    private boolean isStatic = true;

    public Menu_ToggleMuteButton(float x, float y, boolean addToEngine, boolean isStatic){
        super("Green_Toggle_Button.jpg", x, y, 1f, 1f);
        this.isStatic = isStatic;
        In_Engine = addToEngine;
        this.setHoverColour(new Color(128, 128, 128, 255));
        if (!this.isStatic)
        {
            System.out.println("In nweasda");
            toggleFollowView();
            text = new GameUIText(Button_Text_String, font("NewRocker"), x, y-6, 25); //Currently working out offset manually
        }
        else {
            text = new GameUIText(Button_Text_String, font("NewRocker"), x-640, y-365, 25); //Currently working out offset manually
        }
        checkMute();
        if(In_Engine) {
            GameEngine.add(this);
            GameEngine.add(text);
        }
    }
    public Menu_ToggleMuteButton(float x, float y, boolean addToEngine){
        super("Green_Toggle_Button.jpg", x, y, 1f, 1f);
        In_Engine = addToEngine;
        text = new GameUIText(Button_Text_String, font("NewRocker"), x-640, y-365, 25); //Currently working out offset manually
        this.setHoverColour(new Color(128, 128, 128, 255));
        checkMute();
        if(In_Engine) {
            GameEngine.add(this);
            GameEngine.add(text);
        }
    }
//    public Menu_Button(String ButtonText, float x, float y, boolean addToEngine) {
//        super("Default_Menu_Button.jpg", x, y, 1f, 1f);
//        setHoverColour(new Color(128, 128, 128, 255));
//        GameUIText text = addButtonText(ButtonText,x,y);
//        if(addToEngine) {
//            GameEngine.add(this);
//            GameEngine.add(text);
//        }
//    }


    @Override
    public void onPressed() {


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
        Audio.toggleMute();
        Audio.playSFX("Retro_Selection");
        checkMute();
    }


    /**
     * this method checks the font name
     * @param fontName
     * @return
     */
    private Font font(String fontName){
        Font font = new Font();
        try {
            font.loadFromFile(Paths.get("src/com/wgd/ecg/res/fonts/" + fontName + ".ttf"));
        } catch(IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }
        return font;
    }

    /**
     * This method checks for muted sounds
     */
    private void checkMute(){
        if(Audio.isMute()) {
            if(this.getNormalSprite() != null & this.getHoverSprite() != null) {
                this.setNormalSprite("Red_Toggle_Button.jpg");
                this.setHoverSprite("Red_Toggle_Button.jpg");
                text.setStringCenter("Muted");
            }
            Button_Text_String = "Muted";
        }
        else {
            if(this.getNormalSprite() != null & this.getHoverSprite() != null) {
                this.setNormalSprite("Green_Toggle_Button.jpg");
                this.setHoverSprite("Green_Toggle_Button.jpg");
                text.setStringCenter("Not Muted");
            }
            Button_Text_String = "Not Muted";
        }
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
