package Component;

import GameEngine.Bullet;
import GameEngine.GameObject;
import GameEngine.PlayerCharacter;

public class ShootCommand extends Command{
    private PlayerCharacter player;

    public ShootCommand(PlayerCharacter player){
        this.player = player;
    }
    @Override
    public void execute() {
        //player.getWeapon().fire();
        Bullet.spawnBullet(player);
    }
}
