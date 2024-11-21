package Component;

import GameEngine.Component;
import GameEngine.KL;
import GameEngine.Window;

public abstract class Controls extends Component {
    protected KL keyLisentner = Window.getWindow().getKeyListener();

    @Override
    public Component copy() {
        return null;
    }

    public abstract void jump();
    public abstract void moveLeft();
    public abstract void moveRight();
    //public abstract void moveRightSlow();
    public abstract void moveDown();
}
