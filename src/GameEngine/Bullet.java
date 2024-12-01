package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
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
                Window.getScene().getRenderer(1).unsubmit(this);
                Window.getScene().getGameObjectList().remove(this);
                Window.getScene().removeActiveBody(this);
                System.out.println(collision.size());
            }
        }
        for (int i = 0; i < players.size(); i++){
            if (Collision.checkCollision(players.get(i).getComponent(BoxBounds.class), getComponent(BoxBounds.class)) && this != players.get(i)){
                if (players.get(i) == Window.getScene().getPlayer1()) {
                    Window.getScene().getPlayer1().dead();
                    players.remove(players.get(i));
                } else if (players.get(i) == Window.getScene().getPlayer2()){
                    Window.getScene().getPlayer2().dead();
                    players.remove(players.get(i));
                }
                Window.getScene().getRenderer(1).unsubmit(this);
                Window.getScene().removeActiveBody(this);

            }
        }

        List<GameObject> projectiles = Window.getScene().getProjectileLayer();
        for (int i = 0; i < projectiles.size(); i++){
            if (Collision.checkCollision(projectiles.get(i).getComponent(BoxBounds.class), getComponent(BoxBounds.class)) && this != projectiles.get(i)){
                Window.getScene().removeProjectileLayer(projectiles.get(i));
                Window.getScene().removeProjectileLayer(this);
                break;
            }
        }
    }

    public static void spawnBullet(PlayerCharacter player) {
        float playerMidX = player.getX() + player.getComponent(BoxBounds.class).getWidth() / 2.0f -10;
        float playerMidY = player.getY() + player.getComponent(BoxBounds.class).getHeight() / 2.0f - 10;
        Vector lastDirection = player.getComponent(PlayerOneControls.class).getLastDirection();
        System.out.println("Last Direction: X: " + lastDirection.getX() + " Y: " + lastDirection.getY());
        //get the last direction from the controls

        if (lastDirection.getX() == -1){
            playerMidX = player.getX() - 40;
        } else if (lastDirection.getX() == 1){
            playerMidX = player.getX() + player.getComponent(BoxBounds.class).getWidth() + 40;
        } else if (lastDirection.getY() == -1){
            playerMidY = player.getY() - 40;
        } else if (lastDirection.getY() == 1){
            playerMidY = player.getY() + player.getComponent(BoxBounds.class).getHeight() + 40;
        }
        Vector spawnPosition = new Vector(playerMidX, playerMidY);
        System.out.println("Player position: X: " + player.getX() + " Y: " + player.getY());
        System.out.println("Bullet Spawn position: X: " + spawnPosition.getX() + " Y: " + spawnPosition.getY());
        // if the last direction is non-zero, spawn a bullet
        if (lastDirection.getX() != 0 || lastDirection.getY() != 0) {
            Vector bulletVelocity = new Vector(lastDirection.getX() * 500.0f, lastDirection.getY() * 500.0f);
            Bullet newBullet = new Bullet(spawnPosition, bulletVelocity);
            newBullet.addComponent(AssetPool.getSpriteSheet("assets/Bullet/bullets.png").getSprite(player.bulletIndex).copy());
            newBullet.addComponent(new BoxBounds(20, 20));
            System.out.println("Renderer size (before): " + Window.getScene().getRenderer(2).getRenderList().size());
            //Window.getScene().setActiveBodies(newBullet, 1);//add bullet to the scene
            Window.getScene().setProjectileLayer(newBullet);
            System.out.println("Renderer size (after): " + Window.getScene().getRenderer(2).getRenderList().size());
        }
    }


}
