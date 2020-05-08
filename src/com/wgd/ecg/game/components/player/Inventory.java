package com.wgd.ecg.game.components.player;

/*
This class is supposed to represent the inventory of the player. The inventory has 3 slots and the potions fixed
positions in these slots. The potions are stackable.
 */

import com.wgd.ecg.engine.Audio;
import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.Input;
import com.wgd.ecg.engine.UI.GameUISprite;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;
import org.jsfml.graphics.Color;

import java.io.Serializable;

public class Inventory implements Serializable {

    //These 3 integers represent the 3 slots in the inventory of the player.
    //Slot 1 is for health potions, slot 2 for speed potions and slot 3 for
    //strength potions. These potions are stackable and therefore have this fixed
    //position in the inventory.

    private int beans = 0;
    private int health_potions = 5;
    private int speed_potions = 5;
    private int strength_potions = 5;
    private Player player;
    private double healthTimer = GameClock.timeInMS();
    private double speedTimer = GameClock.timeInMS();
    private double strengthTimer = GameClock.timeInMS();
    private boolean speedActive = false;
    private boolean strengthActive = false;
    private transient GameUIText healthAmountText = new GameUIText("0","PressStart2P" , 02,GameEngine.HEIGHT/ 2 - 102, 25);
    private transient GameUIText speedAmountText = new GameUIText("0", "PressStart2P", 72,GameEngine.HEIGHT/ 2 - 102, 25);
    private transient GameUIText strengthAmountText = new GameUIText("0", "PressStart2P", 142,GameEngine.HEIGHT/ 2 - 102, 25);
    private transient GameUISprite healthCover;
    private transient GameUISprite speedCover;
    private transient GameUISprite strengthCover;

    private boolean potionUsed = false; //James

    public Inventory(Player player) {
        this.player = player;
        this.drawComponents();
    }


    /*
    This method returns an array of 3 integers. The array represents the 3 inventory slots and each integer represents
    the number of potions in that slot. Slot 0 represents the health potions, slot 1 the speed potions
    and slot 2 the strength potions.
    */
    public int[] getInventory() {
        int[] inv = new int[3];

        inv[0] = health_potions;
        inv[1] = speed_potions;
        inv[2] = strength_potions;

        return inv;
    }

    public void drawPotions(){
        drawComponents();
    }

    /*
    This method adds all components of the inventory to the GameEngine.
     */
    private void drawComponents() {

        GameUISprite inventorySlot1 = new GameUISprite("inventoryhealth.png", 0,GameEngine.HEIGHT/ 2 - 120, 2,2);
        GameUISprite inventorySlot2 = new GameUISprite("inventoryspeed.png", 70,GameEngine.HEIGHT/ 2 - 120, 2,2);
        GameUISprite inventorySlot3 = new GameUISprite("inventorystrength.png", 140,GameEngine.HEIGHT/ 2 - 120, 2,2);
        healthCover = new GameUISprite("moveTimeCover.png", 0,GameEngine.HEIGHT/ 2 - 120, 2,2);
        speedCover = new GameUISprite("moveTimeCover.png", 70,GameEngine.HEIGHT/ 2 - 120, 2,2);
        strengthCover = new GameUISprite("moveTimeCover.png", 140,GameEngine.HEIGHT/ 2 - 120, 2,2);
        inventorySlot1.toggleFollowView();
        inventorySlot2.toggleFollowView();
        inventorySlot3.toggleFollowView();
        healthCover.toggleFollowView();
        speedCover.toggleFollowView();
        strengthCover.toggleFollowView();
        GameEngine.add(inventorySlot1);
        GameEngine.add(inventorySlot2);
        GameEngine.add(inventorySlot3);
        GameEngine.add(healthCover);
        GameEngine.add(speedCover);
        GameEngine.add(strengthCover);

        GameEngine.add(healthAmountText);
        GameEngine.add(speedAmountText);
        GameEngine.add(strengthAmountText);

        GameUIText temp = new GameUIText("3","PressStart2P" , 20,GameEngine.HEIGHT/ 2 - 140, 15);
        GameEngine.add(temp);

        temp = new GameUIText("4","PressStart2P" , 90,GameEngine.HEIGHT/ 2 - 140, 15);
        GameEngine.add(temp);

        temp = new GameUIText("5","PressStart2P" , 160,GameEngine.HEIGHT/ 2 - 140, 15);
        GameEngine.add(temp);
    }

    /*
    This method can be used to add a potion to the inventory. The method takes either "health", "speed" or "strength"
    as variables and increments the potion amount of the input String by one. The method returns true if the input was
    valid (one of the three options) and false if not.
     */
    public boolean addItem(String i) {
        if(i.equals("health")) {
            if(health_potions < 99) {
                health_potions++;
            }
        }
        else if(i.equals("speed")) {
            if(speed_potions < 99) {
                speed_potions++;
            }
        }
        else if(i.equals("strength")) {
            if(strength_potions < 99) {
                strength_potions++;
            }
        }else if(i.equals("bean")){
            beans++;
        }
        else {
            return false;
        }

        return true;
    }

