package GameEngine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer {
    List<GameObject> gameObjectList;
    Camera camera;

    public Renderer(Camera camera){
        this.camera = camera;
        this.gameObjectList = new ArrayList<>();
    }

    public void submit(GameObject gameObject){
        this.gameObjectList.add(gameObject);
    }

    public void unsubmit(GameObject gameObject){
        this.gameObjectList.remove(gameObject);
    }

    public void removeAll(){
        gameObjectList.removeAll(gameObjectList);
    }

    public void render(Graphics2D g2){
        for (GameObject g: gameObjectList){
            g.draw(g2);
        }
    }

    public List<GameObject> getRenderList(){
        return gameObjectList;
    }


}
