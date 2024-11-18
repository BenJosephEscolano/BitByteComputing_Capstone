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
    private GameObject player1, player2, player3;

    public LevelScene(String name){
        super(name);
    }

    public GameObject getPlayer1(){
        return player1;
    }

    public GameObject getPlayer2(){
        return player2;
    }
    public GameObject getPlayer3(){
        return player3;
    }

    @Override
    public void init() {
        player1 = new GameObject("Some game object", new Transform(new Vector(500.0f, 350.0f)));
        SpriteSheet layerOne, layerTwo, layerThree;
        if (!AssetPool.hasSpriteSheet("assets/player/layerOne.png")){
            layerOne = new SpriteSheet("assets/player/layerOne.png",
                    42, 42, 2, 13, 13 * 5);
        } else {
            layerOne = AssetPool.getSpriteSheet("assets/player/layerOne.png");
        }
        if (!AssetPool.hasSpriteSheet("assets/player/layerTwo.png")){
            layerTwo = new SpriteSheet("assets/player/layerTwo.png",
                    42, 42, 2, 13, 13 * 5);
        } else {
            layerTwo = AssetPool.getSpriteSheet("assets/player/layerTwo.png");
        }
        if (!AssetPool.hasSpriteSheet("assets/player/layerThree.png")){
            layerThree = new SpriteSheet("assets/player/layerThree.png",
                    42, 42, 2, 13, 13 * 5);
        } else {
            layerThree = AssetPool.getSpriteSheet("assets/player/layerThree.png");
        }
        layerOne = AssetPool.getSpriteSheet("assets/player/layerOne.png");
        layerTwo = AssetPool.getSpriteSheet("assets/player/layerTwo.png");
        layerThree = AssetPool.getSpriteSheet("assets/player/layerThree.png");
        int spriteIndex = 42;
        Player playerComp = new Player(
                layerOne.getSprite(spriteIndex),
                layerTwo.getSprite(spriteIndex),
                layerThree.getSprite(spriteIndex),
                Color.CYAN,
                Color.BLUE);
        player1.addComponent(playerComp);
        player1.addComponent(new RigidBody( new Vector()));
        player1.addComponent(new PlayerOneControls());
        player1.addComponent(new BoxBounds(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT));
        int spriteIndex2 = 30;
        player2 = new GameObject("Player2", new Transform(new Vector(700.0f, 350.0f)));
        Player playerComp2 = new Player(
                layerOne.getSprite(spriteIndex2),
                layerTwo.getSprite(spriteIndex2),
                layerThree.getSprite(spriteIndex2),
                Color.BLACK,
                Color.WHITE);
        player2.addComponent(playerComp2);
        player2.addComponent(new RigidBody(new Vector()));
        player2.addComponent(new BoxBounds(42, 42));
        player2.addComponent(new PlayerTwoControls());


        int spriteIndex3 = 50;
        player3 = new GameObject("Player3", new Transform(new Vector(800.0f, 350.0f)));
        Player playerComp3 = new Player(
                layerOne.getSprite(spriteIndex3),
                layerTwo.getSprite(spriteIndex3),
                layerThree.getSprite(spriteIndex3),
                Color.BLACK,
                Color.WHITE);
        player3.addComponent(playerComp3);
        player3.addComponent(new RigidBody(new Vector()));
        player3.addComponent(new BoxBounds(40, 40));
        player3.addComponent(new PlayerTwoControls());





        GameObject ground = new GameObject("Ground", new Transform(new Vector(100, Constants.GROUND_Y)));
        ground.addComponent(new Ground());
        GameObject grid = new GameObject("gird", new Transform(new Vector()));
        grid.addComponent(new Grid());

        addGameObject(grid);
        addGameObject(player1);
        addGameObject(player2);
        addGameObject(player3);
        addGameObject(ground);

    }

    @Override
    public void update(double dt) {
        float newX = 0;
        float newY = 0;
        if (player1.getX() - camera.getX() > Constants.CAMERA_OFFSET_X){
            newX = player1.getX() - Constants.CAMERA_OFFSET_X;
            //camera.setX(newX);
        }
        if (player1.getY() - camera.getY() > Constants.CAMERA_OFFSET_Y){
            newY = player1.getY() - Constants.CAMERA_OFFSET_Y;
            //camera.setY(newY);
        }
        if (camera.getY() > Constants.CAMERA_GROUND_OFFSET){
            newY = Constants.CAMERA_GROUND_OFFSET;
            //camera.setY(newY);
        }

        for (GameObject g: gameObjectList){
            g.update(dt);
        }
        //System.out.println("X: " + player.getX() + ", Y: " + player.getY() );
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        renderer.render(g2);
    }
}


