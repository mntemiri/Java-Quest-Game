package com.wgd.ecg.engine.UI;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.Input;
import com.wgd.ecg.game.GameUtils;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class GameUIButton extends GameUISprite {
    Color clickColour;
    Color hoverColour;
    Color normalColour;

    Texture clickSprite;
    Texture hoverSprite;
    Texture normalSprite;

    protected boolean isHovering;
    boolean isPressed;
    protected boolean isReleased;

    public GameUIButton(String spriteTexture, float x, float y, float scaleX, float scaleY) {
        super(spriteTexture, x, y, scaleX, scaleY);

        clickColour = new Color(255, 255, 255, 255);
        hoverColour = new Color(255, 255, 255, 255);
        normalColour = new Color(255, 255, 255, 255);

        normalSprite = (Texture) sprite.getTexture();
        hoverSprite = (Texture) sprite.getTexture();
        clickSprite = (Texture) sprite.getTexture();

        isHovering = false;
        isPressed = false;
        isReleased = false;
    }

    public Color getClickColour() {
        return clickColour;
    }

    public void setClickColour(Color clickColour) {
        this.clickColour = clickColour;
    }

    public Color getHoverColour() {
        return hoverColour;
    }

    public void setHoverColour(Color hoverColour) {
        this.hoverColour = hoverColour;
    }

    public Color getNormalColour() {
        return normalColour;
    }

    public void setNormalColour(Color normalColour) {
        this.normalColour = normalColour;
    }

    public Texture getClickSprite() {
        return clickSprite;
    }

    public void setClickSprite(String clickSprite) {
        Texture tempTexture = new Texture();

        try {
            tempTexture.loadFromFile(Paths.get("src/com/wgd/ecg/res/images/" + clickSprite));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.clickSprite = tempTexture;
    }

    public Texture getHoverSprite() {
        return hoverSprite;
    }

    public void setHoverSprite(String hoverSprite) {
        Texture tempTexture = new Texture();

        try {
            tempTexture.loadFromFile(Paths.get("src/com/wgd/ecg/res/images/" + hoverSprite));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.hoverSprite = tempTexture;
    }

    public Texture getNormalSprite() {
        return normalSprite;
    }

    public void setNormalSprite(String normalSprite) {
        Texture tempTexture = new Texture();

        try {
            tempTexture.loadFromFile(Paths.get("src/com/wgd/ecg/res/images/" + normalSprite));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.normalSprite = tempTexture;
    }

    public void onHover() {}

    public void onPressed() {}

    public void onReleased() {}

    public boolean getIsHovering() { return isHovering;  }

    public boolean getIsPressed() { return isPressed;  }

    public boolean getIsReleased() { return isReleased;  }



    private void buttonLogic() {
        if (GameUtils.isMouseColliding(boundingBox)) {
            sprite.setTexture(hoverSprite);
            sprite.setColor(hoverColour);

            onHover();

            if (Input.leftMouse) {
                sprite.setTexture(clickSprite);
                sprite.setColor(clickColour);

                isPressed = true;
                isReleased = false;
                isHovering = true;

                onPressed();
            }
            else if (isPressed) {
                isPressed = false;
                isHovering = true;
                isReleased = true;
                onReleased();
            }
            else {
                isHovering = true;
                isReleased = false;
            }
        }
        else {
            sprite.setTexture(normalSprite);
            sprite.setColor(normalColour);

            isPressed = false;
            isReleased = false;
            isHovering = false;

            onHover();
        }
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        boundingBox = new FloatRect(sprite.getGlobalBounds().left, sprite.getGlobalBounds().top, sprite.getGlobalBounds().width, sprite.getGlobalBounds().height);
        buttonLogic();
    }
}
