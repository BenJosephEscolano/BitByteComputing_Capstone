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
    private double spawnCooldown = 0.25;  // Time in seconds between bullet spawns (300 ms)
    private double lastSpawnTime = 0;

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
        player2 = Player.createPlayer(2, 2, 2);
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

        lastSpawnTime += dt;

        if (Window.getWindow().getKeyListener().isKeyPressed(KeyEvent.VK_SPACE) && lastSpawnTime >= spawnCooldown) {
            spawnBullet();  //spawn the bullet
            lastSpawnTime = 0;  //reset the cooldown timer
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        layer1.render(g2);
        layer2.render(g2);
    }

    private void spawnBullet() {
        Vector spawnPosition = player1.getPosition().copy();

        //get the last direction from the controls
        Vector lastDirection = player1.getComponent(PlayerOneControls.class).getLastDirection();

        // if the last direction is non-zero, spawn a bullet
        if (lastDirection.getX() != 0 || lastDirection.getY() != 0) {
            Vector bulletVelocity = new Vector(lastDirection.getX() * 1000.0f, lastDirection.getY() * 1000.0f);
            Bullet newBullet = new Bullet(spawnPosition, bulletVelocity);
            addGameObject(newBullet);  //add bullet to the scene
        }
    }


}


