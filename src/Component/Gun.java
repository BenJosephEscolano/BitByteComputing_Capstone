package Component;

import DataStructure.AssetPool;
import DataStructure.Transform;
import GameEngine.Bullet;
import GameEngine.GameObject;
import GameEngine.PlayerCharacter;
import Util.GunCode;
import Util.Vector;

import java.util.ArrayList;
import java.util.List;

// testing gun class but idk how

public abstract class Gun extends GameObject{
    protected float fireRate;
    protected float bulletSpeed;
    private double lastFireTime;
    private List<Sprite> loadedGun;
    private List<Sprite> unLoadedGun;
    private PlayerCharacter owner;

    public Gun(float fireRate, float bulletSpeed, String l1, String l2, String ul1, String ul2) {
        super("", new Transform(new Vector()));
        this.loadedGun = new ArrayList<>();
        this.unLoadedGun = new ArrayList<>();
        loadedGun.add(AssetPool.getSprite(l1));
        loadedGun.add(AssetPool.getSprite(l2));
        unLoadedGun.add(AssetPool.getSprite(ul1));
        unLoadedGun.add(AssetPool.getSprite(ul2));
        this.fireRate = fireRate;
        this.bulletSpeed = bulletSpeed;
        this.lastFireTime = 0; // Gun hasn't fired yet
        addComponent(loadedGun.get(0));
    }

    public void update(double dt){
        super.update(dt);
        if (owner != null){
            Vector direction = owner.getComponent(PlayerOneControls.class).lastDirection;
            if (direction.getX() == 1){
                setX(owner.getX() + owner.getComponent(BoxBounds.class).getWidth());
                setScale(1,1);
                removeComponent(Sprite.class);
                addComponent(loadedGun.get(0).copy());
            } else {
                setX(owner.getX() - getComponent(Sprite.class).getWidth());
                setScale(-1, 1);
                removeComponent(Sprite.class);
                addComponent(loadedGun.get(1).copy());
            }
            setY(owner.getY());
        }
    }

    public static Gun createGun(GunCode gunType){
        switch (gunType){
            case Pistol:
                Gun pistol = new Pistol();
                return pistol;
            case Rifle:
                Gun rifle = new AutomaticRifle();
                return rifle;
            default:
                return null;
        }
    }

    public void setOwner(PlayerCharacter owner){
        this.owner = owner;
    }

    // this checks if enough time has passed since the last shot to fire again
    public boolean canFire(double currentTime) {
        return currentTime - lastFireTime >= fireRate;
    }

    //this reset the firing timer after a shot
    public void resetFiringTimer(double currentTime) {
        lastFireTime = currentTime;
    }
    public abstract void fire(Vector position, Vector direction, List<GameObject> gameObjectList, double currentTime);

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public float getFireRate() {
        return fireRate;
    }

    //pistol fires one bullet everytime you press space
    public static class Pistol extends Gun {
        public Pistol() {
            super(0.5f, 1000.0f,
                    "assets/Gun/guns/guns_pistol_side_right.png",
                    "assets/Gun/guns/guns_pistol_side_left.png",
                    "assets/Gun/guns/guns_pistol_side_right_reload.png",
                    "assets/Gun/guns/guns_pistol_side_left_reload.png"); // 0.5 firing interval and 1000 units bullet speed
            addComponent(new RigidBody(new Vector()));
        }

        @Override
        public void fire(Vector position, Vector direction, List<GameObject> gameObjectList, double currentTime) {
            if (canFire(currentTime)) {
                Bullet newBullet = new Bullet(position, direction.copy().multiply(bulletSpeed));
                gameObjectList.add(newBullet);
                resetFiringTimer(currentTime); // reset firing timer
            }
        }
    }

    // an AR first continuously while holding space
    public static class AutomaticRifle extends Gun {
        public AutomaticRifle() {
            super(0.1f, 1500.0f,
                    "assets/Gun/guns/guns_rifle_side_right.png",
                    "assets/Gun/guns/guns_rifle_side_left.png",
                    "assets/Gun/guns/guns_rifle_side_right_reload.png",
                    "assets/Gun/guns/guns_rifle_side_left_reload.png"); // 0.1 firing interval and 1000 units bullet speed
            addComponent(new RigidBody(new Vector()));
        }

        @Override
        public void fire(Vector position, Vector direction, List<GameObject> gameObjectList, double currentTime) {
            if (canFire(currentTime)) {
                Bullet newBullet = new Bullet(position, direction.copy().multiply(bulletSpeed));
                gameObjectList.add(newBullet);
                resetFiringTimer(currentTime);
            }
        }


    }
}
