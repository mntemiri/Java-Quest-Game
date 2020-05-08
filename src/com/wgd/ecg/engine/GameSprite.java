package com.wgd.ecg.engine;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class creates a GameSprite
 */
public class GameSprite extends GameObject {
    public Sprite sprite;
    Texture texture;
    Vector2i size;

    boolean onScreen;
    FloatRect boundingBox;

    // Animation settings
    boolean isAnimatedSprite;
    int rowCount = 1;
    int columnCount = 0;
    int maxFrameCount = 0;
    int frame = 0 ;
    double lastFrameTime = 0;
    int currentFrameRow, currentFrameCol;
    float animationSpeedInSeconds = 1f;
    float theX , theY;
    int frameWidth, frameHeight;
    private boolean playOne;
    // End of Animation settings

    public GameSprite(Texture texture, float x, float y) {
        sprite = new Sprite(texture);
        size = texture.getSize();
        sprite.setOrigin(size.x / 2, size.y / 2);
        sprite.setPosition(x, y);
        sprite.setScale(1f, 1);

        boundingBox = new FloatRect(sprite.getGlobalBounds().left, sprite.getGlobalBounds().top, sprite.getGlobalBounds().width, sprite.getGlobalBounds().height);
    }

    public GameSprite(Texture texture, float x, float y, float scaleX, float scaleY) {
        sprite = new Sprite(texture);
        size = texture.getSize();
        sprite.setOrigin(size.x / 2, size.y / 2);
        sprite.setPosition(x, y);
        sprite.setScale(scaleX, scaleY);

        boundingBox = new FloatRect(sprite.getGlobalBounds().left, sprite.getGlobalBounds().top, sprite.getGlobalBounds().width, sprite.getGlobalBounds().height);
    }


    public GameSprite(String spriteTexture, float x, float y, float scaleX, float scaleY) {
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

        boundingBox = new FloatRect(sprite.getGlobalBounds().left, sprite.getGlobalBounds().top, sprite.getGlobalBounds().width, sprite.getGlobalBounds().height);
    }

