package GameEngine;

import Util.Vector;

import java.awt.*;
import java.io.*;
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
    protected CollisionLayer staticBodies;
    protected CollisionLayer activeBodies;
    protected CollisionLayer projectileLayer;
    protected Level levels;
    protected LevelData currLevel;
    protected int currLevelIndex;

    public Scene(String name){
        this.name = name;
        this.camera = new Camera(new Vector());
        this.gameObjectList = new ArrayList<>();
        this.layer1 = new Renderer(this.camera);
        this.layer2 = new Renderer(this.camera);
        this.background = new Renderer(this.camera);
        this.staticBodies = new CollisionLayer();
        this.activeBodies = new CollisionLayer();
        this.levels = new Level();
        this.currLevelIndex = 0;
        this.currLevel = levels.getLevels().get(currLevelIndex);
        this.projectileLayer = new CollisionLayer();
    }

    public void resetCamera(){
        camera.setX(0);
        camera.setY(Window.getWindow().getInsets().top * -1);
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
                return;
            } else {
                index++;
            }
        }
        addToLayerTwo(gameObject);
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


    public void setStaticBodies(GameObject gameObject, int renderLayer){
        if (renderLayer == 1){
            setToLayerOne(gameObject);
        }
        if (renderLayer == 2){
            setToLayerTwo(gameObject);
        }
        getStaticBodies().add(gameObject);
    }

    public void setActiveBodies(GameObject gameObject, int renderLayer){
        if (renderLayer == 1){
            setToLayerOne(gameObject);
        }
        if (renderLayer == 2){
            setToLayerTwo(gameObject);
        }
        getActiveBodies().add(gameObject);
    }

    public void setProjectileLayer(GameObject gameObject){
        setToLayerOne(gameObject);
        projectileLayer.addToLayer(gameObject);
    }

    public List<GameObject> getStaticBodies(){
        return staticBodies.getCollisionLayer();
    }

    public void removeActiveBody(GameObject gameObject){
        activeBodies.remove(gameObject);
        gameObjectList.remove(gameObject);
    }

    public void removeProjectileLayer(GameObject gameObject){
        projectileLayer.remove(gameObject);
        gameObjectList.remove(gameObject);
        layer1.unsubmit(gameObject);
    }

    public List<GameObject> getActiveBodies(){
        return activeBodies.getCollisionLayer();
    }

    public List<GameObject> getProjectileLayer(){
        return projectileLayer.getCollisionLayer();
    }

    public void addToActiveBody(GameObject gameObject){
        this.activeBodies.addToLayer(gameObject);
    }

    public void remove(){
        gameObjectList.removeLast();
        layer1.getRenderList().removeLast();
        layer2.getRenderList().removeLast();
        staticBodies.getCollisionLayer().removeLast();

    }

    public void removeAll(){
        gameObjectList.removeAll(gameObjectList);
        layer1.removeAll();
        layer2.removeAll();
        staticBodies.removeAll();
        activeBodies.removeAll();
    }

    public void saveLevel(LevelData levelData){
        File layer1 = new File(levelData.getLevelLayer(1));
        File layer2 = new File(levelData.getLevelLayer(2));
        saveLevel(layer1, layer2);
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
    }

    public void loadLevel(LevelData levelData){
        File layer1 = new File(levelData.getLevelLayer(1));
        File layer2 = new File(levelData.getLevelLayer(2));
        loadLevel(layer1, layer2);
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
                setStaticBodies(ld, 2);
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
            System.out.println("Game objects: " + gameObjectList.size());
            System.out.println("Renderer: Layer 1: " + layer1.getRenderList().size() + " Layer 2: " + layer2.getRenderList().size());
            System.out.println("Collision: Static: " + staticBodies.getCollisionLayer().size() + " Active: " + activeBodies.getCollisionLayer().size());
        }
    }

    public void clearLevel(){
        removeAll();
        System.out.println("Game objects: " + gameObjectList.size());
        System.out.println("Renderer: Layer 1: " + layer1.getRenderList().size() + " Layer 2: " + layer2.getRenderList().size());
        System.out.println("Collision: Static: " + staticBodies.getCollisionLayer().size() + " Active: " + activeBodies.getCollisionLayer().size());
    }

    public List<LevelData> getLevels(){
        return levels.getLevels();
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void draw(Graphics2D g2);
    public abstract PlayerCharacter getPlayer1();
    public abstract PlayerCharacter getPlayer2();
}
