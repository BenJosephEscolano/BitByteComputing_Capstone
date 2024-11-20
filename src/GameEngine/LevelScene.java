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

public class LevelScene extends Scene{
    private GameObject player1, player2;

    public LevelScene(String name){
        super(name);
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
        player1 = Player.createPlayer(0,0,0);
        player1.setPosition(new Vector(500.0f, 350.0f));
        player1.addComponent(new PlayerOneControls());
        player2 = Player.createPlayer(3, 3, 3);
        player2.setPosition(new Vector(700.0f, 350.0f));
        player2.addComponent(new PlayerTwoControls());
        GameObject ground = new GameObject("ground", new Transform(new Vector(90, Constants.GROUND_Y)));
        ground.addComponent(new Ground(ground.getPosition()));
        GameObject grid = new GameObject("grid", new Transform(new Vector()));
        grid.addComponent(new Grid());
        addGameObject(grid);
        addGameObject(player1);
        addGameObject(player2);
        addGameObject(ground);
    }

    private void loadPlayerAssets(){
        /*if (!AssetPool.hasSpriteSheet("assets/player/layerOne.png")){
            new SpriteSheet("assets/player/layerOne.png",
                    42, 42, 2, 13, 13 * 5);
        }
        if (!AssetPool.hasSpriteSheet("assets/player/layerTwo.png")){
            new SpriteSheet("assets/player/layerTwo.png",
                    42, 42, 2, 13, 13 * 5);
        }
        if (!AssetPool.hasSpriteSheet("assets/player/layerThree.png")){
            new SpriteSheet("assets/player/layerThree.png",
                    42, 42, 2, 13, 13 * 5);
        }*/
        if (!AssetPool.hasSpriteSheet("assets/character_body.png")){
            new SpriteSheet("assets/character_body.png",
                    60, 60, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/character_eyes.png")){
            new SpriteSheet("assets/character_eyes.png",
                    60, 60, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/character_mouth.png")){
            new SpriteSheet("assets/character_mouth.png",
                    60, 60, 0, 4, 4);
        }

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
        layer1.render(g2);
        layer2.render(g2);
    }
}


