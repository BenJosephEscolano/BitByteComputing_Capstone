package Component;

import GameEngine.Component;
import GameEngine.ML;
import GameEngine.Window;

import java.awt.event.MouseEvent;

public class CameraControls extends Component {
    private float prevMx, prevMy;
    private ML mouseListener;

    public CameraControls(){
        this.prevMx = 0.0f;
        this.prevMy = 0.0f;
        this.mouseListener = Window.getWindow().getMouseListener();
    }

    @Override
    public void update(double dt){
        if (mouseListener.isMousePressed() && mouseListener.getMouseButton() == MouseEvent.BUTTON3){
            float dx = (mouseListener.getX() + mouseListener.getDX() - prevMx);
            float dy = (mouseListener.getY() + mouseListener.getDY() - prevMy);
            System.out.println("middle mouse");
            Window.getWindow().getScene().getCamera().setX(
                    (float) (Window.getWindow().getScene().getCamera().getX() - dx)
            );
            Window.getWindow().getScene().getCamera().setY(
                    (float) (Window.getWindow().getScene().getCamera().getY() - dy)
            );
        }
        prevMx = mouseListener.getX() + mouseListener.getDX();
        prevMy = mouseListener.getY() + mouseListener.getDY();
    }

    @Override
    public Component copy() {
        return null;
    }
}
