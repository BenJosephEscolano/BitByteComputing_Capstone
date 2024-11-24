package Component;

import DataStructure.AssetPool;
import GameEngine.Bullet;
import GameEngine.GameObject;
import GameEngine.Window;
import Util.Vector;

import java.awt.event.KeyEvent;
import java.security.Key;

public class PlayerOneControls extends Controls {
    boolean hasJumped;
    protected Vector lastDirection = new Vector(0, 0);
    protected float targetVelocityX = 0;
    protected final float acceleration = 3500f;
    protected final float maxSpeed = 400.0f;
    protected GameObject player;

    public PlayerOneControls() {
        hasJumped = false;
        player = Window.getWindow().getScene().getPlayer1();
    }


    @Override
    public void update(double dt) {

        Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;

        //adjust velocity gradually towards the target
        if (velocity.getX() < targetVelocityX) {
            velocity.setX(Math.min(velocity.getX() + acceleration * (float) dt , targetVelocityX));
        } else if (velocity.getX() > targetVelocityX) {
            velocity.setX(Math.max(velocity.getX() - acceleration * (float) dt , targetVelocityX));
        }

        if (keyLisentner.isKeyPressed(KeyEvent.VK_W)) {
            lastDirection.setX(0);
            lastDirection.setY(-1);
            jump();
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_A)) {
            lastDirection.setX(-1);
            lastDirection.setY(0);
            moveLeft();
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_D)) {
            lastDirection.setX(1);
            lastDirection.setY(0);
            moveRight();
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_S)) {
            lastDirection.setX(0);
            lastDirection.setY(1);
            moveDown();
        }  else {
            stop();
        }
    }

    public void jump() {
        if (!hasJumped) {
            Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
            velocity.setY(-1050);
            hasJumped = true;
        }
    }

    public void stop() {
        Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
        //velocity.setX(0);

        targetVelocityX = 0; // Slowly bring velocity to 0
    }

    @Override
    public void moveLeft() {
        /*Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
        velocity.setX(-500);*/
        targetVelocityX = -maxSpeed;
    }

    @Override
    public void moveRight() {
        /*Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
        velocity.setX(500);*/
        targetVelocityX = maxSpeed;
    }

    @Override
    public void moveDown() {

    }







    public Vector getLastDirection() {
        return lastDirection;
    }
}