package com.wgd.ecg.game.components.UI;

import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.UI.GameUISprite;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.GameUtils;

/**
 * This class creates an attack option bar
 */
public class AttackOptionBar {
    private String heroType;
    private GameUISprite move1Cover;
    private GameUISprite move2Cover;
    private double move1Called = GameClock.timeInMS();
    private double move2Called = GameClock.timeInMS();
    private double cooldown1 = 0, cooldown2 = 0;
    public AttackOptionBar(String heroType) {
        this.heroType = heroType;
        draw();
    }

    /**
     * This method creates the attacks players bar
     */
    public void draw() {
        if(heroType.equals("warrior")) {
            GameUISprite option1 = new GameUISprite("warriorMove2.png", -70,GameEngine.HEIGHT/ 2 - 120, 2,2);
            option1.toggleFollowView();
            GameEngine.add(option1);

            GameUISprite option2 = new GameUISprite("warriorMove1.png", -140,GameEngine.HEIGHT/ 2 - 120, 2,2);
            option2.toggleFollowView();
            GameEngine.add(option2);
        }
        else if(heroType.equals("hunter")) {
            GameUISprite option1 = new GameUISprite("hunterMove2.png", -70,GameEngine.HEIGHT/ 2 - 120, 2,2);
            option1.toggleFollowView();
            GameEngine.add(option1);

            GameUISprite option2 = new GameUISprite("hunterMove1.png", -140,GameEngine.HEIGHT/ 2 - 120, 2,2);
            option2.toggleFollowView();
            GameEngine.add(option2);
        }
        else if(heroType.equals("wizard")) {
            GameUISprite option1 = new GameUISprite("wizardMove2.png", -70,GameEngine.HEIGHT/ 2 - 120, 2,2);
            option1.toggleFollowView();
            GameEngine.add(option1);

            GameUISprite option2 = new GameUISprite("wizardMove1.png", -140,GameEngine.HEIGHT/ 2 - 120, 2,2);
            option2.toggleFollowView();
            GameEngine.add(option2);
        }

        GameUIText temp = new GameUIText("2", "PressStart2P", -50, GameEngine.HEIGHT/ 2 - 140, 15);
        GameEngine.add(temp);
        temp = new GameUIText("1","PressStart2P", -120, GameEngine.HEIGHT/ 2 - 140, 15);
        GameEngine.add(temp);

        move1Cover = new GameUISprite("moveTimeCover.png", -140,GameEngine.HEIGHT/ 2 - 120, 2,2);
        move2Cover = new GameUISprite("moveTimeCover.png", -70,GameEngine.HEIGHT/ 2 - 120, 2,2);
        move1Cover.toggleFollowView();
        move2Cover.toggleFollowView();
        GameEngine.add(move1Cover);
        GameEngine.add(move2Cover);
    }

    public void cooldown(int moveID) {
        switch(moveID) {
            case 0:
                move1Called = GameClock.timeInMS();
                break;
            case 1:
                move2Called = GameClock.timeInMS();
                break;
        }
    }


    /**
     * This method updates the attacks option bar
     */
    public void attackOptionBarUpdate() {
        if(cooldown1 == 0 || cooldown2 == 0) {
            cooldown1 = GameUtils.getPlayer().getHeroType().getMoveOneCooldown();
            cooldown2 = GameUtils.getPlayer().getHeroType().getMoveTwoCooldown();
        }

//        System.out.println("Move1 " + (GameClock.timeInMS() - move1Called));
//        System.out.println("Move2 " + (GameClock.timeInMS() - move2Called));


        if(GameClock.timeInMS() - move1Called < cooldown1 && GameClock.timeInMS() - move1Called > 0) {
            move1Cover.setScale((float)((cooldown1 - (GameClock.timeInMS() - move1Called))/cooldown1)*2, (float)((cooldown1 - (GameClock.timeInMS() - move1Called))/cooldown1)*2);
        }
        else {
            move1Cover.setScale(0, 0);
        }

        if(GameClock.timeInMS() - move2Called < cooldown2 && GameClock.timeInMS() - move2Called > 0) {
            move2Cover.setScale((float)((cooldown2 - (GameClock.timeInMS() - move2Called))/cooldown2)*2, (float)((cooldown2 - (GameClock.timeInMS() - move2Called))/cooldown2)*2);
        }
        else {
            move2Cover.setScale(0, 0);
        }
    }

    /**
     * This method redraws the attack option bar
     */
    public void redrawAttackBar(){
        draw();
    }
}
