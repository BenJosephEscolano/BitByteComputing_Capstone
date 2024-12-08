package Component;

import GameEngine.PlayerCharacter;
import Util.Vector;

import java.awt.event.KeyEvent;

public class PlayerOneControls extends Controls {
    boolean hasJumped;
    protected Vector lastDirection;
    protected float targetVelocityX = 0;
    protected final float acceleration = 3500f;
    protected final float maxSpeed = 300.0f;

    protected Gun shoot;
    private PlayerCharacter player;

    public PlayerOneControls(PlayerCharacter player) {
        hasJumped = false;
        lastDirection = new Vector(1, 0);
        this.player = player;
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

            if (keyLisentner.isKeyPressed(KeyEvent.VK_W) && player.getAliveStatus()) {
                jump();
            } else if (keyLisentner.isKeyPressed(KeyEvent.VK_A)&& player.getAliveStatus()) {
                lastDirection.setX(-1);
                lastDirection.setY(0);
                moveLeft();
            } else if (keyLisentner.isKeyPressed(KeyEvent.VK_D)&& player.getAliveStatus()) {
                lastDirection.setX(1);
                lastDirection.setY(0);
                moveRight();
            } else { // we want to stop the player from moving regardless if they are alive or not
                stop();
            }
            if (keyLisentner.isKeyPressed(KeyEvent.VK_SPACE)&& player.getAliveStatus()){
                shoot.fire(player);
            }



    }

    public void jump() {
        if (!hasJumped) {
            Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
            velocity.setY(-900);
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

    public void setCommand(Gun shoot){
        this.shoot = shoot;
    }

    public Vector getLastDirection() {
        return lastDirection;
    }
}