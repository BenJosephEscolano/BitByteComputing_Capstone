package GameEngine;

import DataStructure.Transform;
import UI.MainContainer;
import Util.Constants;
import Util.SceneCode;
import Util.Vector;
import Component.*;

import java.util.Arrays;
import java.util.List;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

public class LevelEditorScene extends Scene{
    private GameObject player;
    private GameObject ground;
    private GameObject mouseCursor;
    private Grid grid;
    private CameraControls cameraControls;
    private MainContainer edittingButtons = new MainContainer();
    private boolean isLoaded = false;

    public LevelEditorScene(String name){
        super(name);
    }

    @Override
    public void init() {
        grid = new Grid();
        cameraControls = new CameraControls();
        edittingButtons.start();
        mouseCursor = new GameObject("Mouse Cursor", new Transform(new Vector()));
        mouseCursor.addComponent(new SnapToGrid(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));

        player = new GameObject("Some game object", new Transform(new Vector(500.0f, 350.0f)));
        SpriteSheet layerOne = new SpriteSheet("assets/player/layerOne.png",
                42, 42, 2, 13, 13 * 5);
        SpriteSheet layerTwo = new SpriteSheet("assets/player/layerTwo.png",
                42, 42, 2, 13, 13 * 5);
        SpriteSheet layerThree = new SpriteSheet("assets/player/layerThree.png",
                42, 42, 2, 13, 13 * 5);
        int spriteIndex = 42;
        Player playerComp = new Player(
                layerOne.getSprite(spriteIndex),
                layerTwo.getSprite(spriteIndex),
                layerThree.getSprite(spriteIndex),
                Color.CYAN,
                Color.BLUE);
        player.addComponent(playerComp);

        ground = new GameObject("Ground", new Transform(new Vector(0, Constants.GROUND_Y)));
        ground.addComponent(new Ground());

        //addGameObject(player);
        addGameObject(ground);
    }

    @Override
    public void update(double dt) {
        KL keyListener = Window.getWindow().getKeyListener();
        float newY = 0;
        if (camera.getY() > Constants.CAMERA_GROUND_OFFSET){
            newY = Constants.CAMERA_GROUND_OFFSET;
            camera.setY(newY);
        }

        for (GameObject g: gameObjectList){
            g.update(dt);
        }
        File fl = new File("level.obj");
        if (keyListener.isKeyPressed(KeyEvent.VK_F1)){
            try (FileOutputStream file = new FileOutputStream(fl)){
                ObjectOutputStream oos = new ObjectOutputStream(file);
                oos.writeObject(gameObjectList);
                oos.flush();
                oos.close();
            } catch (FileNotFoundException ex){
                System.out.println("oopsies FileNotFoundException");
            } catch (IOException ex){
                System.out.println();
            }
            Window.getWindow().close();
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_F2) && !isLoaded){
            try (FileInputStream file = new FileInputStream(fl)){
                ObjectInputStream ois = new ObjectInputStream(file);
                List<GameObject> loadDate = (List<GameObject>) ois.readObject();
                for(GameObject ld: loadDate){
                    addGameObject(ld);
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
                isLoaded = true;
            }
        }

        cameraControls.update(dt);
        grid.update(dt);
        edittingButtons.update(dt);
        mouseCursor.update(dt);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        grid.draw(g2);
        renderer.render(g2);
        edittingButtons.draw(g2);
        mouseCursor.getComponent(SnapToGrid.class).draw(g2);
    }

    public GameObject getMouseCursor() {
        return mouseCursor;
    }

    public void setMouseCursor(GameObject mouseCursor){
        this.mouseCursor = mouseCursor;
    }
}