    public GameSprite(String spriteTexture, float x, float y, float scaleX, float scaleY , int columnCount, int rowCount){
        texture = new Texture();
        try {

            texture.loadFromFile(Paths.get("src/com/wgd/ecg/res/images/" + spriteTexture));
            size = texture.getSize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setIfAnimatedSprite(true);
        this.columnCount = columnCount;
        this.rowCount = rowCount;

        this.frameWidth = texture.getSize().x / columnCount;
        this.frameHeight = texture.getSize().y / rowCount;

        sprite = new Sprite(texture);
        sprite.setTextureRect(new IntRect(0, 0, frameWidth, frameHeight));
        sprite.setOrigin(size.x / 2, size.y / 2);
        sprite.setPosition(x , y);
        sprite.setScale(scaleX, scaleY);

        boundingBox = new FloatRect(sprite.getGlobalBounds().left, sprite.getGlobalBounds().top, sprite.getTextureRect().width, sprite.getTextureRect().height);
    }

    public GameSprite(String spriteTexture, float x, float y, float scaleX, float scaleY , int columnCount, int rowCount, float animationSpeedInSeconds){
        texture = new Texture();
        try {

            texture.loadFromFile(Paths.get("src/com/wgd/ecg/res/images/" + spriteTexture));
            size = texture.getSize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setIfAnimatedSprite(true);
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.animationSpeedInSeconds = animationSpeedInSeconds;

        this.frameWidth = texture.getSize().x / columnCount;
        this.frameHeight = texture.getSize().y / rowCount;

        sprite = new Sprite(texture);
        sprite.setTextureRect(new IntRect(0, 0, frameWidth, frameHeight));
        sprite.setOrigin(size.x / 2, size.y / 2);
        sprite.setPosition(x, y);
        sprite.setScale(scaleX, scaleY);

        boundingBox = new FloatRect(sprite.getGlobalBounds().left, sprite.getGlobalBounds().top, sprite.getTextureRect().width, sprite.getTextureRect().height);
    }

    public GameSprite(Texture spriteTexture, float x, float y, float scaleX, float scaleY , int columnCount, int rowCount, float animationSpeedInSeconds){


        this.setIfAnimatedSprite(true);
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.animationSpeedInSeconds = animationSpeedInSeconds;

        this.frameWidth = spriteTexture.getSize().x / columnCount;
        this.frameHeight = spriteTexture.getSize().y / rowCount;

        size = spriteTexture.getSize();

        sprite = new Sprite(spriteTexture);
        sprite.setTextureRect(new IntRect(0, 0, frameWidth, frameHeight));
        sprite.setOrigin(size.x / 2, size.y / 2);
        sprite.setPosition(x, y);
        sprite.setScale(scaleX, scaleY);

        boundingBox = new FloatRect(sprite.getGlobalBounds().left, sprite.getGlobalBounds().top, sprite.getTextureRect().width, sprite.getTextureRect().height);
    }

    public FloatRect getBoundingBox() {
        return boundingBox;
    }

    public boolean isOnScreen() {
        return onScreen;
    }

    private void occulusionCheck() {
        boundingBox = new FloatRect(sprite.getGlobalBounds().left, sprite.getGlobalBounds().top, sprite.getGlobalBounds().width, sprite.getGlobalBounds().height);
        if (boundingBox.intersection(GameEngine.boundingBox) == null) onScreen = false;
        else onScreen = true;
    }

    public void changeSprite(String path) {
        texture = new Texture();
        try {

            texture.loadFromFile(Paths.get("src/com/wgd/ecg/res/images/" + path));
            size = texture.getSize();
        } catch (IOException e) {
            e.printStackTrace();
        }


        sprite = new Sprite(texture);
    }

    /**
     * Plays animation at beginning of texture with same size properties as the last texture, this plays the animation instantly
     * @param texture Path of texture location
     */
    public void playAnimation(String texture) {
        try {
            this.texture.loadFromFile(Paths.get("src/com/wgd/ecg/res/images/" + texture));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sprite.setTexture(this.texture);

        lastFrameTime = 0;
        currentFrameCol = 0;
        currentFrameRow = 0;
    }


    /**
     * Plays animation at set point of texture with same size properties as the last texture, this plays the animation instantly
     * @param texture Path of texture location
     * @param columnCount Amount of columns to spritesheet
     * @param rowCount Which row to play at
     */
    public void playAnimation(String texture, int columnCount, int rowCount) {
        try {
            this.texture.loadFromFile(Paths.get("src/com/wgd/ecg/res/images/" + texture));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.frameWidth = this.texture.getSize().x / columnCount;
        this.frameHeight = this.texture.getSize().y / rowCount;

        sprite.setTexture(this.texture);

        lastFrameTime = 0;
        this.columnCount = columnCount;
        this.rowCount = rowCount;
    }

    /**
     * Plays animation on a certain row
     * @param rowCount row that animation is on
     */
    public void playAnimation(int rowCount) {
        lastFrameTime = 0;
        this.frame = 0;

        this.rowCount = rowCount;
    }

    public void setFrame(int value){
        frame = value;
    }

//    /**
//     * Plays animation at set point of texture with also set row/column count to divide texture size with, to make it into chunks
//     * @param frame
//     */
//    public void playAnimation(int frame) {
//        lastFrameTime = 0;
//        this.frame = 0;
//    }

    /**
     * Event function called when final frame of animation reached
     */
    void OnAnimationFinished() {
//        System.out.println("Finished");
        if(playOne){
            animationSpeedInSeconds = 0;
            playOne = false;
        }
    }

    /**
     * Plays animation for sprite, linked to GameClock
     */
    protected void animationPlayer() {
        if(animationSpeedInSeconds == 0){
            return;
        }
        sprite.setOrigin(frameWidth / 2, frameHeight / 2);
        maxFrameCount = columnCount;

//        sprite.setTextureRect(new IntRect(currentFrameRow, currentFrameCol, frameWidth, frameHeight));
        if(GameEngine.window.isOpen()) {
            if (GameClock.timeInSEC() - lastFrameTime >= animationSpeedInSeconds) {
                //Restart the clock
                lastFrameTime = GameClock.timeInSEC();
                frame++;
                if (frame > maxFrameCount-1) {
                    OnAnimationFinished();
                    frame = 0;
                }
                int frameRow = (rowCount * frameHeight) - frameHeight;
                int frameCol = frame % columnCount;

//                System.out.println(frameCol * frameWidth + " " + frameRow + " " + frameWidth + " " + frameHeight);
                sprite.setTextureRect(new IntRect(frameCol * frameWidth, frameRow, frameWidth, frameHeight));
            }
        }
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        occulusionCheck();
        if(isAnimatedSprite)
            animationPlayer();
        if (onScreen)
            window.draw(sprite);
    }

    public void setBoundingBox(FloatRect boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Vector2f getPosition(){
        return sprite.getPosition();
    }

    public boolean isAnimatedSprite() {
        return isAnimatedSprite;
    }

    public void setIfAnimatedSprite(boolean animatedSprite) {
        isAnimatedSprite = animatedSprite;
    }

    public float getAnimationSpeedInSeconds() {
        return animationSpeedInSeconds;
    }

    public void setAnimationSpeedInSeconds(float animationSpeedInSeconds) {
        this.animationSpeedInSeconds = animationSpeedInSeconds;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getMaxFrameCount() {
        return maxFrameCount;
    }

    public void setMaxFrameCount(int maxFrameCount) {
        this.maxFrameCount = maxFrameCount;
    }

    public void setCurrentFrameRow(int row){
        currentFrameRow = row;
    }

    public void setPos(float xPos , float yPos){
        sprite.setPosition(xPos, yPos);
    }

    public void playOne(float animationSpeedInSeconds){
        this.animationSpeedInSeconds = animationSpeedInSeconds;
        playOne = true;
    }
}
