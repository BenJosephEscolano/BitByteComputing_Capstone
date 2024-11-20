package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import UI.MainContainer;
import Util.Constants;
import Util.SceneCode;
import Util.Vector;
import Component.*;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

public class LevelEditorScene extends Scene{
    private GameObject player;
    private GameObject grid;
    private GameObject mouseCursor;
    private CameraControls cameraControls;
    private List<GameObject> editorItems;
    private GameObject currItem;
    private int currIndex = 0;
    private float debounceTime = 0.5f;
    private float debounceLeft = 0.0f;

    public LevelEditorScene(String name){
        super(name);
        editorItems = new ArrayList<>();
        debounceLeft = debounceTime;
    }

    @Override
    public void init() {
        loadAssets();
        loadEditorItems();
        cameraControls = new CameraControls();
        mouseCursor = new GameObject("Mouse Cursor", new Transform(new Vector()));
        mouseCursor.addComponent(new SnapToGrid(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
        grid = new GameObject("grid", new Transform(new Vector()));
        grid.addComponent(new Grid());
        currItem = editorItems.get(currIndex);
    }

    private void loadAssets(){
        if (!AssetPool.hasSpriteSheet("assets/platform_tiles.png")){
            new SpriteSheet("assets/platform_tiles.png", 60, 60, 0, 7, 7);
        }
        if (!AssetPool.hasSpriteSheet("assets/platform_tiles_shadow.png")){
            new SpriteSheet("assets/platform_tiles_shadow.png", 60, 60, 1, 7, 7);
        }
    }

    private void loadEditorItems(){
        int numOfPlatforms = 7;
        for (int i = 0; i < numOfPlatforms; i++){
            GameObject block = new GameObject("block", new Transform(new Vector()));
            block.addComponent(AssetPool.getSpriteSheet("assets/platform_tiles.png").getSprite(i));
            block.addComponent(new SnapToGrid(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
            block.addComponent(new Shadow(AssetPool.getSpriteSheet("assets/platform_tiles_shadow.png").getSprite(i)));
            editorItems.add(block);
        }
        System.out.println("Level Items are loaded: " + editorItems.size());
    }

    @Override
    public void update(double dt) {
        debounceLeft -= (float) dt;
        KL keyListener = Window.getWindow().getKeyListener();

        if (camera.getY() > Constants.CAMERA_GROUND_OFFSET){
            float newY = Constants.CAMERA_GROUND_OFFSET;
            camera.setY(newY);
        }

        for (GameObject g: gameObjectList){
            g.update(dt);
        }

        File fl1 = new File("level_layer1.obj");
        File fl2 = new File("Level_layer2.obj");
        if (keyListener.isKeyPressed(KeyEvent.VK_F1) || keyListener.isKeyPressed(KeyEvent.VK_F4)){
            saveLevel(fl1, fl2);
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_F2)){
            loadLevel(fl1, fl2);
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_F3)) {
            clearLevel();
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_D) && debounceLeft < 0){
            debounceLeft = debounceTime;
            rotateItems(1);
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_A) && debounceLeft < 0){
            debounceLeft = debounceTime;
            rotateItems(-1);
        }

        cameraControls.update(dt);
        grid.update(dt);
        mouseCursor.update(dt);
        currItem.update(dt);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        grid.draw(g2);
        layer1.render(g2);
        layer2.render(g2);
        currItem.getComponent(SnapToGrid.class).draw(g2);
    }

    private void rotateItems(int num){
        currIndex = (editorItems.size() + currIndex + num) % editorItems.size();
        currItem = editorItems.get(currIndex);
    }

    public GameObject getMouseCursor() {
        return mouseCursor;
    }

    public void setMouseCursor(GameObject mouseCursor){
        this.mouseCursor = mouseCursor;
    }

    public void saveLevel(File fl1, File fl2){
        try (FileOutputStream file = new FileOutputStream(fl1)){
            ObjectOutputStream oos = new ObjectOutputStream(file);
            oos.writeObject(layer1.getRenderList());
            oos.flush();
            oos.close();
        } catch (FileNotFoundException ex){
            System.out.println("oopsies FileNotFoundException");
        } catch (IOException ex){
            System.out.println();
        }
        try (FileOutputStream file = new FileOutputStream(fl2)){
            ObjectOutputStream oos = new ObjectOutputStream(file);
            oos.writeObject(layer2.getRenderList());
            oos.flush();
            oos.close();
        } catch (FileNotFoundException ex){
            System.out.println("oopsies FileNotFoundException");
        } catch (IOException ex){
            System.out.println();
        }
        Window.getWindow().close();
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

    public void clearLevel(){
        removeAll();
    }
}
