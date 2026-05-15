package Component;

import GameEngine.*;
import Util.Vector;
import com.studiohartman.jamepad.ControllerState;

import java.awt.event.KeyEvent;

public class PlayerTwoControls extends PlayerOneControls{
    PlayerCharacter player;
    public PlayerTwoControls(PlayerCharacter player){
        this(player, 1);
    }

    public PlayerTwoControls(PlayerCharacter player, int controllerIndex){
        super(player, controllerIndex);
        this.player = player;
        lastDirection.setX(-1);
    }
    @Override
    public void update(double dt){
        Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
        //adjust velocity gradually towards the target
        if (velocity.getX() < targetVelocityX) {
            velocity.setX(Math.min(velocity.getX() + acceleration * (float) dt , targetVelocityX));
        } else if (velocity.getX() > targetVelocityX) {
            velocity.setX(Math.max(velocity.getX() - acceleration * (float) dt , targetVelocityX));
        }

        ControllerState state = controllerManager.getState(controllerIndex);
        
        boolean jumpPressed = keyLisentner.isKeyPressed(KeyEvent.VK_UP) || state.a;
        boolean leftPressed = keyLisentner.isKeyPressed(KeyEvent.VK_LEFT) || state.dpadLeft || state.leftStickX < -0.5f;
        boolean rightPressed = keyLisentner.isKeyPressed(KeyEvent.VK_RIGHT) || state.dpadRight || state.leftStickX > 0.5f;
        boolean firePressed = keyLisentner.isKeyPressed(KeyEvent.VK_NUMPAD1) || state.x || state.rightTrigger > 0.5f;

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
        } else {
            stop();
        }
        if (firePressed && player.getAliveStatus()){
            shoot.fire(player);
        }
    }
}







