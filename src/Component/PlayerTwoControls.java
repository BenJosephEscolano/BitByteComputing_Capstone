package Component;

import DataStructure.AssetPool;
import GameEngine.*;
import Util.Timer;
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
        //System.out.println(reloadTime);
        Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;
        //adjust velocity gradually towards the target
        if (velocity.getX() < targetVelocityX) {
            velocity.setX(Math.min(velocity.getX() + acceleration * (float) dt , targetVelocityX));
        } else if (velocity.getX() > targetVelocityX) {
            velocity.setX(Math.max(velocity.getX() - acceleration * (float) dt , targetVelocityX));
        }
        if (keyLisentner.isKeyPressed(KeyEvent.VK_DOWN)) {
            //lastDirection.setX(0);
            lastDirection.setY(1);
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_NUMPAD0)) {
            //lastDirection.setX(0);
            lastDirection.setY(-1);
        }
        if (keyLisentner.isKeyPressed(KeyEvent.VK_UP)) {
            //lastDirection.setX(0);
            lastDirection.setY(-1);
            jump();
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_LEFT)) {
            lastDirection.setX(-1);
            lastDirection.setY(0);
            moveLeft();
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_RIGHT)) {
            lastDirection.setX(1);
            lastDirection.setY(0);
            moveRight();
        } else {
            stop();
        }
        if (reloadTime.isTime(dt) && keyLisentner.isKeyPressed(KeyEvent.VK_NUMPAD1)){
            reloadTime.resetTime();
            shoot.execute();
        }
    }

}







