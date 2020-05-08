package com.wgd.ecg.engine;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.KeyEvent;


/**
 * Input class, can be instantiated and will update variables for input use.
 */
public class Input extends GameObject {
    private static final Input instance = new Input(); // SINGLETON INSTANCE

    private Keyboard keyboard;
    private Mouse mouse;
//    private KeyEvent keyE;

    // CONTROLS

    public static String pauseControl = "ESC";  //james
    public static String forwardControl = "W";
    public static String backwardControl = "S";
    public static String leftControl = "A";
    public static String rightControl = "D";
    public static String specialAttackControl = "F";
    public static String basicAttackControl = "LEFTMOUSE";
    public static String altAttackControl = "RIGHTMOUSE";
    public static String moveOneControl = "1";
    public static String moveTwoControl = "2";
    public static String healthPotionControl = "3";
    public static String speedPotionControl = "4";
    public static String strengthPotionControl = "5";
//    public static String interact = "E";
    public static String devKey1 = "B";
    public static String devKey2 = "N";
    public static String devKey3 = "M";

    // END OF CONTROLS

    // TO USE //
    public static Vector2i vertical = new Vector2i(0, 0); // Vertical
    public static Vector2i horizontal = new Vector2i(0, 0);
    public static Vector2i mousePos = new Vector2i(0, 0);

    public static boolean pause; //james Pause key

    public static float verticalInt = 0; // Vertical int takes in w and s input
    public static float horizontalInt = 0; // Horizontal int takes in a and d as input

    public static boolean leftMouse; // Left mouse click input
    public static boolean rightMouse; // Right mouse click input

//    public static boolean interaction;
    public static boolean specialAttack; // Special attack input
    public static boolean basicAttack; // Basic attack input
    public static boolean altAttack; // Alternative attack input
    public static boolean moveOne; // Move one input
    public static boolean moveTwo; // Move two input

    public static boolean healthPotion; // Health potion input
    public static boolean speedPotion; // Speed potion input
    public static boolean strengthPotion; // Damage potion input

    //Needed for attacks that will last a certain amount of time (Im going to guess this is probably bad)
    public static boolean currentlyAttackingOne;   // Whether the player is currently attacking
    // END OF TO USE

    /**
     * Initializes input class by starting thread, updates all variables for input constantly
     */
    private Input() {
        keyboard = GameEngine.keyboard;
        GameEngine.add(this);
    }

    @Override
    public void update(RenderWindow window) {
        super.update(window);
        userInput();
    }

    public static Input getInstance() {
        return instance;
    }

    /**
     * updates mouse input
     */
    private void updateMouseInput() {
        mousePos = mouse.getPosition(GameEngine.window);
        basicAttack = leftMouse = mouse.isButtonPressed(Mouse.Button.LEFT);
        altAttack = rightMouse = mouse.isButtonPressed(Mouse.Button.RIGHT);
    }

    /**
     * updates user input
     */
    public void userInput() {
        updateMouseInput();

        if (keyboard.isKeyPressed(Keyboard.Key.W))
            vertical = Vector2i.add(new Vector2i(0, -1), vertical);
        if (keyboard.isKeyPressed(Keyboard.Key.S))
            vertical = Vector2i.add(new Vector2i(0, 1), vertical);
        if (keyboard.isKeyPressed(Keyboard.Key.A))
            horizontal = Vector2i.add(new Vector2i(-1, 0), horizontal);
        if (keyboard.isKeyPressed(Keyboard.Key.D))
            horizontal = Vector2i.add(new Vector2i(1, 0), horizontal);

        if (keyboard.isKeyPressed(Keyboard.Key.F))
            specialAttack = true;
//        if(keyboard.isKeyPressed(Keyboard.Key.E))
//            interaction = true;
        if (keyboard.isKeyPressed(Keyboard.Key.NUM1))
            moveOne = true;
        if (keyboard.isKeyPressed(Keyboard.Key.NUM2))
            moveTwo = true;
        if (keyboard.isKeyPressed(Keyboard.Key.NUM3))
            healthPotion = true;
        if (keyboard.isKeyPressed(Keyboard.Key.NUM4))
            speedPotion = true;
        if (keyboard.isKeyPressed(Keyboard.Key.NUM5))
            strengthPotion = true;
        if (keyboard.isKeyPressed(Keyboard.Key.ESCAPE))
            pause = true;

        verticalInt = vertical.y;
        horizontalInt = horizontal.x;
    }

    /**
     * resets inputs
     */
    public void userInputReset() {
        vertical = new Vector2i(0, 0);
        horizontal = new Vector2i(0, 0);
        int verticalInt = 0;
        int horizontalInt = 0;

        pause = false;

        basicAttack = leftMouse = false;
        altAttack = rightMouse = false;
        specialAttack = false;
//        interaction = false;

        moveOne=false;
        moveTwo=false;
        healthPotion = false;
        speedPotion = false;
        strengthPotion = false;
    }

    public void setAttackingOne(boolean value){
        currentlyAttackingOne = value;
    }

}
