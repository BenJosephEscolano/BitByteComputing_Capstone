package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.Constants;
import Util.SceneCode;
import Util.Timer;
import Util.Vector;
import Component.*;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

public class LevelEditorScene extends Scene{
    private GameObject mouseCursor;
    private Grid grid;
    private CameraControls cameraControls;
    private List<GameObject> editorItems;
    private GameObject currItem;
    private int currIndex = 0;
    private Timer debounceTime;


    public LevelEditorScene(String name){
        super(name);
        editorItems = new ArrayList<>();
        debounceTime = new Timer(0.5f, 0.5f);
        loadLevel(currLevel);
    }

    @Override
    public void init() {
        loadAssets();
        loadEditorItems();
        grid = new Grid();
        cameraControls = new CameraControls();
        mouseCursor = new GameObject("Mouse Cursor", new Transform(new Vector()));
        mouseCursor.addComponent(new SnapToGrid(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
        currItem = editorItems.get(currIndex);
    }

    private void loadAssets(){
        System.out.println("Loading Platforms: " + AssetPool.hasSpriteSheet("assets/Tiles/platform_tiles.png"));
        System.out.println("Loading Shadows: " + AssetPool.hasSpriteSheet("assets/Tiles/platform_tiles_shadow.png"));
        if (!AssetPool.hasSpriteSheet("assets/Tiles/platform_tiles.png")){
            new SpriteSheet("assets/Tiles/platform_tiles.png", Constants.TILE_WIDTH, Constants.TILE_HEIGHT, 0, 7, 7);
        }
        if (!AssetPool.hasSpriteSheet("assets/Tiles/platform_tiles_shadow.png")){
            new SpriteSheet("assets/Tiles/platform_tiles_shadow.png", Constants.TILE_WIDTH, Constants.TILE_HEIGHT, 0, 7, 7);
        }
        System.out.println("Loading Platforms: " + AssetPool.hasSpriteSheet("assets/Tiles/platform_tiles.png"));
        System.out.println("Loading Shadows: " + AssetPool.hasSpriteSheet("assets/Tiles/platform_tiles_shadow.png"));
    }

    private void loadEditorItems(){
        int numOfPlatforms = 7;
        for (int i = 0; i < numOfPlatforms; i++){
            GameObject block = new GameObject("block", new Transform(new Vector()));
            block.addComponent(AssetPool.getSpriteSheet("assets/Tiles/platform_tiles.png").getSprite(i));
            block.addComponent(new SnapToGrid(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
            block.addComponent(new Shadow(AssetPool.getSpriteSheet("assets/Tiles/platform_tiles_shadow.png").getSprite(i)));
            editorItems.add(block);
        }
        System.out.println("Level Items are loaded: " + editorItems.size());
    }

    @Override
    public void update(double dt) {
        debounceTime.addTime(dt);
        KL keyListener = Window.getKeyListener();
        if (camera.getY() > Constants.CAMERA_GROUND_OFFSET){
            float newY = Constants.CAMERA_GROUND_OFFSET;
            camera.setY(newY);
        }
        for (GameObject g: gameObjectList){
            g.update(dt);
        }

        LevelData lvl = levels.getLevels().get(0);
        if (keyListener.isKeyPressed(KeyEvent.VK_F1)){
            saveLevel(lvl);
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_F2) && debounceTime.isTime(0)){
            debounceTime.resetTime();
            loadLevel(lvl);
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_F3)) {
            clearLevel();
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_D) && debounceTime.isTime(0)){
            debounceTime.resetTime();
            rotateItems(1);
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_A) && debounceTime.isTime(0)){
            debounceTime.resetTime();
            rotateItems(-1);
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_Q) && debounceTime.isTime(0)){
            debounceTime.resetTime();
            rotateLevels(-1);
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_E) && debounceTime.isTime(0)){
            debounceTime.resetTime();
            rotateLevels(1);
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_Z) && debounceTime.isTime(0)){
            debounceTime.resetTime();
            remove();
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_F10)){
            System.out.println("Changing scene to level");
            saveLevel(currLevel);
            Window.changeScene(SceneCode.Level);
        }
        //cameraControls.update(dt);
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

    private void rotateLevels(int num){
        saveLevel(currLevel);
        currLevelIndex = (getLevels().size() + currLevelIndex + num) % getLevels().size();
        currLevel = getLevels().get(currLevelIndex);
        loadLevel(currLevel);
        System.out.println(currLevel + " | " + currLevelIndex);
    }

    public PlayerCharacter getPlayer1(){
        return null;
    }
    public PlayerCharacter getPlayer2(){
        return  null;
    }

}
