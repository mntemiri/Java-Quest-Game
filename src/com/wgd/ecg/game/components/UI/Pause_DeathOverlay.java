package com.wgd.ecg.game.components.UI;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;

/**
 * This class creates the pause on death overlay
 */
public class Pause_DeathOverlay {
    protected static final int Centre_X = GameEngine.WIDTH / 2;
    protected static final int Centre_Y = GameEngine.HEIGHT / 2;
    private static Boolean Display_Menu = false;
    private static Menu_Button Quit = new Menu_Button("Quit to Home Screen",0, - 75, false,false, 0);
    private static Pause_RespawnButton respawnButton = new Pause_RespawnButton(0,0,false);
    private static GameUIText temp = new GameUIText("You Have Perished","NewRocker", 0, -150, 40);

    public Pause_DeathOverlay() {
        GameEngine.add(temp);
    }

    /**
     * This method shows the menu
     */
    public static void ShowMenu() {
            Quit.addToEngine();
            respawnButton.addToEngine();
            GameEngine.add(temp);
            Display_Menu = true;
    }

    /**
     * This method clears the menu
     */
    public static void clearMenu() {
        Quit.remove();
        respawnButton.remove();
        GameEngine.remove(temp);
    }
}
