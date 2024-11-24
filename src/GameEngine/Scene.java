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
    protected Renderer background;
    public CollisionLayer collisionLayer;
    public boolean player1IsAlive = true;
    public boolean player2IsAlive = true;

    public Scene(String name){
        this.name = name;
        this.camera = new Camera(new Vector());
        this.gameObjectList = new ArrayList<>();
        this.layer1 = new Renderer(this.camera);
        this.layer2 = new Renderer(this.camera);
        this.background = new Renderer(this.camera);
        collisionLayer = new CollisionLayer();
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
    public void addToBackground(GameObject g){
        if (background.getRenderList().size() > 1){
            background.getRenderList().set(0,g);
        } else {
            background.getRenderList().add(g);
        }
    }
    public void addToLayerTwo(GameObject g){
        gameObjectList.add(g);
        layer2.submit(g);
        for (Component c: g.getAllComponents()){
            c.start();
        }
    }

    public void setToLayerTwo(GameObject gameObject){
        List <GameObject> layer = layer2.getRenderList();
        int index = 0;
        for (GameObject g: gameObjectList){
            if (gameObject.equals(g)){
                gameObjectList.set(index, gameObject);
                layer.set(layer.indexOf(g), gameObject);
                collisionLayer.addToLayer(gameObject);
                return;
            } else {
                index++;
            }
        }
        addToLayerTwo(gameObject);
        collisionLayer.addToLayer(gameObject);
    }

    public void setToLayerOne(GameObject gameObject){
        List <GameObject> layer = layer1.getRenderList();
        int index = 0;
        for (GameObject g: gameObjectList){
            if (gameObject.equals(g)){
                gameObjectList.set(index, gameObject);
                layer.set(layer.indexOf(g), gameObject);
                return;
            } else {
                index++;
            }
        }
        addToLayerOne(gameObject);
    }

    public void playerDead(int num){
        if (num == 1){
            player1IsAlive = false;
        } else {
            player2IsAlive = false;
        }
    }

    public List<GameObject> getCollisionLayer(){
        return collisionLayer.gameObjectList;
    }

    public void remove(GameObject gameObject){
        gameObjectList.remove(gameObject);
        layer1.getRenderList().remove(gameObject);
        layer2.getRenderList().remove(gameObject);
    }

    public void erase(GameObject eraser){
        int index = 0;
        for (GameObject g: gameObjectList){
            if (eraser.equals(g)){
                GameObject remove = gameObjectList.get(index);
                gameObjectList.remove(remove);
                int layerIndex = layer2.getRenderList().indexOf(remove);
                layer1.getRenderList().remove(layerIndex);
                layer2.getRenderList().remove(layerIndex);
                return;
            } else {
                index++;
            }
        }
        System.out.println("nothing to erase");
    }


    public void removeAll(){
        gameObjectList.removeAll(gameObjectList);
        layer1.removeAll();
        layer2.removeAll();
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void draw(Graphics2D g2);
    public abstract GameObject getPlayer1();
    public abstract GameObject getPlayer2();
}
