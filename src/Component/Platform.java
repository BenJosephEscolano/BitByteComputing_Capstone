package Component;

import GameEngine.Component;
import GameEngine.GameObject;
import GameEngine.Window;
import java.io.Serializable;
import java.util.List;

//joseph
public class Platform extends Component implements Serializable {
    private float width, height;

    public Platform(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }
    public float getHeight(){
        return height;
    }

    @Override
    public void update(double dt) {
        List<GameObject> activeBodies = Window.getScene().getActiveBodies();
        for (int i = 0; i < activeBodies.size(); i++){
            Collision.checkPlatformPlayerCollision(this, activeBodies.get(i));
        }
    }

    @Override
    public Component copy() {
        return null;
    }
}