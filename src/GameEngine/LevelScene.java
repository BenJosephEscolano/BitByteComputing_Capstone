package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.Constants;
import Util.Vector;
import Component.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LevelScene extends Scene{
    private GameObject player1, player2, grid;
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
        File lvl1 = new File("level_layer1.obj");
        File shd1 = new File("level_layer2.obj");
        loadLevel(lvl1,shd1);
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
        //GameObject ground = new GameObject("ground", new Transform(new Vector(90, Constants.GROUND_Y)));
        //ground.addComponent(new Ground(ground.getPosition()));
        grid = new GameObject("grid", new Transform(new Vector()));
        grid.addComponent(new Grid());
        //addGameObject(grid);
        addGameObject(player1);
        addGameObject(player2);
        collisionLayer.addToLayer(player1);
        collisionLayer.addToLayer(player2);
        //addGameObject(ground);
    }

    private void respawn(){
        if (!player1IsAlive){
            Window.getWindow().getScene().getRenderer(1).submit(player1);
            player1IsAlive = true;
        }
        if (!player2IsAlive){
            Window.getWindow().getScene().getRenderer(1).submit(player2);
            player2IsAlive = true;
        }
        player1.setPosition(new Vector(500.0f, 350.0f));
        player2.setPosition(new Vector(700.0f, 350.0f));

    }

    private void loadPlayerAssets(){
        if (!AssetPool.hasSpriteSheet("assets/Player/character_body.png")){
            new SpriteSheet("assets/Player/character_body.png",
                    60, 60, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/Player/character_eyes.png")){
            new SpriteSheet("assets/Player/character_eyes.png",
                    60, 60, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/Player/character_mouth.png")){
            new SpriteSheet("assets/Player/character_mouth.png",
                    60, 60, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/Bullet/bullets.png")){
            new SpriteSheet("assets/Bullet/bullets.png",
                     26, 26, 0, 4, 4);
        }
        /*if (!AssetPool.hasSpriteSheet("assets/Gun/gun_drops/gun_drops.png")){
            new SpriteSheet("assets/Gun/gun_drops/gun_drops.png",
                    123, 64, 0, 3, 3);
        }*/
        /*if (!AssetPool.hasSpriteSheet("assets/Gun/guns/guns.png")){
            new SpriteSheet("assets/Gun/guns/guns.png",
                    , , , , )
        }*/
        new Sprite("assets/Background/background_yellow.png");
        new Sprite("assets/Background/background_yellow_1.png");
    }
    public void clearLevel(){
        removeAll();
    }
    public void loadLevel(File fl1, File fl2){
        clearLevel();
        try (FileInputStream file = new FileInputStream(fl1)){
            ObjectInputStream ois = new ObjectInputStream(file);
            List<GameObject> loadDate = (List<GameObject>) ois.readObject();
            for(GameObject ld: loadDate){
                addToLayerOne(ld);
            }
            ois.close();
        } catch (FileNotFoundException ex){
            System.out.println("oopsies FileNotFoundException");
        } catch (IOException ex){
            throw new RuntimeException(ex);
        } catch (ClassCastException ex){
            System.out.println("oopsies ClassCastException");
        } catch (ClassNotFoundException ex){
            System.out.println();
        } finally {
            System.out.println("Success?");
        }
        try (FileInputStream file = new FileInputStream(fl2)){
            ObjectInputStream ois = new ObjectInputStream(file);
            List<GameObject> loadDate = (List<GameObject>) ois.readObject();
            for(GameObject ld: loadDate){
                addToLayerTwo(ld);
                collisionLayer.addToLayer(ld);
            }
            ois.close();
        } catch (FileNotFoundException ex){
            System.out.println("oopsies FileNotFoundException");
        } catch (IOException ex){
            throw new RuntimeException(ex);
        } catch (ClassCastException ex){
            System.out.println("oopsies ClassCastException");
        } catch (ClassNotFoundException ex){
            System.out.println();
        } finally {
            System.out.println("Success?");
        }
    }

    @Override
    public void update(double dt) {
        for (GameObject g: gameObjectList){
            g.update(dt);
        }

        lastSpawnTime += dt;
        if (Window.getWindow().getKeyListener().isKeyPressed(KeyEvent.VK_F1) && lastSpawnTime >= spawnCooldown){
            respawn();
            lastSpawnTime = 0;
        }
        if (Window.getWindow().getKeyListener().isKeyPressed(KeyEvent.VK_SPACE) && lastSpawnTime >= spawnCooldown && player1IsAlive) {
            spawnBullet(player1);  //spawn the bullet
            lastSpawnTime = 0;  //reset the cooldown timer
        } else if (Window.getWindow().getKeyListener().isKeyPressed(KeyEvent.VK_NUMPAD1) && lastSpawnTime >= spawnCooldown && player2IsAlive) {
            spawnBullet(player2);  //spawn the bullet
            lastSpawnTime = 0;  //reset the cooldown timer
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        background.render(g2);
        //grid.draw(g2);
        layer1.render(g2);
        layer2.render(g2);
    }

    public List<GameObject> getPlayers(){
        List<GameObject> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        return players;
    }

    private void spawnBullet(GameObject player) {
        float playerMidX = player.getX() + player.getComponent(BoxBounds.class).getWidth() / 2.0f -13;
        float playerMidY = player.getY() + player.getComponent(BoxBounds.class).getHeight() / 2.0f - 13;
        Vector lastDirection = player.getComponent(PlayerOneControls.class).getLastDirection();

        //get the last direction from the controls

        if (lastDirection.getX() == -1){
            playerMidX = player.getX() - 26;
        } else if (lastDirection.getX() == 1){
            playerMidX = player.getX() + player.getComponent(BoxBounds.class).getWidth();
        } else if (lastDirection.getY() == -1){
            playerMidY = player.getY() - 26;
        } else if (lastDirection.getY() == 1){
            playerMidY = player.getY() + player.getComponent(BoxBounds.class).getHeight() + 26;
        }
        Vector spawnPosition = new Vector(playerMidX, playerMidY);
        // if the last direction is non-zero, spawn a bullet
        if (lastDirection.getX() != 0 || lastDirection.getY() != 0) {
            Vector bulletVelocity = new Vector(lastDirection.getX() * 1000.0f, lastDirection.getY() * 1000.0f);
            Bullet newBullet = new Bullet(spawnPosition, bulletVelocity);
            newBullet.addComponent(AssetPool.getSpriteSheet("assets/Bullet/bullets.png").getSprite(0).copy());
            newBullet.addComponent(new BoxBounds(26, 26));
            addToLayerTwo(newBullet);  //add bullet to the scene
        }
    }


}


