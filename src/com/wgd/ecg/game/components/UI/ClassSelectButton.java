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

public class ClassSelectButton extends GameUIButton {

    protected static final int Centre_X = GameEngine.WIDTH / 2;
    protected static final int Centre_Y = GameEngine.HEIGHT / 2;

    private String heroType;
    private boolean HoverDelay = true;
    GameUIText text;

    public ClassSelectButton( float x, float y, boolean addToEngine, String heroType) {
        super("Class_Select_Button.jpg", x, y, 1f, 1f);
        this.heroType = heroType;
        setHoverColour(new Color(128, 128, 128, 255));
        text = addButtonText(heroType, x ,y);
        if(heroType.equals("Warrior")) {
            this.setNormalSprite("Class_Button_Warrior.png");
            this.setHoverSprite("Class_Button_Warrior.png");
            this.setClickSprite("Class_Button_Warrior.png");
        }
        if(heroType.equals("Wizard")) {
            this.setNormalSprite("Class_Button_Wizard.png");
            this.setHoverSprite("Class_Button_Wizard.png");
            this.setClickSprite("Class_Button_Wizard.png");
        }
        if(heroType.equals("Hunter")) {
            this.setNormalSprite("Class_Button_Hunter.png");
            this.setHoverSprite("Class_Button_Hunter.png");
            this.setClickSprite("Class_Button_Hunter.png");
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
        GameUtils.removePlayer();
        Player player;
        switch (heroType) {
            case "Hunter":
                player = new Player("hunter_animation.png", Centre_X - 645, Centre_Y -200, 4f, 4f, heroType);
                System.out.println("Hunter");
                break;
            case "Warrior":
                player = new Player("warrior_animation.png", Centre_X- 645, Centre_Y-200, 4f, 4f, heroType);
                System.out.println("Warrior");
                break;
            case "Wizard":
                player = new Player("wizard_animation.png", Centre_X- 645, Centre_Y-200, 4f, 4f, heroType);
                System.out.println("Wizard");
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + heroType);
        }
        GameEngine.setTimeScale(1);
        GameUtils.setPlayer(player);
        GameEngine.add(player);
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
        GameEngine.removeClassObject(ClassSelectButton.class);
        GameEngine.removeClassObject(GameUIText.class);
    }
}
