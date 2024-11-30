package Component;

import DataStructure.AssetPool;
import GameEngine.Bullet;
import GameEngine.GameObject;
import GameEngine.KL;
import GameEngine.Window;
import Util.Timer;
import Util.Vector;

import java.awt.event.KeyEvent;

public class PlayerTwoControls extends PlayerOneControls{
    public PlayerTwoControls(){
        super();
        lastDirection.setX(-1);
    }
    @Override
    public void update(double dt){
        reloadTime.addTime(dt);
        Vector velocity = getGameObject().getComponent(RigidBody.class).velocity;

        //adjust velocity gradually towards the target
        if (velocity.getX() < targetVelocityX) {
            velocity.setX(Math.min(velocity.getX() + acceleration * (float) dt, targetVelocityX));
        } else if (velocity.getX() > targetVelocityX) {
            velocity.setX(Math.max(velocity.getX() - acceleration * (float) dt, targetVelocityX));
        }

        if (keyLisentner.isKeyPressed(KeyEvent.VK_UP)){
            lastDirection.setX(0);
            lastDirection.setY(-1);
            jump();
        }else if (keyLisentner.isKeyPressed(KeyEvent.VK_LEFT)){
            lastDirection.setX(-1);
            lastDirection.setY(0);
            moveLeft();
        }else if (keyLisentner.isKeyPressed(KeyEvent.VK_RIGHT)){
            lastDirection.setX(1);
            lastDirection.setY(0);
            moveRight();
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_S)) {
            lastDirection.setX(0);
            lastDirection.setY(1);
        } else if (keyLisentner.isKeyPressed(KeyEvent.VK_NUMPAD1) && reloadTime.isTime(0)){
            Bullet.spawnBullet(getGameObject());
        } else {
            stop();
        }
    }

}







