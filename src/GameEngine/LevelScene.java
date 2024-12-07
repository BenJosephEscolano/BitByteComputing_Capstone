package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.*;
import Component.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LevelScene extends Scene{
    private PlayerCharacter player1, player2;
    private GameObject background;
    private Timer resetLevel;
    private Gun gun1, gun2;
    private  int Player1Wins;
    private  int Player2Wins;

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
    }

    private void respawn(){
        setActiveBodies(player1, 2);
        setActiveBodies(player2, 2);
        player1.revive();
        player2.revive();
        player1.setPosition(currLevel.getSpawnPoint(1).copy());
        player1.getComponent(RigidBody.class).resetGravity();
        player2.setPosition(currLevel.getSpawnPoint(2).copy());
        player2.getComponent(RigidBody.class).resetGravity();
        addToLayerTwo(gun1);
        addToLayerTwo(gun2);
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
                System.out.println("player 2 wins: "+Player2Wins);
            }if(!player2.getAliveStatus()){
                Player1Wins++;
                System.out.println("player 1 wins: " + Player1Wins);
            }
            if(Player1Wins==3){
                System.out.println("player 1 is winner");
                Window.getWindow().close();
            }
            if(Player2Wins==3){
                System.out.println("player 2 is winner");
                Window.getWindow().close();
            }
            resetLevel.resetTime();
            switchBackground();
            switchLevels(-1);
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        backgroundRender.render(g2);
        layer1.render(g2);
        layer2.render(g2);
    }

    public List<GameObject> getPlayers(){
        List<GameObject> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        return players;
    }

}


