package com.wgd.ecg.game.components.UI;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.Camera;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.UI.GameUIButton;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;

import java.io.IOException;
import java.nio.file.Paths;

//import static jdk.jfr.internal.instrument.JDKEvents.remove;

/**
 * This class creates a button that can be selected
 */

public class DifficultySelectButton extends GameUIButton {

    protected static final int Centre_X = GameEngine.WIDTH / 2;
    protected static final int Centre_Y = GameEngine.HEIGHT / 2;

    private String difficulty;
    private boolean HoverDelay = true;
    GameUIText text;

    public DifficultySelectButton( float x, float y, boolean addToEngine, String difficulty) {
        super("Class_Select_Button.jpg", x, y, 1f, 1f);
        this.difficulty = difficulty;
        setHoverColour(new Color(128, 128, 128, 255));
        text = addButtonText(difficulty, x ,y);
        if(difficulty.equals("Easy")) {
            this.setNormalSprite("Difficulty_Button_Easy.png");
            this.setHoverSprite("Difficulty_Button_Easy.png");
            this.setClickSprite("Difficulty_Button_Easy.png");
        }
        if(difficulty.equals("Medium")) {
            this.setNormalSprite("Difficulty_Button_Medium.png");
            this.setHoverSprite("Difficulty_Button_Medium.png");
            this.setClickSprite("Difficulty_Button_Medium.png");
        }
        if(difficulty.equals("Hard")) {
            this.setNormalSprite("Difficulty_Button_Hard.png");
            this.setHoverSprite("Difficulty_Button_Hard.png");
            this.setClickSprite("Difficulty_Button_Hard.png");
        }
        if(addToEngine) {
            GameEngine.add(this);
            GameEngine.add(text);
        }

        Camera camera = new Camera();
        camera.setPos(0, 400);

        GameEngine.setTimeScale(0);
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
        remove();
        switch (difficulty) {
            case "Easy":
                GameUtils.setDifficulty("Easy");
                System.out.println("Easy");
                break;
            case "Medium":
               System.out.println("Medium");
                break;
            case "Hard":
                 System.out.println("Hard");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + difficulty);
        }

        new ClassSelectOverlay(Centre_X - 645, Centre_Y, true);
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
        return new GameUIText(ButtonText, temp, x, y-425, 35); //Change Y to 366 for centre
        //return  new GameUIText("New Game", x-640, y-360, 20);
    }


    /**
     * This method remove the buttons from the game engine
     */
    public void remove(){
        GameEngine.removeClassObject(DifficultySelectButton.class);
        GameEngine.removeClassObject(GameUIText.class);
    }
}
