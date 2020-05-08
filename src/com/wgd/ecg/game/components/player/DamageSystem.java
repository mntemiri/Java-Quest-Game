package com.wgd.ecg.game.components.player;


import com.wgd.ecg.game.GameUtils;

import java.io.Serializable;

/*
This class is supposed the entities health and the methods to deal damage to it/ heal the health.
 */
public class DamageSystem implements Serializable {

    private int health, maxHealth;
    private boolean player;

    /*
    To set up the class it requires a max health and the entity will start with 100% of the max health.
     */
    public DamageSystem(int beginningHealth) {
        this.player = false;
        if(beginningHealth > 0) {
            maxHealth = beginningHealth;
            health = beginningHealth;
        }
        else {
            maxHealth = 0;
            health = 0;
        }
    }

    public DamageSystem(int beginningHealth, boolean player) {
        this.player = player;
        if(beginningHealth > 0) {
            maxHealth = beginningHealth;
            health = beginningHealth;
        }
        else {
            maxHealth = 0;
            health = 0;
        }
    }

    /*
    This is an accessor for the health variable.
     */
    public int getHealth() {
        return health;
    }

    /*
    This is an accessor for the maxHealth variable.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /*
    This method enables the option to deal damage to the health, input the amount of damage you wish to deal.
     */
    public void dealDamage(int damage) {

        if(damage < 0) {
            System.out.println("Damage can't be dealt by a negative number. ~DamageSystem/dealDamage");
        }
        else if(health - damage <= 0) {
            health = 0;
            if(player){
                GameUtils.death();
            }
        }
        else {
            health -= damage;
        }
        if(player){
            GameUtils.getPlayer().hit();
        }
    }

    /*
    Use this method to heal/ increase the health. Input the amount you wish to heal, the health wont however go increase
    beyond the maxHealth.
     */
    public void increaseHealth(int increase) {
        if(increase < 0) {
            System.out.println("Health can't be increased by a negative number. ~DamageSystem/increaseHealth");
        }
        else if(health + increase >= maxHealth) {
            health = maxHealth;
        }
        else {
            health += increase;
        }
    }

    /*
    This method increases the max health.
     */
    public void increaseMaxHealth(int increaseMax) {
        if(increaseMax < 0) {
            System.out.println("Max health can't be increased by a negative number. ~DamageSystem/increaseMaxHealth");
        }
        else {
            maxHealth += increaseMax;
        }
    }

    public void decreaseMaxHealth(int decreaseMax) {
        if(decreaseMax < 0 || maxHealth - decreaseMax <= 0) {
            System.out.println("Max health can't be decreased by or to a negative number. ~DamageSystem/decreaseMaxHealth");
        }
        else {
            maxHealth -= decreaseMax;

            if(health > maxHealth) {
                health = maxHealth;
            }
        }
    }

    /*
    This method is used when you want to update (redraw) the health bar.
     */
    public void update() {

    }
}
