package GameEngine;

import DataStructure.Transform;
import Util.Vector;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameObject implements Serializable {
    private List<Component> componentList;
    private String name;
    private Transform transform;

    public GameObject(String name, Transform transform){
        this.name = name;
        this.transform = transform;
        this.componentList = new ArrayList<>();
    }

    public List<Component> getAllComponents(){
        return this.componentList;
    }

    /*
    Courtesy from the lovely folks of StackOverflow, don't ask me how it works ( • ̀ω•́ )✧
     */
    public <T extends Component> T getComponent(Class<T> componentClass){
        for (Component c: componentList){
            if (componentClass.isAssignableFrom(c.getClass())){
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e){
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
        return null;
    }

    public void addComponent(Component c){
        componentList.add(c);
        c.setGameObject(this);
    }

    public <T extends Component> void removeComponent(Class<T> componentClass){
        for (Component c: componentList){
            if (componentClass.isAssignableFrom(c.getClass())){
                componentList.remove(c);
                return;
            }
        }
    }

    public void update(double dt){
        for (Component c: componentList){
            c.update(dt);
        }
    }

    public void draw(Graphics2D g2){
        for (Component c: componentList){
            c.draw(g2);
        }
    }

    public boolean equals(GameObject g){
        if (g.getX() == getX() && g.getY() == getY()){
            return true;
        }
        return false;
    }

    public GameObject copy(){
      GameObject newGameObject = new GameObject("Generated", transform.copy());
      for (Component c: componentList){
          Component copy = c.copy();
          if (copy != null){
              newGameObject.addComponent(copy);
          }

      }
      return newGameObject;
    }

    public float getX(){
        return transform.getVectorX();
    }

    public float getY(){
        return transform.getVectorY();
    }

    public void setX(float x){
        transform.getPosition().setX(x);
    }

    public void setY(float y) {
        transform.getPosition().setY(y);
    }

    public void setTransform(Transform transform){
        this.transform = transform;
    }

    public float getVectorRotation(){
        return transform.getRotation();
    }

    public void setScale(float x, float y){
        transform.setScale(x, y);
    }

    public Vector getScale(){
        return transform.getScale();
    }

    public Vector getPosition(){
        return transform.getPosition();
    }

    public void setPosition(Vector position){
        transform.setPosition(position);
    }


}
