package Component;

import GameEngine.Component;
import Util.Constants;
import Util.Vector;

import java.io.Serializable;

public class RigidBody extends Component implements Serializable {
    public Vector velocity;

    public RigidBody(Vector velocity){
        this.velocity = velocity;
    }

    @Override
    public void update(double dt){
        float newY = getGameObject().getY() + (float) (velocity.getY() * dt);
        float newX = getGameObject().getX() + (float) (velocity.getX() * dt);
        getGameObject().setY(newY);
        getGameObject().setX(newX);
        velocity.setY(velocity.getY() + Constants.GRAVITY * (float) dt);

        if (Math.abs(velocity.getY()) > Constants.TERMINAL_VELOCITY){
            newY = Math.signum(velocity.getY()) * Constants.TERMINAL_VELOCITY;
            velocity.setY(newY);
        }
    }

    @Override
    public Component copy() {
        return null;
    }
}
