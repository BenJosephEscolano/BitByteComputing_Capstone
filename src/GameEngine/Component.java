package GameEngine;

import java.awt.*;
import java.io.Serializable;

public abstract class Component implements Serializable {
    private GameObject gameObject;

    public GameObject getGameObject(){
        return this.gameObject;
    }

    public void setGameObject(GameObject gameObject){
        this.gameObject = gameObject;
    }

    public void update(double dt){
        return;
    }

    public void draw(Graphics2D g2){
        return;
    }

    public void start(){
        return;
    }

    public abstract Component copy();
}
