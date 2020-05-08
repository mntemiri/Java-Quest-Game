package com.wgd.ecg.engine;

public class GameClock {
    /**
     * This class creates a game clock
     */
    private static GameClock instance = new GameClock();    //game clock instance
    private double clock;   //te clock

    private GameClock() {
        clock = 0;              //set to zero when created
    }

    /**
     * This method returns instance of the class
     * @return instance of the class
     */
    public static GameClock getInstance() {
        return instance;
    }

    /**
     * this method updates the time
     */
    public void updateTime() {
        clock += GameEngine.deltaTime;
    }


    /**
     * this method updates the clock
     */
    private double getClock() {
        return clock;
    }

    /**
     * this method returns the time in sec
     */
    public static double timeInSEC() {
        return GameClock.getInstance().clock;
    }

    /**
     * this method returns the time in millisec
     */
    public static double timeInMS() {
        return GameClock.getInstance().clock * 1000f;
    }
}
