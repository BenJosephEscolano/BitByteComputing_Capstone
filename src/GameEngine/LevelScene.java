package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.Constants;
import Util.SceneCode;
import Util.Timer;
import Util.Vector;
import Component.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LevelScene extends Scene{
    private GameObject player1, player2, grid;
    private Timer resetLevel;



    public LevelScene(String name){
        super(name);
        this.resetLevel = new Timer(2.5f);
    }

    public GameObject getPlayer1(){
        return player1;
    }

    public GameObject getPlayer2(){
        return player2;
    }


    @Override
    public void init() {
        loadPlayerAssets();
        loadLevel(levels.getLevels().getFirst());
        System.out.println("Level Loaded: Layer 1 : " + getRenderer(1).getRenderList().size() + " Layer 2: " + getRenderer(2).getRenderList().size());
        player1 = Player.createPlayer(0,0,0);
        player1.setPosition(new Vector(300.0f, 350.0f));
        player1.addComponent(new PlayerOneControls());
        player2 = Player.createPlayer(2, 2, 2);
        player2.setPosition(new Vector(900.0f, 350.0f));
        player2.addComponent(new PlayerTwoControls());
        GameObject background = new GameObject("background", new Transform(new Vector()));
        background.addComponent(AssetPool.getSprite("assets/Background/background_yellow_1.png"));
        addToBackground(background);
        addGameObject(player1);
        addGameObject(player2);
    }

    private void respawn(){
        addGameObject(player1);
        addGameObject(player2);
        activeBodies.addToLayer(player1);
        activeBodies.addToLayer(player2);
        player1.setPosition(new Vector(500.0f, 350.0f));
        player1.getComponent(RigidBody.class).resetGravity();
        player2.setPosition(new Vector(700.0f, 350.0f));
        player2.getComponent(RigidBody.class).resetGravity();
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
        new Sprite("assets/Background/background_yellow.png");
        new Sprite("assets/Background/background_yellow_1.png");
    }

    @Override
    public void update(double dt) {
        resetLevel.addTime(dt);
        for (int i = 0; i < gameObjectList.size(); i++){
            gameObjectList.get(i).update(dt);
        }

        if (resetLevel.isTime(0)){
            //switchLevels(1);
            //respawn();
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
        background.render(g2);
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


