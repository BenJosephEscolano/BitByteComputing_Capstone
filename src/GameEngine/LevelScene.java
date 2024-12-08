package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.*;
import Component.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import Component.Button;
import java.util.concurrent.ThreadLocalRandom;

public class LevelScene extends Scene{
    private PlayerCharacter player1, player2;
    private GameObject background;
    private GameObject pauseScreen;
    private Timer resetLevel;
    private Gun gun1, gun2;
    private  int Player1Wins;
    private  int Player2Wins;
    private Timer keyboardBuffer;
    private boolean toggle = true;
    private GameObject exitButton, continueButton;
    private ML mouse = Window.getMouseListener();
    private KL key = Window.getKeyListener();
    private GameObject player1ScoreBoard, player2ScoreBoard;
    private int scoreBoardIndex1, scoreBoardIndex2;

    public LevelScene(String name, PlayerCharacter player1, PlayerCharacter player2, Gun gun1, Gun gun2){
        super(name);
        this.player1 = player1;
        this.player2 = player2;
        this.resetLevel = new Timer(2.5f);
        this.gun1 = gun1;
        this.gun2 = gun2;
        this.background = new GameObject("", new Transform(new Vector()));
        this.Player1Wins=0;
        this.Player2Wins=0;
        this.pauseScreen = new GameObject("", new Transform(new Vector()));
        this.keyboardBuffer = new Timer(0.5f);
        this.continueButton = new GameObject("", new Transform(new Vector(380,233)));
        this.exitButton = new GameObject("", new Transform(new Vector(320, 350)));
        this.player1ScoreBoard = new GameObject("", new Transform(new Vector(50,70)));
        this.player2ScoreBoard = new GameObject("", new Transform(new Vector(1050, 70)));
    }

    public PlayerCharacter getPlayer1(){
        return player1;
    }

    public PlayerCharacter getPlayer2(){
        return player2;
    }


    @Override
    public void init() {
        loadPlayerAssets();
        Sound.getInstance().loadMusic("assets/Music/music_in_game.wav");
        Sound.getInstance().playMusicLoop();
        loadLevel(levels.getLevels().get(0));
        player1.setPosition(currLevel.getSpawnPoint(1));
        gun1.setOwner(player1);
        player2.setPosition(currLevel.getSpawnPoint(2));
        player2.setWeapon(gun2);
        gun2.setOwner(player2);
        switchBackground();
        addToBackground(background);
        setActiveBodies(player1, 2);
        setActiveBodies(player2, 2);
        addToLayerTwo(gun2);
        addToLayerTwo(gun1);
        pauseScreen.addComponent(AssetPool.getSprite("assets/PauseScreen/screen_pause.png"));
        continueButton.addComponent(new Button(550, 100));
        exitButton.addComponent(new Button(550, 100));
        player1ScoreBoard.addComponent(getScoreBoard(1, player1.bulletIndex));
        player2ScoreBoard.addComponent(getScoreBoard(2, player2.bulletIndex));
    }

    private Component getScoreBoard(int player, int color){
        SpriteSheet scoreboard = AssetPool.getSpriteSheet("assets/CharacterSelection/GUI/gui_player_win_count.png");
        if (player == 1){
            if (color == 0){
                scoreBoardIndex1 = 1;
            }
            if (color == 1){
                scoreBoardIndex1 = 0;
            }
            if (color == 2){
                scoreBoardIndex1 = 3;
            }
            if (color == 3){
                scoreBoardIndex1 = 2;
            }
            return scoreboard.getSprite(scoreBoardIndex1).copy();
        } else {
            if (color == 0){
                scoreBoardIndex2 = 1 + 16;
            }
            if (color == 1){
                scoreBoardIndex2 = 0 + 16;
            }
            if (color == 2){
                scoreBoardIndex2 = 3 + 16;
            }
            if (color == 3){
                scoreBoardIndex2 = 2 + 16;
            }
            return scoreboard.getSprite(scoreBoardIndex2).copy();
        }
    }

    private void addPoint(int scoreBoard){
        if (scoreBoard == 1){
            scoreBoardIndex1 += 4;
            player1ScoreBoard.removeComponent(Sprite.class);
            player1ScoreBoard.addComponent(AssetPool.getSpriteSheet("assets/CharacterSelection/GUI/gui_player_win_count.png").getSprite(scoreBoardIndex1).copy());
        }
        if (scoreBoard == 2){
            scoreBoardIndex2 += 4;
            player2ScoreBoard.removeComponent(Sprite.class);
            player2ScoreBoard.addComponent(AssetPool.getSpriteSheet("assets/CharacterSelection/GUI/gui_player_win_count.png").getSprite(scoreBoardIndex2).copy());
        }
    }

    private void respawn(){
        setActiveBodies(player1, 2);
        setActiveBodies(player2, 2);
        player1.setPosition(currLevel.getSpawnPoint(1).copy());
        player1.getComponent(RigidBody.class).resetGravity();
        player2.setPosition(currLevel.getSpawnPoint(2).copy());
        player2.getComponent(RigidBody.class).resetGravity();
        gun1.resetAmmo();
        gun2.resetAmmo();
        addToLayerTwo(gun1);
        addToLayerTwo(gun2);
        //player1.revive();
        //player2.revive();
    }

