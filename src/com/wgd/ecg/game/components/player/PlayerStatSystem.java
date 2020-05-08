package com.wgd.ecg.game.components.player;

import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.UI.GameUIButton;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.GameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
This class represents the level of the entity.
 */
public class PlayerStatSystem {
    private int maxLevel, exponent = 2, base = 100, level, token = 0;
    private int XP;
    private double lastButtonUsed = GameClock.timeInMS();
    private GameUIButton button1 = new GameUIButton("levelupoption.png", GameEngine.WIDTH/ 4 - 200, -GameEngine.HEIGHT/ 2 + 40, 2, 2);
    private GameUIButton button2 = new GameUIButton("levelupoption.png", GameEngine.WIDTH/ 4 - 100, -GameEngine.HEIGHT/ 2 + 40, 2, 2);
    private GameUIButton button3 = new GameUIButton("levelupoption.png", GameEngine.WIDTH/ 4, -GameEngine.HEIGHT/ 2 + 40, 2, 2);
    private GameUIText tokenChoiceText1 = new GameUIText("+health","PressStart2P", GameEngine.WIDTH/ 4 -200, -GameEngine.HEIGHT/ 2 + 50, 8);
    private GameUIText tokenChoiceText2 = new GameUIText("+speed","PressStart2P", GameEngine.WIDTH/ 4 - 100, -GameEngine.HEIGHT/ 2 + 50, 8);
    private GameUIText tokenChoiceText3 = new GameUIText("+damage","PressStart2P", GameEngine.WIDTH/ 4, -GameEngine.HEIGHT/ 2 + 50, 8);
    private GameUIText tokenAmountText = new GameUIText("Unspent token: " + token,"PressStart2P", GameEngine.WIDTH/ 4 - 100, -GameEngine.HEIGHT/ 2 + 80, 10);
    private List<String> upgradeLog = new ArrayList<>();

    /*To create an instance of this class, input the starting level, the
    max level reachable and the starting XP.
     */
    public PlayerStatSystem(int startLevel, int maxLvl, int startXP) {
        if(maxLvl < 1) {
            maxLevel = 1;
        }
        else {
            maxLevel = maxLvl;
        }

        if(startLevel < 1) {
            level = 1;
        }
        else if(maxLevel < startLevel) {
            level = maxLevel;
        }
        else {
            level = startLevel;
        }

        if(startXP < 1) {
            XP = 0;
        }
        else if(startXP >= nextLevel()) {
            XP = nextLevel() - 1;
        }
        else {
            XP = startXP;
        }

        button1.toggleFollowView();
        button2.toggleFollowView();
        button3.toggleFollowView();
    }

    public void deathPenalty() {
        if(level > 1) {
            level--;
        }

        if(upgradeLog.size() > 0) {
            if(upgradeLog.get(upgradeLog.size() - 1).equals("health")) {
                GameUtils.getPlayer().health.decreaseMaxHealth(20);
            }
            else if(upgradeLog.get(upgradeLog.size() - 1).equals("speed")) {
                GameUtils.getPlayer().decreaseMaxVelocity(0.0625f);
            }
            else if(upgradeLog.get(upgradeLog.size() - 1).equals("damage")) {
                GameUtils.getPlayer().getHeroType().decreaseDamage(5);
            }
            upgradeLog.remove(upgradeLog.size()-1);
        }
        else if(token > 0) {
            token--;
        }

        XP = 0;

        deleteButtons();
    }

    /*
    This method prints out the current level, XP and needed XP for a level up.
     */
    public void getStats() {
        System.out.println("LVL: " + level + "\nXP: " + XP + " / " + nextLevel());
    }

    public int getLevel() { return level; }

    public int getXP() { return XP; }

    /*
    This method calculates the needed XP to reach the next level.
     */
    public int nextLevel() {
        return (int) Math.floor(base * Math.pow(level, exponent));
    }

    /*
    Use this method to give the entity XP.
     */
    public void addXP(int increase) {
        if(increase >= 0.1) {
            if(XP + increase > nextLevel() && level == maxLevel) {
                XP = nextLevel();
            }
            else {
                XP += increase;
            }

            if(XP >= nextLevel()) {
                levelUp();
            }
        }
    }

    /*
    This method initiates an increase in the entities level.
     */
    private void levelUp() {
        if(maxLevel > level) {
            if(XP > nextLevel()) {
                XP -= nextLevel();

                level++;
                token++;

                if(XP >= nextLevel() && level==maxLevel) {
                    XP = nextLevel();
                }
                else if(XP >= nextLevel()) {
                    levelUp();
                }
            }
            else {
                XP = 0;

                level++;
                token++;
            }
        }
    }

    public boolean skillImprovement(String s) {
        if(token > 0) {
            if(s.equals("health")) {
                GameUtils.getPlayer().health.increaseMaxHealth(20);

                upgradeLog.add("health");

                token--;

                return true;
            }
            else if(s.equals("speed")) {
                GameUtils.getPlayer().increaseMaxVelocity(0.0625f);

                upgradeLog.add("speed");

                token--;

                return true;
            }
            else if(s.equals("damage")) {
                GameUtils.getPlayer().getHeroType().increaseDamage(5);

                upgradeLog.add("damage");

                token--;

                return true;
            }

            return false;
        }
        else {
            return false;
        }
    }

    public void drawTokens(){
        drawTokenChoices();
    }

    private void drawTokenChoices() {
        deleteButtons();

        GameEngine.add(button1);
        GameEngine.add(button2);
        GameEngine.add(button3);

        GameEngine.add(tokenChoiceText1);
        GameEngine.add(tokenChoiceText2);
        GameEngine.add(tokenChoiceText3);
        tokenAmountText.setString("Unspent token: " + token);
        GameEngine.add(tokenAmountText);
    }

    private void checkButtons() {
        if(button1.getIsReleased()) {
            skillImprovement("health");
            lastButtonUsed = GameClock.timeInMS();
            deleteButtons();
        }
        else if(button2.getIsReleased()) {
            skillImprovement("speed");
            lastButtonUsed = GameClock.timeInMS();
            deleteButtons();
        }
        else if(button3.getIsReleased()) {
            skillImprovement("damage");
            lastButtonUsed = GameClock.timeInMS();
            deleteButtons();
        }
    }

    private void deleteButtons() {
        GameEngine.remove(button1);
        GameEngine.remove(button2);
        GameEngine.remove(button3);
        GameEngine.remove(tokenChoiceText1);
        GameEngine.remove(tokenChoiceText2);
        GameEngine.remove(tokenChoiceText3);
        GameEngine.remove(tokenAmountText);
    }

    public void levelSystemUpdate() {
        checkButtons();
        if (GameClock.timeInMS() - lastButtonUsed > 1000) {
            if (token > 0) {
                drawTokenChoices();
            }
        }

        if(token < 1) {
            deleteButtons();
        }
    }
}
