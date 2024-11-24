package Component;

import GameEngine.*;

import GameEngine.Component;
import GameEngine.Window;
import Util.Constants;
import Util.Vector;
import java.util.List;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class SnapToGrid extends Component implements Serializable {
    protected float debounceTime = 0.2f;
    protected float debounceLeft = 0.0f;
    protected int gridWidth, gridHeight;
    protected ML mouseListener = Window.getWindow().getMouseListener();
    protected Camera camera = Window.getWindow().getScene().getCamera();

    public SnapToGrid(int gridWidth, int gridHeight){
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    @Override
    public void update(double dt){
        debounceLeft -= (float) dt;

        if (getGameObject().getComponent(Sprite.class) != null) {
            float x = (float) Math.floor((mouseListener.getX() + camera.getX() + mouseListener.getDX()) / gridWidth);
            float y = (float) Math.floor((mouseListener.getY() + camera.getY() + mouseListener.getDY()) / gridHeight);

            getGameObject().setX(x * gridWidth - (float) camera.getX());
            getGameObject().setY(y * gridHeight - (float) camera.getY());

            if (mouseListener.isMousePressed() && mouseListener.getMouseButton() == MouseEvent.BUTTON1) {
                debounceLeft = debounceTime;
                GameObject object = getGameObject().copy();
                if (!((LevelEditorScene) Window.getWindow().getScene()).eraseMode) {
                    GameObject shadow = getGameObject().copy();
                    shadow.removeComponent(Sprite.class);
                    Sprite hadow = (Sprite) getGameObject().getComponent(Shadow.class).getSubSprite().copy();
                    shadow.addComponent(hadow);
                    object.setPosition(new Vector(x * gridWidth, y * gridHeight));
                    object.addComponent(new BoxBounds(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
                    object.addComponent(new Platform(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
                    shadow.setPosition(new Vector(x * gridWidth - 10, y * gridHeight + 10));
                    Window.getWindow().getScene().setToLayerOne(shadow);
                    Window.getWindow().getScene().setToLayerTwo(object);
                } else {
                    System.out.println("erase");
                    Window.getWindow().getScene().erase(object);
                }
                System.out.println("GameObjectList: " + Window.getWindow().getScene().getGameObjectList().size());
                System.out.println("Layer 1: " + Window.getWindow().getScene().getRenderer(1).getRenderList().size());
                System.out.println("Layer 2: " + Window.getWindow().getScene().getRenderer(2).getRenderList().size());
            }
        }
    }

    @Override
    public void draw(Graphics2D g2){
        Sprite sprite = getGameObject().getComponent(Sprite.class);
        if (sprite != null){
            float alpha = 0.5f;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            //g2.setComposite(ac);
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
