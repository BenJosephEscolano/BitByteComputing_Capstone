package Component;

import Util.Vector;

import java.awt.event.KeyEvent;

public class PlayerOneControls extends Controls{
    public boolean hasJumped;
    private int i=0;

    private Vector lastDirection = new Vector(0, 0);
    // store last direction faced to shoot while not moving

    private Gun currentGun;
    // store current gun (pistol or AR)

    public PlayerOneControls(){
        hasJumped = false;
    }

    @Override
    public void update(double dt){

        if (keyLisentner.isKeyPressed(KeyEvent.VK_W)){
            lastDirection.setX(0);
            lastDirection.setY(-1);
            jump();
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_A)){
            lastDirection.setX(-1);
            lastDirection.setY(0);
            moveLeft();
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_D)){
            lastDirection.setX(1);
            lastDirection.setY(-0.5f);
            moveRight();
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_S)){
            lastDirection.setX(0);
            lastDirection.setY(1);
            moveDown();
        } else {
            stop();
        }
    }

    public void jump() {
        if (!hasJumped){
            Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
            velocity.setY(-1000);
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

    public Vector getLastDirection() {
        return lastDirection;
    }
}
