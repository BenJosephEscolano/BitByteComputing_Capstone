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
    private GameObject player;
    private boolean isLoaded = false;

    public LevelScene(String name){
        super(name);
    }

    public GameObject getPlayer(){
        return player;
    }

    @Override
    public void init() {
        player = new GameObject("Some game object", new Transform(new Vector(500.0f, 350.0f)));
        SpriteSheet layerOne, layerTwo, layerThree;
        /*if (!AssetPool.hasSpriteSheet("assets/player/layerOne.png")){
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
        }*/
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
        player.addComponent(playerComp);
        player.addComponent(new RigidBody( new Vector(395,0)));
        player.addComponent(new BoxBounds(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT));

        GameObject ground = new GameObject("Ground", new Transform(new Vector(0, Constants.GROUND_Y)));
        ground.addComponent(new Ground());

        addGameObject(player);
        addGameObject(ground);
    }

    @Override
    public void update(double dt) {
        if (!isLoaded) {
            File fl = new File("level.obj");

            try (FileInputStream file = new FileInputStream(fl)) {
                ObjectInputStream ois = new ObjectInputStream(file);
                java.util.List<GameObject> loadDate = (List<GameObject>) ois.readObject();
                for (GameObject ld : loadDate) {
                    addGameObject(ld);
                }
                ois.close();
            } catch (FileNotFoundException ex) {
                System.out.println("oopsies FileNotFoundException");
            } catch (IOException ex) {
                throw new RuntimeException(ex);

            } catch (ClassCastException ex) {
                System.out.println("oopsies ClassCastException");
            } catch (ClassNotFoundException ex) {
                System.out.println();
            } finally {
                System.out.println("Success?");
                isLoaded = true;
            }
        }
        float newX = 0;
        float newY = 0;
        if (player.getX() - camera.getX() > Constants.CAMERA_OFFSET_X){
            newX = player.getX() - Constants.CAMERA_OFFSET_X;
            camera.setX(newX);
        }
        if (player.getY() - camera.getY() > Constants.CAMERA_OFFSET_Y){
            newY = player.getY() - Constants.CAMERA_OFFSET_Y;
            camera.setY(newY);
        }
        if (camera.getY() > Constants.CAMERA_GROUND_OFFSET){
            newY = Constants.CAMERA_GROUND_OFFSET;
            camera.setY(newY);
        }

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


