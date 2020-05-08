package com.wgd.ecg.game.components.UI;
import com.wgd.ecg.engine.GameEngine;


/**
 * This class creates the difficulty select overlay
 */
public class DifficultySelectOverlay {
    protected static final int Centre_X = GameEngine.WIDTH / 2;
    protected static final int Centre_Y = GameEngine.HEIGHT / 2;
    public DifficultySelectOverlay(float posX, float posY, boolean addToEngine) {
        DifficultySelectButton easy_button = new DifficultySelectButton(posX - 400, posY, addToEngine, "Easy");
        DifficultySelectButton medium_button = new DifficultySelectButton(posX, posY, addToEngine, "Medium");
        DifficultySelectButton hard_button = new DifficultySelectButton(posX + 400, posY, addToEngine, "Hard");
    }
}