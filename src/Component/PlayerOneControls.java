package Component;

import Util.Vector;

import java.awt.event.KeyEvent;

public class PlayerOneControls extends Controls {
    boolean hasJumped;
    private Vector lastDirection = new Vector(0, 0);
    private float targetVelocityX = 0;
    private final float acceleration = 1000.0f;
    private final float maxSpeed = 500.0f;

    public PlayerOneControls() {
        hasJumped = false;
    }

    @Override
    public void update(double dt) {
        Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;

        //adjust velocity gradually towards the target
        if (velocity.getX() < targetVelocityX) {
            velocity.setX(Math.min(velocity.getX() + acceleration * (float) dt, targetVelocityX));
        } else if (velocity.getX() > targetVelocityX) {
            velocity.setX(Math.max(velocity.getX() - acceleration * (float) dt, targetVelocityX));
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
        } else {
            stop();
        }
    }

    public void jump() {
        if (!hasJumped) {
            Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
            velocity.setY(-1000);
            hasJumped = true;
        }
    }

    public void stop() {
        targetVelocityX = 0; // Slowly bring velocity to 0
    }

    @Override
    public void moveLeft() {
        targetVelocityX = -maxSpeed;
    }

    @Override
    public void moveRight() {
        targetVelocityX = maxSpeed;
    }

    @Override
    public void moveDown() {

    }

    public Vector getLastDirection() {
        return lastDirection;
    }
}