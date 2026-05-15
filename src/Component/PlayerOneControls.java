package Component;

import GameEngine.PlayerCharacter;
import Util.Vector;
import com.studiohartman.jamepad.ControllerState;

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
        this(player, 0);
    }

    public PlayerOneControls(PlayerCharacter player, int controllerIndex) {
        hasJumped = false;
        lastDirection = new Vector(1, 0);
        this.player = player;
        this.controllerIndex = controllerIndex;
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

        ControllerState state = controllerManager.getState(controllerIndex);
        
        boolean jumpPressed = keyLisentner.isKeyPressed(KeyEvent.VK_W) || state.a;
        boolean leftPressed = keyLisentner.isKeyPressed(KeyEvent.VK_A) || state.dpadLeft || state.leftStickX < -0.5f;
        boolean rightPressed = keyLisentner.isKeyPressed(KeyEvent.VK_D) || state.dpadRight || state.leftStickX > 0.5f;
        boolean firePressed = keyLisentner.isKeyPressed(KeyEvent.VK_SPACE) || state.x || state.rightTrigger > 0.5f;

        if (jumpPressed && player.getAliveStatus()) {
            jump();
        } else if (leftPressed && player.getAliveStatus()) {
            lastDirection.setX(-1);
            lastDirection.setY(0);
            moveLeft();
        } else if (rightPressed && player.getAliveStatus()) {
            lastDirection.setX(1);
            lastDirection.setY(0);
            moveRight();
        } else { // we want to stop the player from moving regardless if they are alive or not
            stop();
        }
        if (firePressed && player.getAliveStatus()){
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