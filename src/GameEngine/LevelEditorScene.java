package GameEngine;

import DataStructure.Transform;
import UI.MainContainer;
import Util.Constants;
import Util.Vector;
import Component.*;

import java.awt.*;

public class LevelEditorScene extends Scene{
    private GameObject player;
    private GameObject ground;
    private GameObject mouseCursor;
    private Grid grid;
    private CameraControls cameraControls;
    private MainContainer mainContainer = new MainContainer();

    public LevelEditorScene(String name){
        super(name);
    }

    public GameObject getPlayer(){
        return player;
    }

    @Override
    public void init() {
        grid = new Grid();
        cameraControls = new CameraControls();
        mainContainer.start();
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
        float newY = 0;
        if (camera.getY() > Constants.CAMERA_GROUND_OFFSET){
            newY = Constants.CAMERA_GROUND_OFFSET;
            camera.setY(newY);
        }

        for (GameObject g: gameObjectList){
            g.update(dt);
        }
        cameraControls.update(dt);
        grid.update(dt);
        mainContainer.update(dt);
        mouseCursor.update(dt);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        grid.draw(g2);
        renderer.render(g2);
        mainContainer.draw(g2);
        mouseCursor.draw(g2);
    }

    public GameObject getMouseCursor() {
        return mouseCursor;
    }

    public void setMouseCursor(GameObject mouseCursor){
        this.mouseCursor = mouseCursor;
    }
}
