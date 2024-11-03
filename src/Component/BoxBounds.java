package Component;

import GameEngine.Component;

public class BoxBounds extends Component {
    private float width, height;

    public BoxBounds(float width, float height){
        this.width = width;
        this.height = height;
    }
    @Override
    public void update(double dt){

    }

    @Override
    public Component copy() {
        return new BoxBounds(width, height);
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }
}
