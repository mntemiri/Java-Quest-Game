package com.wgd.ecg.engine;

import org.jsfml.audio.Music;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Audio Class
 * This will be used to play sound effects and music from the audio directory
 */
public class Audio {
    private static String AUDIO_TEXT = "\u001b[35m";
    private static String RESET_TEXT = "\u001b[0m";
    private static Audio audio = new Audio();
    private static boolean Mute = true;
    private static int Master_Volume;
    private static int SFX_Volume;
    private static Music BGM;
    private static Sound SFX;
    private static int BGM_Volume;
    private static int currentTrack = 0;
    private static ArrayList<Music> Music_Queue = new ArrayList<>();
    private static boolean Music_Status = true;
    private static ArrayList<String> SFX_Buffer = new ArrayList<>();

    /**
     * Audio Constructor
     */
    private Audio() {
        Master_Volume = 50;
        SFX_Volume = 50;
        BGM_Volume = 50;
    }

    /**
     * Takes a filename (string) and changes the file inside the music directory into playable music
     * @param fileName the name of the file
     * @return playable Music of that file
     */
    private static Music stringToMusic(String fileName) {
        try {
            Music temp = new Music();
            if(!fileName.contains(".ogg"))
                temp.openFromFile(Paths.get("src/com/wgd/ecg/res/audio/music/" + fileName + ".ogg"));
            else
                temp.openFromFile(Paths.get("src/com/wgd/ecg/res/audio/music/" + fileName));
            return temp;
        } catch (Exception e) {
            print("Cant Find Audio File \"" + fileName + "\" check the name is correct and the file type is \".ogg\"" );
            return null;
        }
    }

    /**
     * Checks if music is currently playing
     * @return True if music is playing, otherwise False
     */
    private static boolean isBGMPlaying() {
        return BGM.getStatus().toString().equals("PLAYING");
    }

    /**
     * This method is used inside the frame to track and update any changes made to audio
     */
    protected static void audioUpdate() {
        updateVolume();
        if(!Mute) {
            if (BGM == null & Music_Queue.isEmpty()) { //if there is no music playing or Queued
                if (Music_Status) { //used to stop the messaged being printed with each loop
                    print("-No Music Being Played or Queued-");
                    Music_Status = false;
                }
            } else {
                Music_Status = true;
                if (BGM == null & !Music_Queue.isEmpty()) { //if no music has been assigned to BGM, but the queue isnt empty
                    BGM = Music_Queue.get(currentTrack);
                    BGM.play();
                }
                if (!isBGMPlaying() & !Music_Queue.isEmpty()) { //if nothing is playing
                    nextTrack();
                }
            }
            if (!SFX_Buffer.isEmpty()) {
                playSFXBuffer();
            }
            if (SFX != null) {
                SFX.setVolume(SFX_Volume);
            }
            clearSFXBuffer(); //clears the buffer
        }
        else {

        if(BGM != null)
            BGM.stop();
        }
    }


    /**
     * Plays the SFX by adding it to the SFX_Buffer (KEEP ALL FILE TYPES TO .ogg)
     * @param fileName The name of the SFX file you want to play
     */
    public static void playSFX(String fileName) {
        if(!Mute)
            SFX_Buffer.add((fileName));
    }

    /**
     * Plays the Music with given filename (KEEP ALL FILE TYPES TO .ogg)
     * @param fileName The name of the Song file you want to play
     */
    public static void playBGM(String fileName ) {
        BGM = stringToMusic(fileName);
        updateVolume();
        if(!Mute)
            BGM.play();
    }

    /**
     * Adds Music to the BGM_Queue (Used to make playlists for levels etc.) (KEEP ALL FILE TYPES TO .ogg) (Example Audio.queueBGM("song1", "song2", "song3"...)
     * @param songs The name of the Song file(s) you want to queue
     */
    public static void queueBGM(String ... songs){
        for (String song:songs) {
            Music track = stringToMusic(song);
            if(track != null)
                Music_Queue.add(track);
        }

    }
    /**
     * Clears the BGM_Queue
     */
    private static void clearBGMQueue(){
        Music_Queue.clear();
    }

