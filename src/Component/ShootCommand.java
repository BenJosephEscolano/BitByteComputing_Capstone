package Component;

import GameEngine.Bullet;
import GameEngine.GameObject;
import GameEngine.PlayerCharacter;

public class ShootCommand extends Command{
    private PlayerCharacter player;
    private Gun gun;

    public ShootCommand(PlayerCharacter player, Gun gun){
        this.player = player;
        this.gun = gun;
    }
    @Override
    public void execute() {
        gun.fire(player);
    }
}