    private void switchBackground(){
        if (background.getComponent(Sprite.class) != null){
            background.removeComponent(Sprite.class);
        }
        int upperBound=5;int lowerBound=1;int randomNumber = ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1);
        System.out.println(randomNumber);
        switch (randomNumber){
            case 1:
                background.addComponent(AssetPool.getSprite("assets/Background/background_yellow_1.png"));
                break;
            case 2:
                background.addComponent(AssetPool.getSprite("assets/Background/background_blue_1.png"));
                break;
            case 3:
                background.addComponent(AssetPool.getSprite("assets/Background/background_pink_1.png"));
                break;
            case 4:
                background.addComponent(AssetPool.getSprite("assets/Background/background_green_1.png"));
                break;
            case 5:
                background.addComponent(AssetPool.getSprite("assets/Background/background_blue_2.png"));
                break;
        }
    }

    private void switchLevels(int num){
        currLevelIndex = (getLevels().size() + currLevelIndex + num) % getLevels().size();
        currLevel = getLevels().get(currLevelIndex);
        loadLevel(currLevel);
        System.out.println(currLevel + " | " + currLevelIndex);
        respawn();
        player1.revive();
        player2.revive();
    }

    private void loadPlayerAssets(){
        if (!AssetPool.hasSpriteSheet("assets/Player/character_body.png")){
            new SpriteSheet("assets/Player/character_body.png",
                    Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/Player/character_eyes.png")){
            new SpriteSheet("assets/Player/character_eyes.png",
                    Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/Player/character_mouth.png")){
            new SpriteSheet("assets/Player/character_mouth.png",
                    Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/Bullet/bullets.png")){
            new SpriteSheet("assets/Bullet/bullets.png",
                     20, 20, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/CharacterSelection/GUI/gui_player_win_count.png")){
            new SpriteSheet("assets/CharacterSelection/GUI/gui_player_win_count.png",
                    150, 30, 30, 4, 4*8);
        }
    }

    @Override
    public void update(double dt) {
        for (int i = 0; i < gameObjectList.size(); i++){
            gameObjectList.get(i).update(dt);
        }
        Collision.isOutOfBounds(player1);
        Collision.isOutOfBounds(player2);
        for (int i = 0; i < getProjectileLayer().size(); i++){
            Collision.isOutOfBounds(getProjectileLayer().get(i));
        }

        if ((!player1.getAliveStatus() || !player2.getAliveStatus()) && resetLevel.isTime(dt) ){
            if(!player1.getAliveStatus()){
                Player2Wins++;
                if (Player2Wins < 4){
                    addPoint(2);
                }
            }if(!player2.getAliveStatus()){
                Player1Wins++;
                if (Player1Wins < 4){
                    addPoint(1);
                }
            }
            if(Player1Wins==4){
                Window.changeScene(SceneCode.WinScreen, 1, player1.bulletIndex);
            }
            if(Player2Wins==4){
                Window.changeScene(SceneCode.WinScreen, 2, player2.bulletIndex);
            }
            resetLevel.resetTime();
            switchBackground();
            switchLevels(1);
        }
        if (Window.getWindow().isPause() && key.isKeyPressed(KeyEvent.VK_ESCAPE) && toggle){
            Window.getWindow().play();
            Window.getScene().getRenderer(2).unsubmit(pauseScreen);
        }
        if (Window.getWindow().isPause() &&
                mouse.isMousePressed()
                && mouse.getX() > exitButton.getX()
                && mouse.getX() < exitButton.getX() + exitButton.getComponent(Button.class).getWidth()
                && mouse.getY() > exitButton.getY()
                && mouse.getY() < exitButton.getY()+ exitButton.getComponent(Button.class).getHeight()
        ){
            Window.getWindow().play();
            Sound.getInstance().stopMusic();
            Window.changeScene(SceneCode.SplashScreen);
        }
        if (Window.getWindow().isPause() &&
                mouse.isMousePressed()
                && mouse.getX() > continueButton.getX()
                && mouse.getX() < continueButton.getX() + continueButton.getComponent(Button.class).getWidth()
                && mouse.getY() > continueButton.getY()
                && mouse.getY() < continueButton.getY()+ continueButton.getComponent(Button.class).getHeight()
        ){
            Window.getWindow().play();
            Window.getScene().getRenderer(2).unsubmit(pauseScreen);
        }
        
        if (keyboardBuffer.isTime(dt) && Window.getKeyListener().isKeyPressed(KeyEvent.VK_ESCAPE)){
            keyboardBuffer.resetTime();
            Window.getWindow().pause();
            Window.getScene().addToLayerTwo(pauseScreen);
            toggle = false;
        }
        if (!Window.getKeyListener().isKeyPressed(KeyEvent.VK_ESCAPE)){
            toggle = true;
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        backgroundRender.render(g2);
        layer1.render(g2);
        layer2.render(g2);
        if (Window.getWindow().isPause()){
            exitButton.draw(g2);
            continueButton.draw(g2);
        }
        player1ScoreBoard.draw(g2);
        player2ScoreBoard.draw(g2);
    }

    public List<GameObject> getPlayers(){
        List<GameObject> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        return players;
    }

}


