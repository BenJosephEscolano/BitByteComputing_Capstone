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
        float newY = getGameObject().getX() + (float) (velocity.getX() * dt);
        float newX = getGameObject().getY() + (float) (velocity.getY() * dt);
        getGameObject().setY(newX);
        getGameObject().setX(newY);
        velocity.setY(velocity.getY() + Constants.GRAVITY * (float) dt);

        if (Math.abs(velocity.getY()) > Constants.TERMINAL_VELOCITY){
            newY = Math.signum(velocity.getY()) * Constants.TERMINAL_VELOCITY;
            velocity.setY(newY);
        }
    }

    public void setYVelocity(float y){
        velocity.setY(y);
    }

    @Override
    public Component copy() {
        return null;
    }
}
