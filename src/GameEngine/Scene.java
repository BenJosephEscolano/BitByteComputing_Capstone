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
    protected Renderer layer1;
    protected Renderer layer2;

    public Scene(String name){
        this.name = name;
        this.camera = new Camera(new Vector());
        this.gameObjectList = new ArrayList<>();
        this.layer1 = new Renderer(this.camera);
        this.layer2 = new Renderer(this.camera);
    }

    public List<GameObject> getGameObjectList() {
        return gameObjectList;
    }

    public Renderer getRenderer(int n) {
        switch (n){
            case 1:
                return layer1;
            case 2:
                return layer2;
            default:
                return null;
        }
    }

    public Camera getCamera(){
        return camera;
    }

    public void addGameObject(GameObject g){
        gameObjectList.add(g);
        layer1.submit(g);
        for (Component c: g.getAllComponents()){
            c.start();
        }
    }

    public void addToLayerOne(GameObject g){
        gameObjectList.add(g);
        layer1.submit(g);
        for (Component c: g.getAllComponents()){
            c.start();
        }
    }
    public void addToLayerTwo(GameObject g){
        gameObjectList.add(g);
        layer2.submit(g);
        for (Component c: g.getAllComponents()){
            c.start();
        }
    }


    public void removeAll(){
        gameObjectList.removeAll(gameObjectList);
        layer1.removeAll();
        layer2.removeAll();
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void draw(Graphics2D g2);

}