    public int beanCount() {  return beans;  }

    /*
    This method takes one of three possible Strings as input: "health", "speed" or "strength". The method then activates
    the effect of that potion and returns true. If none of the three Strings were given as an input the method returns
    false.
     */
    public boolean usePotion(String s) {
        if(s.equals("health") && GameClock.timeInMS() - healthTimer > 1000) {
            if(healthPotionEffect()) {
                healthTimer = GameClock.timeInMS();
                return true;
            }
        }
        else if(s.equals("speed") && GameClock.timeInMS() - speedTimer > 4000) {
            if(speedPotionEffect()) {
                speedTimer = GameClock.timeInMS();
                return true;
            }
        }
        else if(s.equals("strength") && GameClock.timeInMS() - strengthTimer > 4000) {
            if(strengthPotionEffect()) {
                strengthTimer = GameClock.timeInMS();
                return true;
            }
        }
        return false;
    }

    private boolean healthPotionEffect() {
        if(health_potions > 0) {
            player.health.increaseHealth(player.health.getMaxHealth()/2);
            health_potions--;
            return true;
        }
        else {
            System.out.println("No health potions in inventory!");
            return false;
        }
    }

    private boolean speedPotionEffect() {
        if(speed_potions > 0) {
            speed_potions--;
            player.setMaxVelocity(player.getMaxVelocity() + 1.0f);
            speedActive = true;
            return true;
        }
        else {
            System.out.println("No speed potions in inventory!");
            return false;
        }
    }

    private boolean strengthPotionEffect() {
        if(strength_potions > 0) {
            strength_potions--;
            strengthActive = true;
            GameUtils.getPlayer().getHeroType().updateBaseDamage(GameUtils.getPlayer().getHeroType().getBaseDamage() * 2);
            return true;
        }
        else {
            System.out.println("No strength potions in inventory!");
            return false;
        }
    }

    private void resizePotionTimer() {
        if(GameClock.timeInMS() - healthTimer < 1000 && GameClock.timeInMS() - healthTimer > 0) {
            healthCover.setScale((float)((1000 - (GameClock.timeInMS() - healthTimer))/1000)*2, (float)((1000 - (GameClock.timeInMS() - healthTimer))/1000)*2);
        }
        else {
            healthCover.setScale(0, 0);
        }

        if(GameClock.timeInMS() - speedTimer < 4000 && GameClock.timeInMS() - speedTimer > 0) {
            speedCover.setScale((float)((4000 - (GameClock.timeInMS() - speedTimer))/4000)*2, (float)((4000 - (GameClock.timeInMS() - speedTimer))/4000)*2);
        }
        else {
            speedCover.setScale(0, 0);
        }

        if(GameClock.timeInMS() - strengthTimer < 4000 && GameClock.timeInMS() - strengthTimer > 0) {
            strengthCover.setScale((float)((4000 - (GameClock.timeInMS() - strengthTimer))/4000)*2, (float)((4000 - (GameClock.timeInMS() - strengthTimer))/4000)*2);
        }
        else {
            strengthCover.setScale(0, 0);
        }
    }

    //STILL IN PROGRESS
    public void inventoryUpdate() {
        int[] temp;

        if(speedActive && GameClock.timeInMS() - speedTimer > 4000) {

            player.setMaxVelocity(player.getMaxVelocity());

            speedActive = false;
        }

        if(strengthActive && GameClock.timeInMS() - strengthTimer > 4000) {

            GameUtils.getPlayer().getHeroType().resetBaseDamage();

            strengthActive = false;
        }

        if(Input.healthPotion)
            if(usePotion("health"))
                potionUsed = true;
        if(Input.speedPotion)
            if(usePotion("speed"))
                potionUsed = true;
        if(Input.strengthPotion)
            if(usePotion("strength"))
                potionUsed = true;

//        if(Input.interaction) {
//            addItem("health");
//            addItem("speed");
//            addItem("strength");
//        }


        temp = getInventory();

        healthAmountText.setStringCenter(Integer.toString(temp[0]));

        speedAmountText.setStringCenter(Integer.toString(temp[1]));

        strengthAmountText.setStringCenter(Integer.toString(temp[2]));

        if(potionUsed) {
            Audio.playSFX("Potion_Drink");
            potionUsed = false;
        }

        resizePotionTimer();
    }
    public void reDrawComponents(){ //James
        this.drawComponents();
    }

    public int getBeans(){
        return beans;
    }

    public void deathEvent() {
        if(health_potions < 5) {
            health_potions = 5;
        }

        if(speed_potions < 5) {
            speed_potions = 5;
        }

        if(strength_potions < 5) {
            strength_potions = 5;
        }
    }
}
