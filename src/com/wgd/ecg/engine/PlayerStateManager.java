package com.wgd.ecg.engine;

import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;
import org.jsfml.system.Vector2f;

import static com.wgd.ecg.game.components.GameUtils.playerPosition;

public class PlayerStateManager {
    static private Player savedPlayer;
    static private Vector2f returnLocation; //return point where you'll spawn when exiting
    static private boolean Override_Save = true; //if true then a new save can be made
    static private Vector2f house = new Vector2f(-600, 0);
    static private boolean death = false;

    /**
     * Saves the player and his location
     */
    public static void savePlayer(){
        if(Override_Save) {
            savedPlayer = GameUtils.getPlayer();
            returnLocation = GameUtils.playerPosition();
            Override_Save = false;
            System.out.println("Saving Player State...");
            System.out.print(returnLocation);
        }

    }

    /**
     * loads the saved player to the specified location
     * @param xPos
     * @param yPos
     * @return
     */
    public static Player loadPlayer(float xPos, float yPos){
        savedPlayer.setPosition(xPos, yPos);
        savedPlayer.reDrawUI();
        savedPlayer.refreshWeapon();
        savedPlayer.getHeroType().getMoveBar().redrawAttackBar();
        System.out.println("Loading Player State...");
        GameEngine.add(savedPlayer);

        return savedPlayer; //if you happened to need a copy of player
    }


    /**
     * This method is used to reset the players location
     */
    public static void resetPlayerLocation(){
        if(savedPlayer == null) {
            Override_Save = true;
            savePlayer();
            savedPlayer.setPosition(0,  300);
        }
        else {
            if(death) {
                savedPlayer.setPosition(0, 300);
                death = false;
            }
            else
                savedPlayer.setPosition(returnLocation.x, returnLocation.y + 50); // 50 so that when respawn you dont respawn on the entrance and loop back
        }
        savedPlayer.reDrawUI();
        savedPlayer.refreshWeapon();
        savedPlayer.getHeroType().getMoveBar().redrawAttackBar();
        System.out.println("Reset Player State...");
        GameEngine.add(savedPlayer);
        Override_Save = true;
    }


    /**
     * This method is used to clear the saved location
     */
    public static void clearSave(){
        savedPlayer = null;
        returnLocation = null;
        Override_Save = false;
    }

    public static void deathState(){
        death = true;
    }
}

