package com.wgd.ecg.game.gameobjects.entities.heroes;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.Input;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.UI.AttackOptionBar;
import com.wgd.ecg.game.components.player.DamageSystem;
import com.wgd.ecg.game.gameobjects.Entity;
import org.jsfml.system.Vector2f;

import java.io.Serializable;

/**
 * This class creates the hero
 */
public class Hero implements Serializable {
    private int damage;
    private int speed;
    private DamageSystem health;
    protected int baseDamage = 0;
    protected int temporaryBaseDamage = baseDamage;
    protected transient AttackOptionBar moveBar;
    public double moveOneCooldown;
    public double moveTwoCooldown;

    public Hero(String heroType){
        moveBar = new AttackOptionBar(heroType);
    }


    public void basicAttack() {

    }


    public void moveOne(){
        System.out.println("Please implement move one :)");
    }

    public void moveOneContinue(){

    }

    public void moveTwo(){
        System.out.println("Please implement move two :)");
    }

    public void specialMove(){
        System.out.println("Please implement special move :)");
    }


    /**
     * This method returns the base damage
     * @return the base damage
     */
    public int getBaseDamage() { return baseDamage; }

    /**
     * This method updates the base damage
     * @param damage
     */
    public void updateBaseDamage(int damage) { temporaryBaseDamage = damage; }

    /**
     * This method resets the base damage
     */
    public void resetBaseDamage() { temporaryBaseDamage = baseDamage; }


    /**
     * This method increases damage by improvement
     * @param improvement
     */
    public void increaseDamage(int improvement) {
        baseDamage += improvement;
        temporaryBaseDamage = baseDamage;
    }

    /**
     * This method decreases the damage
     * @param decrease
     */
    public void decreaseDamage(int decrease) {
        baseDamage -= decrease;
        temporaryBaseDamage = baseDamage;
    }

    /**
     * This method returns the move one cool down
     * @return  moveOneCooldown
     */
    public double getMoveOneCooldown() {
        return moveOneCooldown;
    }

    /**
     * This method returns the move two cool down
     * @return  moveTwoCooldown
     */
    public double getMoveTwoCooldown() {
        return moveTwoCooldown;
    }


    /**
     * This method returns the move bar
     * @return moveBar
     */
    public AttackOptionBar getMoveBar() {
        return moveBar;
    }
}
