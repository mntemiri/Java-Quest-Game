package com.wgd.ecg.game.gameobjects;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.engine.GameStateManager;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.Doorway;
import com.wgd.ecg.game.gameobjects.entities.monsters.Knight;
import com.wgd.ecg.game.gameobjects.generation.Tree;
import org.jsfml.graphics.RenderWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * This class creates the castle
 */

public class Castle extends GameSprite {
    private int level;               //level we want to load to when colliding
    private BufferedImage img;      // the png image of the castle
    private float width;             //width of the image
    private float height;            //height of the image castle
    private float x;                  //xposition of  the castle
    private float y;                 //y position of the castlessssss
    boolean isCollidingWithGameObject;   //check for collision with player
    private Entity castle;
    private Doorway doorway;
    private Random rand = new Random();


    /**
     * The constructor takes
     *
     * @param x             xPosition
     * @param y             yPosition
     * @param scaleX        the width of image
     * @param scaleY        the height of image
     * @param level         the level we want to load
     *                      and creates the castle as well as the collision box
     */
    public Castle(float x, float y, float scaleX, float scaleY, int level) {
        super("castleOpenAndClosed3.png", x, y, scaleX, scaleY, 1, 2, 1);
        castle = new Entity("castleOpenAndClosed3.png", x, y, scaleX, scaleY, 1, 2, 1);
        castle.setMass(2000000f);
        GameEngine.add(castle);
        this.ZDepth = 5;
        this.x = x;
        this.y = y;
        doorway = new Doorway(x - 55, y, 2F, 2f, level, 0, 100, false);

//        System.out.println("LEVEL: " + level);

        if(GameUtils.getPlayer().getInventory().getBeans() == level-5) {
            GameEngine.add(doorway);
            playAnimation(1);
        }
        else {
            playAnimation(2);
        }

        if(GameUtils.getPlayer().getInventory().getBeans()  < level-4) {
            spawnKnights();
        }

        checkIfCollision();

        System.out.println(this.level);
    }

    /**
     * This method checks for collision between the castle  and the trees
     *
     * @return is collision
     */
    public void checkIfCollision() {
        for (GameSprite gamesprite: GameUtils.checkCollisionAllGameSprites(this)) {
            if((gamesprite instanceof Tree))
                GameEngine.remove(gamesprite);
        }
    }

    private void spawnKnights() {
        for(int i = 0; i < 3; i++) {
            GameEngine.add(new Knight((int)(x + (-400 + rand.nextInt(800))), (int)(y + 250 + rand.nextInt(100)), 5));
        }
        GameEngine.add(new Knight((int)x - 50, (int)(y + 250), 5));
        GameEngine.add(new Knight((int)x + 50, (int)(y + 250), 5));
    }

    /**
     * This method returns the  x position of the cave
     * @return x Position
     */
    public float getX() {
        return x;
    }

    /**
     * This method returns the  y position of the castle
     * @return y Position
     */
    public float getY() {
        return y;
    }

    /**
     * This method removed the cave and doorway from the engine
     */
    public void onRemove(){
        GameEngine.remove(castle);
        GameEngine.remove(doorway);
    }

    public void bossDefeated() {

    }

    /**
     * THis method removes the doorway from castle
     */
    public void removeDoor(){
        this.doorway.remove();
    }

    @Override
    public void update(RenderWindow window) {
//        if(GameUtils.getPlayer().getInventory().getBeans()+5 > level) {
//            playAnimation(1);
//        }
    }
}
