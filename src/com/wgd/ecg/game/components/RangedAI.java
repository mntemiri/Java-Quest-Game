package com.wgd.ecg.game.components;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.game.GameUtils;
import com.wgd.ecg.game.gameobjects.Entity;
import com.wgd.ecg.game.gameobjects.Projectile;
import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;
import org.jsfml.system.Vector2f;

/**
 * This class creates a ranged AI
 */
public class RangedAI extends AI {
    String projectileName;


    public RangedAI(Monster host){
        super(host);
        projectileName = "arrow.png";
    }

    //Fires at the player

    @Override
    protected void hitPlayer() {

        if(System.currentTimeMillis() - lastAttackTime < 1000){
            return;
        }
        Vector2f monPos = host.getPosition();
        Vector2f playerPos = GameUtils.playerPosition();
        playerPos = Vector2f.add(playerPos, GameUtils.getPlayer().getVelocity());
        Vector2f direction = Entity.normaliseVector(Vector2f.sub(playerPos, monPos));
        direction = GameUtils.normaliseVector(direction);

        //Get spawn location of arrow
        Vector2f instancePos = Vector2f.mul(Entity.normaliseVector(Vector2f.sub(playerPos, monPos)), 90);
        instancePos = Vector2f.add(instancePos, monPos);

        double angle = GameUtils.getAngleBetween(playerPos, monPos);

        Projectile arrow = new Projectile(projectileName, instancePos.x,  instancePos.y, 1.5f, 1.5f, host.getDamage(), true);
        arrow.setMaxVelocity(1);
        arrow.setFriction(0.1f);
        arrow.sprite.rotate(-1* (int) angle);
        arrow.addForce(Entity.normaliseVector(direction), 200f);
        checkDirection(direction);
        GameEngine.add(arrow);
        lastAttackTime = System.currentTimeMillis();

    }

    /**
     * This method is used to set the projectile
     * @param type
     */
    public void setProjectile(String type){
        projectileName = type;
    }
}
