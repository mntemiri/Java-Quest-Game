package com.wgd.ecg;

import com.wgd.ecg.engine.GameEngine;
import com.wgd.ecg.engine.GameStateManager;

public class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));

        if(System.getProperty("os.name").equals("Linux"))//will check if the OS is linux and apply a fix to prevent XThreads issue
            System.load(System.getProperty("user.dir") + "/libs/fixThreads.so");

        GameEngine engine = GameEngine.getInstance();

        engine.loadLevel(GameStateManager.MENUSTATE);
        engine.run();
    }
}