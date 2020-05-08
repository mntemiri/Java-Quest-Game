package com.wgd.ecg.game.gameobjects.entities.heroes;

import com.wgd.ecg.engine.*;
import com.wgd.ecg.engine.UI.GameUISprite;
import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.GameUtils;
//import com.wgd.ecg.game.components.LevelLoader;
import com.wgd.ecg.game.components.UI.Pause_MenuOverlay;
import com.wgd.ecg.game.components.player.Inventory;
import com.wgd.ecg.game.components.player.DamageSystem;
import com.wgd.ecg.game.components.player.PlayerStatSystem;
import com.wgd.ecg.game.gameobjects.*;
import com.wgd.ecg.game.gameobjects.entities.monsters.Goblin;
import com.wgd.ecg.game.gameobjects.entities.monsters.Monster;
import com.wgd.ecg.game.gameobjects.entities.monsters.Skeleton;
import com.wgd.ecg.game.gameobjects.entities.monsters.Zombie;
import com.wgd.ecg.game.gameobjects.generation.Tree;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Player class for main character and controls
 */
public class Player extends Entity implements Serializable {
    private transient Camera camera;
    private float force = 200f;
    private float maxVelocity = 0.75f; // Max velocity variable
    private Inventory inventory;
    private PlayerStatSystem level = new PlayerStatSystem(1, 100, 0);;
    public DamageSystem health = new DamageSystem(100, true);
    private int damage = 10; //A number to save the amount of damage the player deals per standard hit.
    private Hero heroType;
    public static boolean start = true;
    private transient GameUISprite healthBarFilling; //This is the filling of the health bar.
    private transient GameUIText healthBarCount; //This is a counter displayed on the health bar.
    private transient GameUISprite levelBarFilling; //This is the filling of the level bar.
    private transient GameUIText levelBarCount; //This is a counter displayed on the health bar.
    private Weapon weapon; //The weapon sprite overlaying the player
    private double lastHealing = GameClock.timeInMS();
    private double lastHit;
    private boolean beatBossOne = false;
    private boolean beatBossTwo = false;
    private boolean beatBossThree = false;

    private boolean spawnEnemies = true; //Shall we spawn enemies on the player?
    private double lastSpawned; //Time we last spawn an enemy
    private int spawnRate = 500; //Number of ms to spawn a monster


    private String heroSelection;


    private float  width , height;//this is used to get the position and the dimensions of player
    private float xPos , yPos;
    private transient BufferedImage img ;
    private boolean movementEnabled = true;
    private boolean facingRight = true;


