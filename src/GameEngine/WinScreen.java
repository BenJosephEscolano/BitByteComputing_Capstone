package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.SceneCode;
import Util.Timer;
import Util.Vector;
import Component.Sprite;

import java.awt.*;
import java.lang.management.MemoryUsage;

public class WinScreen extends Scene{
    private GameObject winScreen;
    private Timer continueBuffer;

    public WinScreen(String name, int player, int color) {
        super(name);
        winScreen = new GameObject("", new Transform(new Vector()));
        continueBuffer = new Timer(7);
        Sprite screen = getWinScreen(player, color);
        winScreen.addComponent(screen);
    }

    @Override
    public void init() {
        Sound.getInstance().closeMusic();
        Sound.getInstance().loadMusic("assets/Music/winner_sfx.wav");
        Sound.getInstance().playMusic();
    }

    public Sprite getWinScreen(int player, int color){
        if (color == 0){
            if (player == 1){
                return AssetPool.getSprite("assets/WinnerScreen/screen_winner_player1_green.png");
            }
            if (player == 2){
                return AssetPool.getSprite("assets/WinnerScreen/screen_winner_player2_green.png");
            }
        }
        if (color == 1){
            if (player == 1){
                return AssetPool.getSprite("assets/WinnerScreen/screen_winner_player1_yellow.png");
            }
            if (player == 2){
                return AssetPool.getSprite("assets/WinnerScreen/screen_winner_player2_yellow.png");
            }
        }
        if (color == 2){
            if (player == 1){
                return AssetPool.getSprite("assets/WinnerScreen/screen_winner_player1_pink.png");
            }
            if (player == 2){
                return AssetPool.getSprite("assets/WinnerScreen/screen_winner_player2_pink.png");
            }
        }
        if (color == 3){
            if (player == 1){
                return AssetPool.getSprite("assets/WinnerScreen/screen_winner_player1_blue.png");
            }
            if (player == 2){
                return AssetPool.getSprite("assets/WinnerScreen/screen_winner_player2_blue.png");
            }
        }
        return null;
    }

    @Override
    public void update(double dt) {
        if (continueBuffer.isTime(dt)){
            Sound.getInstance().closeMusic();
            Window.changeScene(SceneCode.SplashScreen);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        winScreen.draw(g2);
    }

    @Override
    public PlayerCharacter getPlayer1() {
        return null;
    }

    @Override
    public PlayerCharacter getPlayer2() {
        return null;
    }
}
