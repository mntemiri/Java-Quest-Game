package com.wgd.ecg.game.components.UI;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;

/**
 * This class creates a pause menu overlay method
 */
public class Pause_MenuOverlay {
    protected static final int Centre_X = GameEngine.WIDTH / 2; //James
    protected static final int Centre_Y = GameEngine.HEIGHT / 2; //James
    private static Boolean Display_Menu = false;
    private static Menu_Button Quit = new Menu_Button("Quit to Home Screen",0, - 75, false,false, 0);
    private static Menu_ToggleMuteButton mute = new Menu_ToggleMuteButton(0, 0, false, false); //james - will make more usable
    private static Pause_ResumeButton resume = new Pause_ResumeButton(0,75,false);

    public Pause_MenuOverlay() {

    }

    /**
     * This method shows the menu
     */
    public static void ShowMenu() {
        System.out.println("In show Menu");
        if (!Display_Menu) {
            GameEngine.setTimeScale(0);
            resume.addToEngine();
            Quit.addToEngine();
            mute.addToEngine();
            Display_Menu = true;
        }

    }
    public static void setDisplay_Menu(Boolean display_Menu) {
        Display_Menu = display_Menu;
        System.out.println("In set display menu");
        if (!Display_Menu){
            GameEngine.setTimeScale(1);

            resume.remove();
            Quit.remove();
            mute.remove();

            Quit.remove();
            mute.remove();
            Display_Menu = false;
        }
    }
}
