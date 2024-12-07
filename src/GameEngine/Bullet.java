package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.Timer;
import Util.Vector;
import Component.Collision;
import Component.BoxBounds;
import Component.PlayerOneControls;
import Component.Sprite;
import java.util.List;

public class Bullet extends GameObject {
    private Vector velocity;  // store the bullet's velocity
    private PlayerCharacter player;
    private Sprite bullet2, bullet3;
    private Timer animationFrame;
    private float bulletSpeed;

    public Bullet(Vector position, Vector velocity, PlayerCharacter player) {
        super("bullet", new Transform(position));
        this.velocity = velocity;
        this.animationFrame = new Timer(0.16f);
        setBulletSprite(player.bulletIndex);
        SoundEffect.getInstance().loadSoundEffect("gun_shot", "assets/Music/music_gun_sfx.wav");
        SoundEffect.getInstance().playSoundEffect("gun_shot");
    }



    public Bullet(PlayerCharacter player, float bulletSpeed){
        super("bullet", new Transform(new Vector()));
        this.player = player;
        this.bulletSpeed = bulletSpeed;
        setBulletSprite(player.bulletIndex);
    }

    @Override
    public void update(double dt) {
        // Update the position based on velocity
        if (animationFrame.isTime(dt)){
            removeComponent(Sprite.class);
            addComponent(bullet2.copy());
        }

        List<GameObject> players = Window.getScene().getActiveBodies();
        List<GameObject> collision = Window.getScene().getStaticBodies();
        Vector position = getPosition();
        position.setX(position.getX() + velocity.getX() * (float) dt);
        position.setY(position.getY() + velocity.getY() * (float) dt);

        for (GameObject c: collision){
            if (Collision.checkCollision(c.getComponent(BoxBounds.class), getComponent(BoxBounds.class))){
                removeComponent(Sprite.class);
                addComponent(bullet3.copy());
                Window.getScene().getGameObjectList().remove(this);
                Window.getScene().removeActiveBody(this);
                Window.getScene().removeProjectileLayer(this);
                System.out.println(collision.size());
            }
        }
        for (int i = 0; i < players.size(); i++){
            if (Collision.checkCollision(players.get(i).getComponent(BoxBounds.class), getComponent(BoxBounds.class)) && this != players.get(i)){
                removeComponent(Sprite.class);
                addComponent(bullet3.copy());
                if (players.get(i) == Window.getScene().getPlayer1()) {
                    Window.getScene().getPlayer1().dead();
                    players.remove(players.get(i));

                } else if (players.get(i) == Window.getScene().getPlayer2()){
                    Window.getScene().getPlayer2().dead();
                    players.remove(players.get(i));
                }
                Window.getScene().removeProjectileLayer(this);
            }
        }

        List<GameObject> projectiles = Window.getScene().getProjectileLayer();
        for (int i = 0; i < projectiles.size(); i++){

            if (Collision.checkCollision(projectiles.get(i).getComponent(BoxBounds.class), getComponent(BoxBounds.class)) && this != projectiles.get(i)){
                removeComponent(Sprite.class);
                addComponent(bullet3.copy());
                Window.getScene().removeProjectileLayer(projectiles.get(i));
                Window.getScene().removeProjectileLayer(this);
                break;
            }
        }
    }

    private void setBulletSprite(int index){
        switch (index){
            case 0:
                this.bullet2 = AssetPool.getSprite("assets/Bullet/bullet_sprites/bullets_green_2.png");
                this.bullet3 = AssetPool.getSprite("assets/Bullet/bullet_sprites/bullets_green_3.png");
                break;
            case 1:
                this.bullet2 = AssetPool.getSprite("assets/Bullet/bullet_sprites/bullets_yellow_2.png");
                this.bullet3 = AssetPool.getSprite("assets/Bullet/bullet_sprites/bullets_yellow_3.png");
                break;
            case 2:
                this.bullet2 = AssetPool.getSprite("assets/Bullet/bullet_sprites/bullets_pink_2.png");
                this.bullet3 = AssetPool.getSprite("assets/Bullet/bullet_sprites/bullets_pink_3.png");
                break;
            case 3:
                this.bullet2 = AssetPool.getSprite("assets/Bullet/bullet_sprites/bullets_blue_2.png");
                this.bullet3 = AssetPool.getSprite("assets/Bullet/bullet_sprites/bullets_blue_3.png");
                break;
        }
    }

    public void spawnBullet() {
        float playerMidX = player.getX() + player.getComponent(BoxBounds.class).getWidth() / 2.0f - 10;
        float playerMidY = player.getY() + player.getComponent(BoxBounds.class).getHeight() / 2.0f - 10;
        Vector lastDirection = player.getComponent(PlayerOneControls.class).getLastDirection();
        //get the last direction from the controls

        if (lastDirection.getX() == -1){
            playerMidX = player.getX() - 42 - 20;
        } else if (lastDirection.getX() == 1){
            playerMidX = player.getX() + player.getComponent(BoxBounds.class).getWidth() + 42;
        } else if (lastDirection.getY() == -1){
            playerMidY = player.getY() - 40;
        } else if (lastDirection.getY() == 1){
            playerMidY = player.getY() + player.getComponent(BoxBounds.class).getHeight() + 40;
        }
        Vector spawnPosition = new Vector(playerMidX, playerMidY);
        // if the last direction is non-zero, spawn a bullet
        if (lastDirection.getX() != 0 || lastDirection.getY() != 0) {
            Vector bulletVelocity = new Vector(lastDirection.getX() * bulletSpeed, lastDirection.getY() * bulletSpeed);
            Bullet newBullet = new Bullet(spawnPosition, bulletVelocity, player);
            newBullet.addComponent(AssetPool.getSpriteSheet("assets/Bullet/bullets.png").getSprite(player.bulletIndex).copy());
            newBullet.addComponent(new BoxBounds(20, 20));
            Window.getScene().setProjectileLayer(newBullet);
        }
    }


}
