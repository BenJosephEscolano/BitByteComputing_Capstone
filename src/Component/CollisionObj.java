package Component;

import GameEngine.Component;
import GameEngine.GameObject;
import GameEngine.LevelScene;
import GameEngine.Window;

import java.util.List;

public class CollisionObj extends Component {

    @Override
    public void update(double dt){
        /*Collision.checkPlatformPlayerCollision(getGameObject(), Window.getWindow().getScene().getPlayer1());
        Collision.checkPlatformPlayerCollision(getGameObject(), Window.getWindow().getScene().getPlayer2());*/
    }



    @Override
    public Component copy() {
        return null;
    }
}
