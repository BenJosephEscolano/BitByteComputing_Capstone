package Component;

import GameEngine.*;
import Util.Vector;

import java.awt.event.KeyEvent;

public class PlayerTwoControls extends PlayerOneControls{
    PlayerCharacter player;
    public PlayerTwoControls(PlayerCharacter player){
        super(player);
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

        if (keyLisentner.isKeyPressed(KeyEvent.VK_UP) && player.getAliveStatus()) {
            jump();
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_LEFT) && player.getAliveStatus()) {
            lastDirection.setX(-1);
            lastDirection.setY(0);
            moveLeft();
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_RIGHT) && player.getAliveStatus()) {
            lastDirection.setX(1);
            lastDirection.setY(0);
            moveRight();
        } else {
            stop();
        }
        if (keyLisentner.isKeyPressed(KeyEvent.VK_NUMPAD1) && player.getAliveStatus()){
            shoot.fire(player);
        }
    }
}







