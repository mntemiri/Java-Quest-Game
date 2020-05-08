package com.wgd.ecg.game.gameobjects;

import com.wgd.ecg.engine.GameClock;
import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameObject;
import com.wgd.ecg.game.GameUtils;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

/**
 * This class creates a bomb
 */
public class Bomb extends Entity {
    float targetX;
    float targetY;
    double armTime;
    double timer;
    int maxShards = 40;
    int radius = 100;
    public Bomb(float x, float y, float targetX, float targetY, double timer){
        super("bomb.png",  x,  y,  2f,  2f);
        Vector2f direction = Vector2f.sub(new Vector2f(targetX, targetY), new Vector2f(x, y));
        addForce(direction, 200);
        this.targetY = targetY;
        this.targetX = targetX;
        this.timer = timer;
        armTime = GameClock.timeInMS();
    }
    @Override
    public void update(RenderWindow window) {
        super.update(window);
        Vector2f pos = getPosition();
        if(targetX - pos.x < 10 && targetY - pos.y < 10){
            setFriction(10000f);
        }
        if(GameClock.timeInMS() - armTime > timer){
            explode();
        }
    }

    /**
     * This method is used to create the bomb explosion animation
     */
    private void explode(){
        System.out.println("Explode");
        GameEngine.remove(this);
        Vector2f thisPos = getPosition();
        for(int i = 0; i < 40; i++){
            double placeAngle = i * (360/maxShards);
            Vector2f radiusPos = new Vector2f((float) Math.sin(Math.toRadians(placeAngle)) * radius,  (float) Math.cos(Math.toRadians(placeAngle)) * radius);
            Vector2f position = Vector2f.add(thisPos, radiusPos);
            Projectile shard = new Projectile("red_bolt.png", position.x, position.y, 0.3f, 0.3f, (int) 5, true, (double) 10000);


            shard.setMaxVelocity(1f);
            Vector2f force = Entity.normaliseVector(Vector2f.sub(shard.getPosition(), thisPos));
            shard.addForce(force, 200f);

            double angle = GameUtils.getAngleBetween(shard.getPosition(), thisPos);

            shard.sprite.rotate(-1 * (int) angle);
            GameEngine.add(shard);
        }

    }

}
