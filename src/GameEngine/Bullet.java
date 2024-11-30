package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.Timer;
import Util.Vector;
import Component.Collision;
import Component.BoxBounds;
import Component.PlayerOneControls;

import java.util.List;

public class Bullet extends GameObject {
    private Vector velocity;  // store the bullet's velocity


    public Bullet(Vector position, Vector velocity) {
        super("bullet", new Transform(position));
        this.velocity = velocity;
    }

    @Override
    public void update(double dt) {
        // Update the position based on velocity
        List<GameObject> players = Window.getScene().getActiveBodies();
        List<GameObject> collision = Window.getScene().getStaticBodies();
        Vector position = getPosition();
        position.setX(position.getX() + velocity.getX() * (float) dt);
        position.setY(position.getY() + velocity.getY() * (float) dt);

        for (GameObject c: collision){
            if (Collision.checkCollision(c.getComponent(BoxBounds.class), getComponent(BoxBounds.class))){
                Window.getScene().getRenderer(2).unsubmit(this);
                Window.getScene().getGameObjectList().remove(this);
                System.out.println(collision.size());
            }
        }
        for (int i = 0; i < players.size(); i++){
            if (Collision.checkCollision(players.get(i).getComponent(BoxBounds.class), getComponent(BoxBounds.class))){
                Window.getScene().getRenderer(1).unsubmit(players.get(i));
                Window.getScene().getRenderer(2).unsubmit(this);
                Window.getScene().getGameObjectList().remove(this);
                if (players.get(i) == Window.getScene().getPlayer1()) {
                    Window.getScene().player1IsAlive = false;
                } else {
                    Window.getScene().player2IsAlive = false;
                }
                players.remove(players.get(i));
            }
        }
    }

    public static void spawnBullet(GameObject player) {
        float playerMidX = player.getX() + player.getComponent(BoxBounds.class).getWidth() / 2.0f -13;
        float playerMidY = player.getY() + player.getComponent(BoxBounds.class).getHeight() / 2.0f - 13;
        Vector lastDirection = player.getComponent(PlayerOneControls.class).getLastDirection();

        //get the last direction from the controls

        if (lastDirection.getX() == -1){
            playerMidX = player.getX() - 26;
        } else if (lastDirection.getX() == 1){
            playerMidX = player.getX() + player.getComponent(BoxBounds.class).getWidth();
        } else if (lastDirection.getY() == -1){
            playerMidY = player.getY() - 26;
        } else if (lastDirection.getY() == 1){
            playerMidY = player.getY() + player.getComponent(BoxBounds.class).getHeight() + 26;
        }
        Vector spawnPosition = new Vector(playerMidX, playerMidY);
        // if the last direction is non-zero, spawn a bullet
        if (lastDirection.getX() != 0 || lastDirection.getY() != 0) {
            Vector bulletVelocity = new Vector(lastDirection.getX() * 1000.0f, lastDirection.getY() * 1000.0f);
            Bullet newBullet = new Bullet(spawnPosition, bulletVelocity);
            newBullet.addComponent(AssetPool.getSpriteSheet("assets/Bullet/bullets.png").getSprite(0).copy());
            newBullet.addComponent(new BoxBounds(26, 26));
            Window.getScene().addToLayerTwo(newBullet);  //add bullet to the scene
        }
    }


}
