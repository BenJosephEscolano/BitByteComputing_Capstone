package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.*;
import Component.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LevelScene extends Scene{
    private PlayerCharacter player1, player2, grid;
    private GameObject background;
    private Timer resetLevel;
    private Gun test, test2;
    private  int Player1Wins;
    private  int Player2Wins;

    public LevelScene(String name){
        super(name);
        this.resetLevel = new Timer(5.0f);
        this.test = Gun.createGun(GunCode.Pistol);
        this.test2 = Gun.createGun(GunCode.Rifle);
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
        System.out.println("Level Loaded: Layer 1 : " + getRenderer(1).getRenderList().size() + " Layer 2: " + getRenderer(2).getRenderList().size());
        player1 = PlayerCharacter.createPlayer(0,0,0);
        PlayerOneControls controller1 = new PlayerOneControls(player1);
        player1.addComponent(controller1);
        player1.setPosition(currLevel.getSpawnPoint(1));
        //test.addComponent(new Item(player1));
        player1.setWeapon(test);
        test.setOwner(player1);
        player2 = PlayerCharacter.createPlayer(2, 2, 2);
        PlayerTwoControls controller2 = new PlayerTwoControls(player2);
        player2.addComponent(controller2);
        player2.setPosition(currLevel.getSpawnPoint(2));
        //test2.addComponent(new Item(player2));
        player2.setWeapon(test2);
        test2.setOwner(player2);
        switchBackground();
        addToBackground(background);
        addGameObject(player1);
        addGameObject(player2);
        addToLayerTwo(test2);
        addToLayerTwo(test);
        //addToBackground(background);
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
        addToLayerTwo(test);
        addToLayerTwo(test2);
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
                break;}
    }

    private void switchLevels(int num){
        currLevelIndex = (getLevels().size() + currLevelIndex + num) % getLevels().size();
        currLevel = getLevels().get(currLevelIndex);
        loadLevel(currLevel);
        System.out.println(currLevel + " | " + currLevelIndex);
        respawn();
    }

    private void loadPlayerAssets(){
        System.out.println("Loading Platforms: " + AssetPool.hasSpriteSheet("assets/Tiles/platform_tiles.png"));
        System.out.println("Loading Shadows: " + AssetPool.hasSpriteSheet("assets/Tiles/platform_tiles_shadow.png"));
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
        resetLevel.addTime(dt);
        for (int i = 0; i < gameObjectList.size(); i++){
            gameObjectList.get(i).update(dt);
        }
        if(Player1Wins==3){
            System.out.println("player 1 is winner");
            Window.getWindow().close();
        }
        if(Player2Wins==3){
            System.out.println("player 2 is winner");
            Window.getWindow().close();
        }


        Collision.isOutOfBounds(player1);
        Collision.isOutOfBounds(player2);

        for (int i = 0; i < getProjectileLayer().size(); i++){
            Collision.isOutOfBounds(getProjectileLayer().get(i));
        }

        if (resetLevel.isTime(0) && (!player1.getAliveStatus() || !player2.getAliveStatus())){
            switchBackground();
            switchLevels(-1);
            if(!player1.getAliveStatus()){
                Player2Wins++;
                System.out.println("player 2 wins: "+Player2Wins);
            }if(!player2.getAliveStatus()){
                Player1Wins++;
                System.out.println("player 1 wins: " + Player1Wins);
            }
        }

        if (Window.getKeyListener().isKeyPressed(KeyEvent.VK_F9)){
            System.out.println("Changing scene to level editor");
            Window.changeScene(SceneCode.LevelEditor);
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


