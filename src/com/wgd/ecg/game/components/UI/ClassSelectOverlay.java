package com.wgd.ecg.game.components.UI;
import com.wgd.ecg.engine.GameEngine;


/**
 * This class creates the class select overlay
 */
public class ClassSelectOverlay {
    protected static final int Centre_X = GameEngine.WIDTH / 2;
    protected static final int Centre_Y = GameEngine.HEIGHT / 2; 
    public ClassSelectOverlay(float posX, float posY, boolean addToEngine) {
        ClassSelectButton warrior_button = new ClassSelectButton(posX - 400, posY, addToEngine, "Warrior");
        ClassSelectButton wizard_button = new ClassSelectButton(posX, posY, addToEngine, "Wizard");
        ClassSelectButton hunter_button = new ClassSelectButton(posX + 400, posY, addToEngine, "Hunter");
    }
}
