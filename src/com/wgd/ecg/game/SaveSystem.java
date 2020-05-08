package com.wgd.ecg.game;

import com.wgd.ecg.game.gameobjects.entities.heroes.Player;

import java.io.*;

public class SaveSystem {
    public static void savePlayer(Player player) {
        try {
            File yourFile = new File("saves/player.save");
            yourFile.createNewFile();
            FileOutputStream saveFile = new FileOutputStream("saves/player.save");
            ObjectOutputStream out = new ObjectOutputStream(saveFile);
            out.writeObject(player);
            out.close();
            saveFile.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Player loadPlayer() {
        Player player = null;
        try {
            FileInputStream fileIn = new FileInputStream("/saves/player.save");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            player = (Player) in.readObject();
            in.close();
            fileIn.close();
            return player;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
    }
}
