package com.wgd.ecg.game.gameobjects.entities.heroes;

import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameSprite;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.components.player.Inventory;
import com.wgd.ecg.game.gameobjects.Entity;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

/**
 * This class creates the old man that explains the goal to the player
 */
public class OldMan extends Entity {
    private double lastSpoke;
    private int line;
    private GameSprite speechBubble;
    private int numLines;
    private boolean speaking;
    int beans;
    private boolean firstLine;
    public OldMan(float x, float y){
        super("oldman_animation.png", x, y, 4f, 4f, 4, 2, 2.5f);
        setMass(90000f);
        lastSpoke = -100;
    }

    //Speak on collision
//    @Override
//    public void OnCollision(Entity collider, Entity colliding, FloatRect colliderRect) {
//        if(GameClock.timeInSEC() - lastSpoke> 5f){
//            say(line);
//            lastSpoke = GameClock.timeInSEC();
//            line++;
//            if(line == 7){
//                line = 4;
//            }
//        }
//    }



    /**
     * This method is used to read the png images
     * @param line each image number
     */
    public void say(String line){
        speechBubble = new GameSprite("/speech/speech" + line + ".png", getPosition().x, getPosition().y - 75, 1.5f, 1.5f);
        GameEngine.add(speechBubble);
        speechBubble.ZDepth = 3;
        setCurrentFrameRow(2);
        setAnimationSpeedInSeconds(0.2f);

    }

    //Cycles through voice lines and animations
    @Override
    public void update(RenderWindow window) {
        super.update(window);
        try{
            this.beans = GameUtils.getPlayer().getInventory().getBeans();
        }catch(Exception e){
            beans = 0;
        }


        if(GameClock.timeInSEC() - lastSpoke > 4.5f && GameClock.timeInSEC() - lastSpoke <5f){
            GameEngine.remove(speechBubble);
            setCurrentFrameRow(1);
            setAnimationSpeedInSeconds(2.5f);
        }
        else if(GameClock.timeInSEC() - lastSpoke > 5f){
            if(beans == 0){
                say(line + "");
                lastSpoke = GameClock.timeInSEC();

                line++;
                if(line == 7) {
                    line = 4;
                }
            }else if(beans == 1){
                say("b" + beans + "_" + line);
                lastSpoke = GameClock.timeInSEC();
                line++;

                if(line == 3) {
                    line = 0;
                }
            }else if(beans == 2){
                say("b" + beans + "_" + line);
                lastSpoke = GameClock.timeInSEC();
                line++;

                if(line == 3) {
                    line = 0;
                }
            }else if(beans == 3){
                say("b" + beans + "_" + line);
                lastSpoke = GameClock.timeInSEC();
                line++;

                if(line == 5) {
                    line = 2;
                }
            }
        }
    }
}
