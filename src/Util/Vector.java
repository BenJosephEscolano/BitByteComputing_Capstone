package Util;

import java.io.Serializable;

public class Vector implements Serializable {
    private float x, y;

    public Vector(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector(){
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setVector(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector copy(){
        return new Vector(x, y);
    }


}
