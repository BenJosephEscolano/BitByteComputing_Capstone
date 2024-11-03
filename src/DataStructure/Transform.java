package DataStructure;

import Util.Vector;

public class Transform {
    private Vector position;
    private Vector scale;
    private float rotation;

    public Transform(Vector position){
        this.position = position;
        this.scale = new Vector(1.0f, 1.0f);
        this.rotation = 0.0f;
    }

    public Vector getPosition(){
        return position;
    }

    public void setPosition(Vector newPosition){
        this.position = newPosition;
    }

    public void setScale(Vector newScale){
        this.scale = newScale;
    }

    @Override
    public String toString(){
        return "Position : (" + position.getX() + ", " + position.getY() + ")";
    }

    public float getVectorX(){
        return position.getX();
    }

    public float getVectorY(){
        return position.getY();

    }

    public float getRotation(){
        return rotation;
    }

    public void setRotation(float rotation){
        this.rotation = rotation;
    }

    public Vector getScale(){
        return scale;
    }

    public void setScale(float x, float y){
        scale.setVector(x, y);
    }

    public Transform copy(){
        Transform transform = new Transform(this.position.copy());
        transform.scale = this.scale.copy();
        transform.rotation = this.rotation;
        return transform;
    }

}
