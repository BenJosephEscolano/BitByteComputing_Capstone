package Component;

import GameEngine.Component;
import GameEngine.KL;
import GameEngine.Window;

import com.studiohartman.jamepad.ControllerManager;

public abstract class Controls extends Component {
    protected KL keyLisentner = Window.getWindow().getKeyListener();
    protected ControllerManager controllerManager = Window.getControllerManager();

    @Override
    public Component copy() {
        return null;
    }

    public abstract void jump();
    public abstract void moveLeft();
    public abstract void moveRight();

}
