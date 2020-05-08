package com.wgd.ecg.engine.UI;

import com.wgd.ecg.engine.GameEngine;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.nio.file.Paths;

public class GameUISprite extends GameUI {
    public Sprite sprite;
    Texture texture;
    Vector2i size;

    float scaleX, scaleY;

    FloatRect boundingBox;

    float x, y;


    public GameUISprite (String spriteTexture, float x, float y, float scaleX, float scaleY) {
        texture = new Texture();
        try {

            texture.loadFromFile(Paths.get("src/com/wgd/ecg/res/images/" + spriteTexture));
            size = texture.getSize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sprite = new Sprite(texture);
        sprite.setOrigin(size.x / 2, size.y / 2);
        sprite.setPosition(x, y);
        sprite.setScale(scaleX, scaleY);

        this.x = x;
        this.y = y;

        this.scaleX = scaleX;
        this.scaleY = scaleY;

        boundingBox = new FloatRect(sprite.getGlobalBounds().left, sprite.getGlobalBounds().top, sprite.getGlobalBounds().width, sprite.getGlobalBounds().height);

        followView = false;
    }

    private void followPlayer() {
        if (followView)
            sprite.setPosition(Vector2f.add(GameEngine.view.getCenter(), new Vector2f(x, y)));
        else
            sprite.setPosition(new Vector2f(x, y));
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;

        sprite.setScale(scaleX, scaleY);
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        window.draw(sprite);
        followPlayer();
    }
}
