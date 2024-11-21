package Component;

import GameEngine.Bullet;
import GameEngine.GameObject;
import Util.Vector;

import java.util.List;

// testing gun class but idk how

public abstract class Gun {
    protected float fireRate;
    protected float bulletSpeed;
    private double lastFireTime;

    public Gun(float fireRate, float bulletSpeed) {
        this.fireRate = fireRate;
        this.bulletSpeed = bulletSpeed;
        this.lastFireTime = 0; // Gun hasn't fired yet
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
            super(0.5f, 1000.0f); // 0.5 firing interval and 1000 units bullet speed
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
            super(0.1f, 1500.0f); // 0.1 firing interval and 1000 units bullet speed
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