    /**
     * Sets the volume of BGM
     * @param volume the new volume
     */
    public static void setBGM_Volume(int volume) {
        print("BGM_Volume_Accessed");
        if(volume > 100) volume = 100;
        BGM_Volume = volume;
    }

    /**
     * Sets the volume of SFX
     * @param volume the new volume
     */
    public static void setSFX_Volume(int volume) {
        print("SFX_Volume_Accessed");
        if(volume > 100) volume = 100;
        SFX_Volume = volume;
    }

    /**
     * Setss the master volume, this alters the SFX adn BGM volume by the same proportion
     * @param volume the new volume
     */
    public static void setMasterVolume(int volume) {
        print("Master_Volume_Accessed");
        if(volume < 0) volume = 0;
        if(volume > 100) volume = 100;
        Master_Volume = volume;
        setSFX_Volume(Master_Volume);
        setBGM_Volume(Master_Volume);
        printVolume();
        print(Master_Volume);
    }

    /**
     * Returns an Instance of Audio
     * @return instance of Audio
     */
    public static Audio getInstance() {
        return audio;
    }

    /**
     * Changes the BGM to the next track, if the end of the queue has been reached restart then start again
     */
    private static void nextTrack(){
        currentTrack++;
        if(currentTrack >= Music_Queue.size())
            currentTrack = 0;
        BGM = Music_Queue.get(currentTrack);
        BGM.play();
    }

    /**
     * Takes a fileName (string) and returns playable Sound
     * @param fileName
     * @return
     */
    private static Sound stringToSound(String fileName) {
        SoundBuffer soundBuffer = new SoundBuffer();
        try {
            if(!fileName.contains(".ogg")) //if .ogg file extension hasn't been added it will be added here
                soundBuffer.loadFromFile(Paths.get("src/com/wgd/ecg/res/audio/sfx/" + fileName + ".ogg"));
            else
                soundBuffer.loadFromFile(Paths.get("src/com/wgd/ecg/res/audio/sfx/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            print( "\"" + fileName + "\"" + ": Invalid File Name");
        }
        return new Sound(soundBuffer);
    }

    /**
     * plays all the SFX in the SFX buffer
     */
    private static void playSFXBuffer(){
        for (String sound: SFX_Buffer) {
            SFX = stringToSound(sound);
            SFX.play();
        }
    }

    /**
     * clears the SFX Buffer
     */
    private static void clearSFXBuffer(){
        SFX_Buffer.clear();
    }

    /**
     * Clears the BGM Queue and stops the music
     */
    public static void resetBGM(){
        clearBGMQueue();
        if(BGM != null)
            BGM.stop();
        BGM = null;
    }

    /**
     * Toggles audio mute on/off
     */
    public static void toggleMute(){
        Mute = Mute ? false : true;
        print("Audio_Mute: " + Mute);
    }

    /**
     * Sets volumes if BGM and SFX once they play (inside update loop)
     */
    private static void updateVolume(){
        if(BGM != null)
            BGM.setVolume(BGM_Volume);
        if(SFX != null)
            SFX.setVolume(SFX_Volume);
    }

    /**
     * Prints the current Master, BGM and SFX volumes to terminal
     */
    public static void printVolume(){
        print("Master_Volume: " + Master_Volume);
        print("BGM_Volume: " + BGM_Volume);
        print("SFX_Volume: " + SFX_Volume);
    }

    /**
     * @return the state of mute
     */
    public static boolean isMute(){
        return Mute;
    }

    /**
     * @return the current master volume
     */
    public static int getMaster_Volume() {
        return Master_Volume;
    }

    /**
     * @return the current sfx_volume
     */
    public static int getSFX_Volume() {
        return SFX_Volume;
    }

    /**
     * @return the current bgm_volume
     */
    public static int getBGM_Volume() {
        return BGM_Volume;
    }

    /**
     * Just something for james to use as he hates standard print statements
     * @param string what to print
     * @param <T> anything that can be used with .toString
     */
    private static <T> void print(T string){
        System.out.println(AUDIO_TEXT + string.toString() + RESET_TEXT);
    }

    public static void playRandomSwoosh(){
        int numSounds = 5;
        int num = new Random().nextInt(numSounds);
        playSFX("swoosh" + num);
    }
}

