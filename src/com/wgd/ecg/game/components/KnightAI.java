package com.wgd.ecg.game.components;

import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;
import org.jsfml.system.Vector2f;

/**
 * This class creates the knight
 */
public class KnightAI extends AI{
    private boolean goingBackToStartingPosition = false;
    private Vector2f startPosition;
//    protected Vector2f direction = com.wgd.ecg.game.GameUtils.normaliseVector(getRandomVector());

    public KnightAI(Monster host) {
        super(host);
        startPosition = new Vector2f(host.getStartingXPosition(), host.getStartingYPosition());
    }

    @Override
    public void brainUpdate() {
        if(inRange()) {
            hitPlayer();
        }
        else {
            if(goingBackToStartingPosition) {
                if(inRangeOfStartingPosition(50)) {
                    host.playAnimation(1);

                    host.setAnimationSpeedInSeconds(0.5f);

                    goingBackToStartingPosition = false;
                }
                else {
                    goBackToStart();
                }
            }
            else {
                if(inSight()) {
                    if(chasing) {
                        chase(true);
                    }
                    else {
                        chase(true);

                        host.playAnimation(2);

                        host.setAnimationSpeedInSeconds(0.2f);
                    }
                }
                else {
                    if(inRangeOfStartingPosition()) {
                        if(chasing) {
                            host.playAnimation(1);

                            host.setAnimationSpeedInSeconds(0.5f);

                            chasing = false;
                        }
                    }
                    else {
                        chasing = false;

                        goingBackToStartingPosition = true;

                        host.playAnimation(2);

                        host.setAnimationSpeedInSeconds(0.2f);
                    }
                }
            }
        }
    }

    /**
     * This method is used to put the knight in the start position
     */
    private void goBackToStart() {
        direction = new Vector2f(host.getStartingXPosition() - host.getXPos(), host.getStartingYPosition() - host.getYPos());
        direction = com.wgd.ecg.game.GameUtils.normaliseVector(direction);
        checkDirection(direction);
        host.addForce(direction, speed);
    }

    /**
     * This method returns true if the knight is in range of starting pos
     * @return
     */
    private boolean inRangeOfStartingPosition() {
        return GameUtils.findMagnitude(Vector2f.sub(host.getPosition(), startPosition)) < 100;
    }

    /**
     * This method returns true if the knight is in range of starting pos
     * @return
     */
    private boolean inRangeOfStartingPosition(float range) {
        return GameUtils.findMagnitude(Vector2f.sub(host.getPosition(), startPosition)) < range;
    }
}
