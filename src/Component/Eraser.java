package Component;

import GameEngine.*;
import Util.Constants;
import Util.Vector;

import java.awt.event.MouseEvent;

public class Eraser extends Component{



    /*public void update(double dt) {
        float x = (float) Math.floor((mouseListener.getX() + camera.getX() + mouseListener.getDX()) / gridWidth);
        float y = (float) Math.floor((mouseListener.getY() + camera.getY() + mouseListener.getDY()) / gridHeight);

        getGameObject().setX(x * gridWidth - (float) camera.getX());
        getGameObject().setY(y * gridHeight - (float) camera.getY());
        if (mouseListener.isMousePressed() && mouseListener.getMouseButton() == MouseEvent.BUTTON1 && debounceLeft < 0){
            debounceLeft = debounceTime;
            System.out.println("erase");
        }
        //System.out.println("erase");
    }*/

    @Override
    public Component copy() {
        return this;
    }
}
