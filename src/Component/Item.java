package Component;

import GameEngine.CollisionLayer;
import GameEngine.Component;
import GameEngine.PlayerCharacter;
import Util.Vector;

public class Item extends Component {
    PlayerCharacter owner;

    public Item(PlayerCharacter owner){
        this.owner = owner;
    }

    @Override
    public void update(double dt){
        Vector direction = owner.getComponent(PlayerOneControls.class).lastDirection;
        if (direction.getX() == 1){
            getGameObject().setX(owner.getX() + owner.getComponent(BoxBounds.class).getWidth());
            getGameObject().setScale(1,1);
        } else {
            getGameObject().setX(owner.getX() - getGameObject().getComponent(Sprite.class).getWidth());
            getGameObject().setScale(-1, 1);
        }
        getGameObject().setY(owner.getY());
    }

    @Override
    public Component copy() {
        return null;
    }
}
