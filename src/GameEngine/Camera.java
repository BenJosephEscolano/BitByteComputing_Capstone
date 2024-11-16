package GameEngine;

import Util.Vector;

public class Camera {
    private Vector position;

    public Camera(Vector position){
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    public double getX(){
        return getPosition().getX();
    }

    public double getY(){
        return getPosition().getY();
    }

    public void setX(float num){
        getPosition().setX(num);
    }

    public void setY(float num){
        getPosition().setY(num);
    }
}
