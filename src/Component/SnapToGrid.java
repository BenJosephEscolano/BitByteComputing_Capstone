package Component;

import GameEngine.Camera;
import GameEngine.Window;
import GameEngine.Component;
import GameEngine.GameObject;
import GameEngine.ML;

import Util.Constants;
import Util.Vector;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SnapToGrid extends Component {
    private float debounceTime = 0.2f;
    private float debounceLeft = 0.0f;
    private int gridWidth, gridHeight;

    public SnapToGrid(int gridWidth, int gridHeight){
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    @Override
    public void update(double dt){
        debounceLeft -= (float) dt;
        ML mouseListener = Window.getWindow().getMouseListener();
        Camera camera = Window.getWindow().getScene().getCamera();
        if (getGameObject().getComponent(Sprite.class) != null) {
            float x = (float) Math.floor((mouseListener.getX() + camera.getX() + mouseListener.getDX()) / gridWidth);
            float y = (float) Math.floor((mouseListener.getY() + camera.getY() + mouseListener.getDY()) / gridHeight);

            getGameObject().setX(x * gridWidth - (float)camera.getX());
            getGameObject().setY(y * gridHeight - (float)camera.getY());

            if (mouseListener.getY() < Constants.BUTTON_OFFSET_Y && mouseListener.isMousePressed() && mouseListener.getMouseButton() == MouseEvent.BUTTON1 && debounceLeft < 0){
                debounceLeft = debounceTime;
                GameObject object = getGameObject().copy();
                object.setPosition(new Vector(x * gridWidth, y * gridHeight));
                Window.getWindow().getScene().addGameObject(object);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2){
        Sprite sprite = getGameObject().getComponent(Sprite.class);
        if (sprite != null){
            float alpha = 0.5f;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2.setComposite(ac);
            g2.drawImage(sprite.getImage(), (int) getGameObject().getX(), (int) getGameObject().getY()
            , sprite.getWidth(), sprite.getHeight(), null);
            alpha = 1.0f;
            ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2.setComposite(ac);
        }
    }

    @Override
    public Component copy() {
        return null;
    }
}
