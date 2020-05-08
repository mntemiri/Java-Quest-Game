package com.wgd.ecg.game.gameobjects;

import com.wgd.ecg.engine.*;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.Collider;
import com.wgd.ecg.game.components.Doorway;
import com.wgd.ecg.game.gameobjects.generation.Tree;
import com.wgd.ecg.game.levels.Dungeon;
import org.jsfml.graphics.RenderWindow;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * this class  creates the cave
 */
public class Cave extends GameSprite {
    protected static final int Centre_X = GameEngine.WIDTH / 2;
    protected static final int Centre_Y = GameEngine.HEIGHT / 2;
    private int level;
    private BufferedImage img;
    private float width;
    private float height;
    private float x;
    private float y;
    private float scaleX;
    private float scaleY;
    boolean isCollidingWithGameObject;
    private PlayerStateManager playerStateManager = new PlayerStateManager();
    Entity cave;
    Doorway doorway;

    public Cave(float x, float y, float scaleX, float scaleY, int level) {
        super("cave.png", x, y, scaleX, scaleY, 1, 2);
        try {
            img = ImageIO.read(new File("src/com/wgd/ecg/res/images/cave.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = img.getWidth() * scaleX/2;
        height = img.getHeight() * scaleY/2;
        this.x = x;
        this.y = y;
        this.level = level;
        this.scaleX = scaleX;
        this.scaleY = scaleY;

        cave = new Entity("caveSolo.png", x, y, scaleX, scaleY);

        init();

        checkIfCollision();

        playAnimation(1);
    }


    /**
     * This method checks for collision between the cave and the other caves and castles
     * @param caves  other caves
     * @param castles  other castles
     * @return is collision
     */
    public boolean checkIfCollision(Cave[] caves, Castle[] castles) {
        for (Cave temp: caves) {
            if (temp == null) break;
            if (GameUtils.checkCollisionWithGameSprite(temp, cave))
                return true;
        }

        for (Castle temp: castles) {
            if (temp == null) break;
            if (GameUtils.checkCollisionWithGameSprite(temp, cave)) {
                return true;
            }
        }

        init();
        return false;
    }

    /**
     * This method adds a doorway to the cave
     */
    public void init() {
        cave.setMass(2000000f);
        GameEngine.add(cave);

        Vector2f entrance = new Dungeon().getDungeonEntrance();
        doorway = new Doorway(x, y + (height / 2), 1.5f, 2f, GameStateManager.DUNGEONSTATE, entrance.x, entrance.y, false);

        GameEngine.add(doorway);
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
    }

    /**
     * This method removed the cave and doorway from the engine
     */
    public void onRemove(){
        GameEngine.remove(cave);
        GameEngine.remove(doorway);
    }

    /**
     * This method returns the  x position of the cave
     * @return x Position
     */
    public float getX() {
        return x;
    }

    /**
     * This method returns the  y position of the cave
     * @return y Position
     */
    public float getY() {
        return y;
    }

    /**
     * This method checks for collision between the player and the cave
     */
    public void checkIfCollision() {
        for (GameSprite gamesprite: GameUtils.checkCollisionAllGameSprites(this)) {
            if((gamesprite instanceof Tree))
                GameEngine.remove(gamesprite);
        }
    }
}