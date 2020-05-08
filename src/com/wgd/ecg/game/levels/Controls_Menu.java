package com.wgd.ecg.game.levels;

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.engine.Level;
import com.wgd.ecg.engine.UI.GameUISprite;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.components.UI.Menu_Button;
import com.wgd.ecg.game.components.UI.Menu_VolumeButton;
import com.wgd.ecg.game.components.UI.Menu_ToggleMuteButton;
import com.wgd.ecg.game.levels.proceduralgen.ProceduralGeneration;

/**
 * Dungeon class used for dungeon maps.
 */
public class Controls_Menu extends Level {
    @Override
    public void load() {
        super.load();
        GameUIText moving = new GameUIText("Control Commands", "NewRocker", 0,-300, 50);
        GameEngine.add(moving);
        GameUISprite up = new GameUISprite("w.png" , 80 , 200 , 0.1f , 0.1f);
        GameEngine.add(up);
        GameUIText moveUp = new GameUIText("move up", "NewRocker", -470,-170,20);
        GameEngine.add(moveUp);
        GameUISprite down = new GameUISprite("s.png" , 80 , 270 , 0.1f , 0.1f);
        GameEngine.add(down);
        GameUIText moveDown = new GameUIText("move down", "NewRocker", -470,-100, 20);
        GameEngine.add(moveDown);
        GameUISprite left = new GameUISprite("d.png" , 80 , 340 , 0.1f , 0.1f);
        GameEngine.add(left);
        GameUIText moveLeft = new GameUIText("move left", "NewRocker", -470,-30, 20);
        GameEngine.add(moveLeft);
        GameUISprite right = new GameUISprite("a.png" , 80 , 410 , 0.1f , 0.1f);
        GameEngine.add(right);
        GameUIText moveRight = new GameUIText("move right", "NewRocker", -470,40, 20);
        GameEngine.add(moveRight);
        GameUISprite esc = new GameUISprite("ESC.jpg" , 80 , 480 , 0.12f , 0.12f);
        GameEngine.add(esc);
        GameUIText escButton = new GameUIText("Escape the game", "NewRocker", -440,110, 20);
        GameEngine.add(escButton);
        GameUISprite one = new GameUISprite("1b.png" , 520 , 200 , 0.1f , 0.1f);
        GameEngine.add(one);
        GameUIText oneButton = new GameUIText("Move One Control", "NewRocker", 0,-170,20);
        GameEngine.add(oneButton);
        GameUISprite two = new GameUISprite("2b.png" , 520 , 270 , 0.1f , 0.1f);
        GameEngine.add(two);
        GameUIText twoButton = new GameUIText("Move Two Control", "NewRocker", 0,-100, 20);
        GameEngine.add(twoButton);
        GameUISprite three = new GameUISprite("3b.png" , 520 , 340 , 0.1f , 0.1f);
        GameEngine.add(three);
        GameUIText threeButton = new GameUIText("Health Potion Control", "NewRocker", 20,-30, 20);
        GameEngine.add(threeButton);
        GameUISprite four = new GameUISprite("4b.png" , 520 , 410 , 0.1f , 0.1f);
        GameEngine.add(four);
        GameUIText fourButton = new GameUIText("Speed Potion Control", "NewRocker", 20,40, 20);
        GameEngine.add(fourButton);
        GameUISprite five = new GameUISprite("5b.png" , 520 , 480 , 0.1f , 0.1f);
        GameEngine.add(five);
        GameUIText fiveButton = new GameUIText("Strength Potion Control", "NewRocker", 20,110, 20);
        GameEngine.add(fiveButton);
        GameUISprite rightClick = new GameUISprite("leftClick.png" , 1000 , 200 , 0.25f , 0.25f);
        GameEngine.add(rightClick);
        GameUIText attack = new GameUIText("Basic Attack ", "NewRocker", 470,-170,20);
        GameEngine.add(attack);
        Menu_Button NewGame = new Menu_Button("Go Back", Centre_X, Centre_Y + 200, true, 0);
    }
}
