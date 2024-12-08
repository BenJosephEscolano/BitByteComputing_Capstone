package Component;

import DataStructure.AssetPool;
import DataStructure.Transform;
import GameEngine.Bullet;
import GameEngine.GameObject;
import GameEngine.PlayerCharacter;
import Util.GunCode;
import Util.Timer;
import Util.Vector;

import java.util.ArrayList;
import java.util.List;

// testing gun class but idk how

public abstract class Gun extends GameObject{
    protected Timer fireRate;
    protected Timer reloadTime;
    protected float bulletSpeed;
    protected int ammo;
    protected int currAmmo;
    private List<Sprite> loadedGun;
    private List<Sprite> unLoadedGun;
    private PlayerCharacter owner;

    public Gun(int ammo, float fireRate, float bulletSpeed, float reloadSpeed, String l1, String l2, String ul1, String ul2) {
        super("", new Transform(new Vector()));
        this.loadedGun = new ArrayList<>();
        this.unLoadedGun = new ArrayList<>();
        loadedGun.add(AssetPool.getSprite(l1));
        loadedGun.add(AssetPool.getSprite(l2));
        unLoadedGun.add(AssetPool.getSprite(ul1));
        unLoadedGun.add(AssetPool.getSprite(ul2));
        this.fireRate = new Timer(fireRate);
        this.reloadTime = new Timer(reloadSpeed);
        this.bulletSpeed = bulletSpeed;
        this.ammo = ammo;
        currAmmo = ammo;
        addComponent(loadedGun.get(0));
    }

    public void update(double dt){
        super.update(dt);
        if (currAmmo == 0 && reloadTime.isTime(dt)){
            currAmmo = ammo;
            reloadTime.resetTime();
        }
        fireRate.addTime(dt);
        if (owner != null){
            Vector direction = owner.getComponent(PlayerOneControls.class).lastDirection;
            if (currAmmo > 0){
                if (direction.getX() == 1){
                    setX(owner.getX() + owner.getComponent(BoxBounds.class).getWidth());
                    setScale(1,1);
                    removeComponent(Sprite.class);
                    addComponent(loadedGun.get(0).copy());
                } else  {
                    setX(owner.getX() - getComponent(Sprite.class).getWidth());
                    setScale(-1, 1);
                    removeComponent(Sprite.class);
                    addComponent(loadedGun.get(1).copy());
                }
            } else {
                if (direction.getX() == 1){
                    setX(owner.getX() + owner.getComponent(BoxBounds.class).getWidth());
                    setScale(1,1);
                    removeComponent(Sprite.class);
                    addComponent(unLoadedGun.get(0).copy());
                } else  {
                    setX(owner.getX() - getComponent(Sprite.class).getWidth());
                    setScale(-1, 1);
                    removeComponent(Sprite.class);
                    addComponent(unLoadedGun.get(1).copy());
                }
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
            case Sniper:
                Gun sniper = new Sniper();
                return sniper;
            default:
                return null;
        }
    }

    public void setOwner(PlayerCharacter owner){
        this.owner = owner;
        owner.getComponent(PlayerOneControls.class).setCommand(this);
    }

    public void fire(PlayerCharacter owner){
        if (fireRate.isTime(0) && currAmmo > 0){
            fireRate.resetTime();
            Bullet newBullet = new Bullet(owner, bulletSpeed);
            newBullet.spawnBullet();
            currAmmo--;
        }
    }

    public void resetAmmo(){
        //This function is used to reload players ammo when they respawn
        if (currAmmo > 0){
            currAmmo = ammo;
        }
    }


    //pistol fires one bullet everytime you press space
    public static class Pistol extends Gun {
        public Pistol() {
            super(3,0.25f, 1000.0f, 1.0f,
                    "assets/Gun/guns/guns_pistol_side_right.png",
                    "assets/Gun/guns/guns_pistol_side_left.png",
                    "assets/Gun/guns/guns_pistol_side_right_reload.png",
                    "assets/Gun/guns/guns_pistol_side_left_reload.png"); // 0.5 firing interval and 1000 units bullet speed
            addComponent(new RigidBody(new Vector()));
        }

    }

    public static class Sniper extends Gun {
        public Sniper() {
            super(3,0.5f, 1500.0f, 2.0f,
                    "assets/Gun/guns/guns_sniper_side_right.png",
                    "assets/Gun/guns/guns_sniper_side_left.png",
                    "assets/Gun/guns/guns_sniper_side_right_reload.png",
                    "assets/Gun/guns/guns_sniper_side_left_reload.png"); // 0.5 firing interval and 1000 units bullet speed
            addComponent(new RigidBody(new Vector()));
        }

    }


    // an AR first continuously while holding space
    public static class AutomaticRifle extends Gun {
        public AutomaticRifle() {
            super(5, 0.1f, 500.0f, 3.0f,
                    "assets/Gun/guns/guns_rifle_side_right.png",
                    "assets/Gun/guns/guns_rifle_side_left.png",
                    "assets/Gun/guns/guns_rifle_side_right_reload.png",
                    "assets/Gun/guns/guns_rifle_side_left_reload.png"); // 0.1 firing interval and 1000 units bullet speed
            addComponent(new RigidBody(new Vector()));
        }

    }
}
