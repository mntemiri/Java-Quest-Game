package com.wgd.ecg.engine.UI;

import com.wgd.ecg.engine.GameObject;
import org.jsfml.graphics.RenderWindow;

public class GameUI extends GameObject {
    Boolean followView;

    public GameUI() {
        ZDepth = UI;
        followView = true;
    }

    public void toggleFollowView() {
        this.followView = !followView;
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);

    }
}