    public Player(String spriteTexture, float x, float y, float scaleX, float scaleY, String heroSelection) {

        super(spriteTexture, x, y, scaleX, scaleY, 8, 1, 0.15f);
        camera = new Camera();
        try {
            img = ImageIO.read(new File("src/com/wgd/ecg/res/images/" + spriteTexture));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = img.getWidth() * scaleX;
        height = img.getHeight() * scaleY;
        mass = 70f;
        friction = 2f;
        inventory = new Inventory(this);

        setMaxVelocity(maxVelocity); // Max velocity is little like this cause of sprite and camera scale

        setClass(heroSelection);
        userInterfaceInit();
    }

    /**
     * Sets the type of hero - this controls the sprite drawn for the Player and also their weapon.
     * @param heroSelection The type of hero to set the Player as
     */
    public void setClass(String heroSelection){
        this.heroSelection = heroSelection;
        switch(heroSelection){
            case "Wizard":
                this.heroType = new Wizard();
                weapon = new Weapon("staff.png", xPos, yPos, 2f, 2f, 25);
                GameEngine.add(weapon);
                break;
            case "Hunter":
                this.heroType = new Hunter();
                weapon = new Weapon("bow.png", xPos, yPos, 3f, 3f, 0);
                GameEngine.add(weapon);
                break;
            case "Warrior":
                this.heroType = new Warrior();
                weapon = new Weapon("sword.png", xPos, yPos, 3f, 3f, 25, 3, 1);
                GameEngine.add(weapon);
                health.increaseMaxHealth(100);
                break;
        }
    }

    public PlayerStatSystem getStats(){
        return this.level;
    }


    public void getHealthExp(){
        userInterfaceInit();
    }

    private void userInterfaceInit() {
        //Adding the frame of the health bar.
        GameUISprite barFrame = new GameUISprite("barframe.png",  0, GameEngine.HEIGHT/ 2 - 40, 4, 4);
        barFrame.toggleFollowView();
        GameEngine.add(barFrame);

        //Adding the filling for the health bar.
        healthBarFilling = new GameUISprite("healthbar.png", -220, GameEngine.HEIGHT/2 -52, 440, 4);
        healthBarFilling.toggleFollowView();
        GameEngine.add(healthBarFilling);

        //Adding the health bar counter.
        healthBarCount = new GameUIText(Integer.toString(health.getHealth()),"PressStart2P", 0, GameEngine.HEIGHT/2 -50, 15);
        GameEngine.add(healthBarCount);

        //Adding the level bar filling.
        levelBarFilling = new GameUISprite("levelbar.png", -220, GameEngine.HEIGHT/2 - 24, 440, 4);
        levelBarFilling.toggleFollowView();
        GameEngine.add(levelBarFilling);

        //Adding the level bar counter.
        levelBarCount = new GameUIText("LVL:" + level.getLevel() + " XP:" + level.getXP() + "/" + level.nextLevel(),"PressStart2P", 0, GameEngine.HEIGHT/ 2 - 23, 10);
        GameEngine.add(levelBarCount);
    }

    /**
     * Updates every frame for user control input, specifically to add force and move the character.
     * Also used to set max velocity of character.
     */
    public void userControls()
    {
        //Old static movement code
//        sprite.move(input.horizontalInt * GameEngine.deltatime * speed, input.verticalInt * GameEngine.deltatime * speed);

        // Vector for user input
        Vector2f direction = new Vector2f(Input.horizontalInt, Input.verticalInt);

        // Need to have a normalised vector for direction, and apply speed as next argument
        // Will probably improve this but for now like this
        // physics already has deltaTime handled so don't need to worry about multiplying any values with that
        if(movementEnabled){
            addForce(direction, force);
            checkDirection(direction);
        }

        //Make player stand still when not moving
        if(Input.horizontalInt == 0 && Input.verticalInt == 0){
            setFrame(0);
        }


        // Interpolates camera to players
        camera.interpolateToTarget(getPosition());

        //setMaxVelocity(maxVelocity); // Max velocity is little like this cause of sprite and camera scale

        for(GameSprite tree : GameUtils.checkCollisionAllGameSprites(this)) {
            if (tree instanceof Tree && tree.isOnScreen())
                ((Tree) tree).transparencyIncrease();
        }

        for(GameSprite triggerBox : GameUtils.checkCollisionAllGameSprites(this)) {
            if (triggerBox instanceof TriggerBox && triggerBox.isOnScreen())
                ((TriggerBox) triggerBox).OnTrigger();
        }



        /*if(Input.moveOne)
            GameEngine.add(new Entity("zombieright.png", (GameEngine.view.getCenter().x - GameEngine.WIDTH / 2) + Input.mousePos.x, (GameEngine.view.getCenter().y - GameEngine.HEIGHT / 2) + Input.mousePos.y, 0.1f, 0.1f));
         */
        if(Input.basicAttack)
            heroType.basicAttack();

        if(Input.moveOne)
            heroType.moveOne();

        if(Input.currentlyAttackingOne)
            heroType.moveOneContinue();

        if(Input.moveTwo)
            heroType.moveTwo();

        if (Input.pause)
            Pause_MenuOverlay.ShowMenu();
    }

    private void userInterface() {
        level.levelSystemUpdate();

        inventory.inventoryUpdate();

        healthBarCount.setStringCenter(Integer.toString(health.getHealth()));

        healthBarFilling.setScale(((float) health.getHealth() / (float) health.getMaxHealth()) * 440, 4);

        levelBarCount.setStringCenter("LVL:" + level.getLevel() + " XP:" + level.getXP() + "/" + level.nextLevel());

        levelBarFilling.setScale(((float) level.getXP() / (float) level.nextLevel()) * 440, 4);
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        userControls();
        userInterface();
        heroType.moveBar.attackOptionBarUpdate();

        if(health.getHealth() == 0) {
            inventory.deathEvent();
            level.deathPenalty();
            health.increaseHealth(health.getMaxHealth());
        }
//        if(spawnEnemies){
//            spawnEnemies();
//        }

        if(heroType instanceof Warrior && GameClock.timeInMS() - lastHealing > 1000) {
            health.increaseHealth(health.getMaxHealth() / 100);
            lastHealing = GameClock.timeInMS();
        }

        if(GameClock.timeInMS() - lastHit > 200){
            sprite.setColor(new Color(255, 255, 255, 255));
        }
    }

    public float getMaxVelocity(){
        return this.maxVelocity;
    }

    public Hero getHeroType() {
        return heroType;
    }

    public void basicAttack(){
        this.heroType.basicAttack();
    }

    public  float getPlayerWidth(){ return width; }

    public  float getPlayerHeight(){ return height; }

    public int getDamage() { return damage; }

    public void setDamage(int temp) { damage = temp; }

    public Inventory getInventory(){return this.inventory;}

    public void increaseMaxVelocity(float increase) {
        this.maxVelocity += increase;

        setMaxVelocity(this.maxVelocity);
    }

    public void decreaseMaxVelocity(float decrease) {
        this.maxVelocity -= decrease;

        setMaxVelocity(this.maxVelocity);
    }

    public void setMovementEnabled(boolean value){
        movementEnabled = value;
    }

    public PlayerStatSystem getLevel(){
        return level;
    }

    public DamageSystem getHealth(){
        return health;
    }

    protected void checkDirection(Vector2f direction){
        if(direction.x < 0 && facingRight){
            facingRight = false;
            sprite.scale(-1.f,1.f);
        }else if(direction.x > 0 && facingRight == false){
            facingRight = true;
            sprite.scale(-1.f,1.f);
        }
    }

    /**
     * Reads the weapon to the player
     */
    public void refreshWeapon(){
        switch(heroSelection){
            case "Wizard":
                weapon = new Weapon("staff.png", xPos, yPos, 2f, 2f, 25);
                GameEngine.add(weapon);
                break;
            case "Hunter":
                weapon = new Weapon("bow.png", xPos, yPos, 3f, 3f, 0);
                GameEngine.add(weapon);
                break;
            case "Warrior":
                weapon = new Weapon("sword.png", xPos, yPos, 3f, 3f, 25, 3, 1);
                GameEngine.add(weapon);
                break;
        }
    }

    /**
     * Gets the players weapon
     * @return The weapon of the player
     */
    public Weapon getWeapon(){
        return this.weapon;
    }

    public void reDrawUI(){
        this.userInterfaceInit();
        this.inventory.reDrawComponents();
        this.heroType.moveBar.draw();
    }

    public void spawnEnemies(){
        if(GameClock.timeInMS() - lastSpawned < spawnRate){
            return;
        }
        int radius = 1000;
        Random random = new Random();
        int angle = random.nextInt(360);
        int mob = random.nextInt(2);

        //Place mob at specified angle
        Vector2f radiusPos = new Vector2f((float) Math.sin(Math.toRadians(angle)) * radius,  (float) Math.cos(Math.toRadians(angle)) * radius);
        Vector2f position = Vector2f.add(this.getPosition(), radiusPos);
        boolean success = true;
        Monster monster = null;
        ArrayList<GameSprite> collidingWith;

        switch(mob) {
            case 0:
                //Spawn goblin
                monster = new Goblin((int) position.x, (int) position.y, 4f, 4f);
                collidingWith = GameUtils.checkCollisionAllGameSprites(monster);
                for (GameSprite collidingSprite : collidingWith) {
                    if(collidingSprite instanceof Monster){
                        success = false;
                    }
                }
                break;

            case 1:
                //Spawn skeleton
                monster = new Skeleton((int) position.x, (int) position.y, 4f, 4f);
                collidingWith = GameUtils.checkCollisionAllGameSprites(monster);
                for (GameSprite collidingSprite : collidingWith) {
                    if(collidingSprite instanceof Monster){
                        success = false;
                    }
                }
                break;
        }

        if(success){
            if(monster != null){
                GameEngine.add(monster);
                lastSpawned = GameClock.timeInMS();
            }
        }else{
            System.out.println("Fail");
        }
    }

    public void hit(){
        lastHit = GameClock.timeInMS();
        sprite.setColor(new Color(255, 100, 100, 255));
    }
    public boolean hasBeatBossOne(){
        return this.beatBossOne;
    }
    public boolean hasBeatBossTwo(){
        return this.beatBossTwo;
    }
    public boolean hasBeatBossThree(){
        return this.beatBossThree;
    }

    public void setBeatBossOne(Boolean value){
        beatBossOne = value;
    }
    public void setBeatBossTwo(Boolean value){
        beatBossTwo = value;
    }
    public void setBeatBossThree(Boolean value){
        beatBossThree = value;
    }
}


