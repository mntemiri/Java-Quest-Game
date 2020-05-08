package com.wgd.ecg.engine;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Transform;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

import java.io.Serializable;

/**
 * This class creates the camera of the game
 */
public class Camera implements Serializable {
    View view;
    float interpolationSpeed;

    public Camera() {
        view = GameEngine.view;
        interpolationSpeed = 20f;
//        view.setSize(5000, 5000);
    }

    /**
     * this method sets  the position of camera
     * @param position
     */
    public void interpolateToTarget(Vector2f position) {
        view.setCenter(Vector2f.add(view.getCenter(), Vector2f.mul(Vector2f.sub(position, view.getCenter()), GameEngine.deltaTime * interpolationSpeed)));
    }

    /**
     * This method moves the position of camera
     * @param x
     * @param y
     */
    public void move(float x, float y) {
        view.move(x, y);
    }

    /**
     * this method sets  the position of camera
     * @param x
     * @param y
     */
    public void setPos(float x, float y) {
        view.setCenter(x, y);
    }

    /**
     * This method returns the view position
     * @return
     */
    public Transform getViewPos() {
        return view.getTransform();
    }

    /**
     * This method returns the interpolation position
     * @return
     */
    public float getInterpolationSpeed() {
        return interpolationSpeed;
    }


    /**
     * This method sets the interpolation pos
     * @param interpolationSpeed
     */
    public void setInterpolationSpeed(float interpolationSpeed) {
        this.interpolationSpeed = interpolationSpeed;
    }
}
