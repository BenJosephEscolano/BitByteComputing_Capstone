package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.Constants;
import Util.Vector;
import Component.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.List;

public class LevelScene extends Scene {
    private GameObject player1, player2;

    public LevelScene(String name) {
        super(name);
    }

    public GameObject getPlayer1() {
        return player1;
    }

    public GameObject getPlayer2() {
        return player2;
    }

    @Override
    public void init() {
        loadPlayerAssets();
        player1 = Player.createPlayer(42,42,42, Color.CYAN, Color.BLUE);
        player1.setPosition(new Vector(500.0f, 350.0f));
        player1.addComponent(new PlayerOneControls());
        player2 = Player.createPlayer(30, 30, 30, Color.ORANGE, Color.YELLOW);
        player2.setPosition(new Vector(700.0f, 350.0f));
        player2.addComponent(new PlayerTwoControls());
        GameObject ground = new GameObject("ground", new Transform(new Vector(90, Constants.GROUND_Y)));
        ground.addComponent(new Ground(ground.getPosition()));
        GameObject grid = new GameObject("grid", new Transform(new Vector()));
        grid.addComponent(new Grid());
        //addGameObject(grid);
        addGameObject(player1);
        addGameObject(player2);
        addGameObject(ground);

        // platforms//joseph
        createPlatform(300, 400, 200, 20, Color.DARK_GRAY);
        createPlatform(600, 300, 150, 20, Color.DARK_GRAY);
        createPlatform(100, 500, 100, 20, Color.DARK_GRAY);


    }

    private void loadPlayerAssets() {
        if (!AssetPool.hasSpriteSheet("assets/player/layerOne.png")) {
            new SpriteSheet("assets/player/layerOne.png",
                    42, 42, 2, 13, 13 * 5);
        }
        if (!AssetPool.hasSpriteSheet("assets/player/layerTwo.png")) {
            new SpriteSheet("assets/player/layerTwo.png",
                    42, 42, 2, 13, 13 * 5);
        }
        if (!AssetPool.hasSpriteSheet("assets/player/layerThree.png")) {
            new SpriteSheet("assets/player/layerThree.png",
                    42, 42, 2, 13, 13 * 5);
        }
    }
    //crete platforms//joseph
    private void createPlatform(float x, float y, int width, int height, Color color) {
        GameObject platform = new GameObject(
                "platform",
                new Transform(new Vector(x, y))
        );
        Platform platformComponent = new Platform(platform.getPosition(), width, height);
        platformComponent.setColor(color);
        platform.addComponent(platformComponent);
        addGameObject(platform);
    }

    @Override
    public void update(double dt) {
        for (GameObject g: gameObjectList){
            g.update(dt);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        renderer.render(g2);
    }


}