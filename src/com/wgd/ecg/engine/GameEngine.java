package com.wgd.ecg.engine;

import com.wgd.ecg.engine.UI.GameUIText;
import com.wgd.ecg.game.gameobjects.entities.heroes.Player;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * GameEngine class a singleton, instances itself. Can access variables due to them being static.
 */
public class GameEngine {
    private static final GameEngine instance = new GameEngine(1280, 720); // SINGLETON INSTANCE
    private Clock clock = new Clock();
    private View defaultView;

    public static int WIDTH, HEIGHT;

    public static Keyboard keyboard;
    public static View view;
    public static GameStateManager gameStateManager;
    public static RenderWindow window;

    private List<GameObject> entities = new CopyOnWriteArrayList();

    public static float lastTime; // Used for fps
    public static float deltaTime; // time it takes to update a frame
    public static float fps;
    public static float fixedDeltaTime;
    public static float realDeltaTime;

    private static float timeScale;

    public static int fpsLimit;

    public static FloatRect boundingBox;

    private boolean entitiesChange;
    /**
     * singleton instance
     * @param WIDTH
     * @param HEIGHT
     */
    private GameEngine(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;

        fpsLimit = 60;

        gameStateManager = new GameStateManager();
        window = new RenderWindow(new VideoMode(WIDTH, HEIGHT), "Java Quest");
        defaultView = (View) window.getDefaultView();
        view = new View(defaultView.getCenter(), defaultView.getSize());
        window.setView(view);
        window.setFramerateLimit(fpsLimit);

        entitiesChange = true;

        fixedDeltaTime = 1 / 100f;
        timeScale = 1f;
    }

    /**
     * Gets game engine instance
     * @return GameEngine
     */
    public static GameEngine getInstance () {
        return instance;
    }

    /**
     * Adds Base class entities to list, to be drawn in game
     * @param gameObject the game object to add to the renderer
     */
    public static void add(GameObject gameObject) {
        GameEngine.getInstance().entities.add(gameObject);

        GameEngine.getInstance().entitiesChange = true;
    }

    /**
     * Removes Base class entity from list
      */
    public static void remove(GameObject gameObject) {
        GameEngine.getInstance().entities.remove(gameObject);

        GameEngine.getInstance().entitiesChange = true;
    }

    /**
     * Removes Base class entity from list
      */
    public static void remove(UUID ID) {
        for (GameObject entity : GameEngine.getInstance().entities)
            if (entity.ID == ID) {
                GameEngine.getInstance().entities.remove(entity);
                break;
            }

        GameEngine.getInstance().entitiesChange = true;
    }

    public static List<GameObject> getDrawList() {
        return GameEngine.getInstance().entities;
    }

    /**
     * Clears entities draw list
     */
    public static void clear() {
        for (GameObject GO : GameEngine.getInstance().entities)
            if (GO.removeOnClear)
                GameEngine.remove(GO);
    }

    /**
     * Automatically sorts entities by ZDepth
     */
    private void sortEntitiesInOrder() {
        if (entitiesChange) {
            Collections.sort(entities, new Comparator<GameObject>() {
                public int compare(GameObject c1, GameObject c2) {
                    if (c1.ZDepth > c2.ZDepth) return 1;
                    if (c1.ZDepth < c2.ZDepth) return -1;
                    return 0;
                }
            });
            entitiesChange = false;
        }
    }

    /**
     * Changes level in game
     * Can change by using for example:
     * loadLevel(GameStateManager.MENU);
     * @param level the level to change to.
     */
    public static void loadLevel(int level) {
        gameStateManager.changeGameWorld(level);
        gameStateManager.loadWorld();
        gameStateManager.levelLoaded = true;
    }

    public static Vector2f getMousePosition(){
        Vector2i mousePos = Mouse.getPosition(window);
        Vector2f worldPos = GameEngine.window.mapPixelToCoords(mousePos, view);

        return worldPos;
    }

    public static <t> void removeClassObject(Class<t> className) { //James use GameEngine.removeClassObject("className".class)
        for (GameObject entity : GameEngine.getDrawList()) { // For each GameObject in scene
            if (entity.getClass().getName().equals(className.getName())){
                GameEngine.getInstance().entities.remove(entity);
            }
        }
        GameEngine.getInstance().entitiesChange = true;

    }

    public static <t> void removeClassExceptObject(Class<t> className) { //James use GameEngine.removeClassExceptObject("className".class)
        int i = 0;
        for (GameObject entity : GameEngine.getDrawList()) { // For each GameObject in scene
            if (!entity.getClass().getName().equals(className.getName())){
                GameEngine.getInstance().entities.remove(entity);
            }
        }
        GameEngine.getInstance().entitiesChange = true;
    }

    public static <t> boolean containsInstanceOf(Class<t> className) { //James use GameEngine.containsInstanceOf("className".class)
        int i = 0;
        for (GameObject entity : GameEngine.getDrawList()) { // For each GameObject in scene
            if (entity.getClass().getName().equals(className.getName())){
                return true;
            }
        }
        return false;
    }

    public static float getTimeScale() {
        return timeScale;
    }

    public static void setTimeScale(float timeScale) {
        GameEngine.timeScale = timeScale;
    }

    /**
     * Run game engine
     */
    public void run()
    {
        while (window.isOpen()) {
            window.clear();
            //BEGINNING OF FRAME//

            boundingBox = new FloatRect(view.getCenter().x - GameEngine.WIDTH / 2, view.getCenter().y  - GameEngine.HEIGHT / 2, GameEngine.WIDTH, GameEngine.HEIGHT);

            Input.getInstance().userInput();

            Audio.audioUpdate();

            for (Event event : window.pollEvents()) // Polls all window events
                if (event.type == event.type.CLOSED) window.close();

            window.setView(view); // Updates view (essentially camera) every frame

            sortEntitiesInOrder();
            for (GameObject gameObject : entities) // draws all Base class based elements in game
                gameObject.update(window);

            Input.getInstance().userInputReset();

            // updates deltatime every frame
            if(clock.getElapsedTime().asSeconds() > 0.25) {
                realDeltaTime = 0.25f;
                clock.restart().asSeconds();
            } else {
                realDeltaTime = clock.restart().asSeconds();
            }

            deltaTime = realDeltaTime * timeScale;
            fps = 1.0f / lastTime;
            lastTime = deltaTime;

            GameClock.getInstance().updateTime();

            //END OF FRAME//
            window.display();
        }
    }
}