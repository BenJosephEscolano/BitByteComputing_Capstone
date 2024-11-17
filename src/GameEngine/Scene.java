package GameEngine;

import Util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Scene class is a container of the related objects needed for the game to function
 */

public abstract class Scene {
    String name;
    protected Camera camera;
    protected List<GameObject> gameObjectList;
    protected Renderer renderer;

    public Scene(String name){
        this.name = name;
        this.camera = new Camera(new Vector());
        this.gameObjectList = new ArrayList<>();
        this.renderer = new Renderer(this.camera);

    }

    public List<GameObject> getGameObjectList() {
        return gameObjectList;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Camera getCamera(){
        return camera;
    }

    public void addGameObject(GameObject g){
        gameObjectList.add(g);
        renderer.submit(g);
        for (Component c: g.getAllComponents()){
            c.start();
        }
    }

    public void removeAll(){
        gameObjectList.removeAll(gameObjectList);
        renderer.removeAll();
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void draw(Graphics2D g2);

}
