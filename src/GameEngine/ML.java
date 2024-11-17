package GameEngine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class in charge of adding mouse functionality to our program
 */
public class ML extends MouseAdapter implements MouseListener {
    public boolean mousePressed = false;
    public boolean mouseDragged = false;
    public int mouseButton = -1;
    public float x = -1.0f, y = -1.0f;
    public float dx = -1.0f, dy = -1.0f;

    @Override
    public void mousePressed(MouseEvent mouseEvent){
        this.mousePressed = true;
        this.mouseButton = mouseEvent.getButton();
    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent){
        this.mousePressed = false;
        this.mouseDragged = false;
        this.dx = 0;
        this.dy = 0;
        mouseMoved(mouseEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent){
        this.x = mouseEvent.getX();
        this.y = mouseEvent.getY();
    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent){
        this.mouseDragged = true;
        this.dx = mouseEvent.getX() - this.x;
        this.dy = mouseEvent.getY() - this.y;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public int getMouseButton() {
        return mouseButton;
    }

    public float getX(){
        return x;
    }

    public float getY() {
        return y;
    }

    public float getDX(){
        return dx;
    }

    public float getDY() {
        return dy;
    }
    @Override
    public String toString(){
        return "X: " + x + " | Y: " + y;
    }

    public boolean isMouseDragged() {
        return mouseDragged;
    }
}
