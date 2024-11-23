package Component;

import Util.Vector;

import java.awt.event.KeyEvent;

public class PlayerOneControls extends Controls{
    public boolean hasJumped;
    private int i=0;

    public PlayerOneControls(){
        hasJumped = false;
    }
    @Override
    public void update(double dt){

        if(keyLisentner.isKeyPressed((KeyEvent.VK_Q)) && keyLisentner.isKeyPressed(KeyEvent.VK_D)){
            Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
            velocity.setX(1500);
            System.out.println("Dashing!X");
        }
        if(keyLisentner.isKeyPressed((KeyEvent.VK_Q)) && keyLisentner.isKeyPressed(KeyEvent.VK_A)){
            Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
            velocity.setX(-1500);
            System.out.println("Dashing!Y");
        }

        if (keyLisentner.isKeyPressed(KeyEvent.VK_W)){
            jump();
        }else if (keyLisentner.isKeyPressed(KeyEvent.VK_A)){
            moveLeft();
        }else if (keyLisentner.isKeyPressed(KeyEvent.VK_D)){
            moveRight();
        } else {
            stop();
        }
    }

    public void jump() {
        if (!hasJumped){
            Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
            velocity.setY(-800);
            System.out.println("Jumped");
            System.out.println(velocity.getY());
            hasJumped = true;
        }


    }
    public void stop(){
        Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
        velocity.setX(0);
    }
    @Override
    public void moveLeft() {
        Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
        velocity.setX(-500);
    }

    @Override
    public void moveRight() {
        Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
        velocity.setX(500);
    }

    @Override
    public void moveDown() {

    }
}