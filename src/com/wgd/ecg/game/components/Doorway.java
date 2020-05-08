package com.wgd.ecg.game.components;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.PlayerStateManager;
import com.wgd.ecg.engine.TriggerBox;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;

public class Doorway extends TriggerBox {
    public float ExitX;
    public float ExitY;
    public int LevelState;
    public boolean isTransparent;
    public boolean isExit;
    PlayerStateManager playerStateManager = new PlayerStateManager();
    /**
     * This is used to create an entrance
     * @param x xPosition of door
     * @param y yPosition of door
     * @param sizeX width of door
     * @param sizeY height of door
     * @param levelState the level to load
     * @param respawnPointX the x respawn location inside the passed level state
     * @param respawnPointY the y respawn location inside the passed level state
     * @param isTransparent sets the door visible/not-visible
     */
    public Doorway(float x, float y, float sizeX, float sizeY, int levelState, float respawnPointX, float respawnPointY, boolean isTransparent) {
        super(x, y, sizeX, sizeY);
        this.LevelState = levelState;
        this.ExitX = respawnPointX;
        this.ExitY = respawnPointY;
        this.isTransparent = isTransparent;
        if(isTransparent) {
            this.sprite.setColor(new Color(255, 255, 255, 0));
        }
        this.isExit = false;
    }

    /**
     * This is used as an exit door when collided with will return you just below the entrance door
     * @param x x location of the exit door
     * @param y y location of the exit door
     * @param sizeX width of door
     * @param sizeY height of door
     * @param isTransparent sets the door visible/not-visible
     */
    public Doorway(float x, float y, float sizeX, float sizeY, boolean isTransparent){
        super(x, y, sizeX, sizeY);
        this.LevelState = 1;
        this.isTransparent = isTransparent;
        if(isTransparent) {
            this.sprite.setColor(new Color(255, 255, 255, 0));
        }
        this.isExit = true;
    }

    @Override
    public void OnTrigger() {
        System.out.println("Trigger Doorway");
        GameEngine.loadLevel(LevelState);
        if(isExit) {
            PlayerStateManager.resetPlayerLocation();
        }
        else {
            PlayerStateManager.savePlayer();
            PlayerStateManager.loadPlayer(ExitX, ExitY);
        }

    }

    /**
     * Removes the door from the engine
     */
    public void remove(){
        GameEngine.remove(this);
    }

}

