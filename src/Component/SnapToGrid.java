package Component;

import GameEngine.*;

import GameEngine.Component;
import GameEngine.Window;
import Util.Constants;
import Util.Vector;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class SnapToGrid extends Component implements Serializable {
    protected int gridWidth, gridHeight;
    protected ML mouseListener = Window.getMouseListener();
    protected Camera camera = Window.getScene().getCamera();

    public SnapToGrid(int gridWidth, int gridHeight){
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    @Override
    public void update(double dt){
        if (getGameObject().getComponent(Sprite.class) != null) {
            float x = (float) Math.floor((mouseListener.getX() + camera.getX() + mouseListener.getDX()) / gridWidth);
            float y = (float) Math.floor((mouseListener.getY() + camera.getY() + mouseListener.getDY()) / gridHeight);

            getGameObject().setX(x * gridWidth - (float) camera.getX());
            getGameObject().setY(y * gridHeight - (float) camera.getY());

            if (mouseListener.isMousePressed() && mouseListener.getMouseButton() == MouseEvent.BUTTON1) {
                GameObject object = getGameObject().copy();

                GameObject shadow = getGameObject().copy();
                shadow.removeComponent(Sprite.class);
                Sprite shadowSprite = (Sprite) getGameObject().getComponent(Shadow.class).getSubSprite().copy();
                shadow.addComponent(shadowSprite);
                object.setPosition(new Vector(x * gridWidth, y * gridHeight));
                object.addComponent(new BoxBounds(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
                object.addComponent(new Platform(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
                shadow.setPosition(new Vector(x * gridWidth - 10, y * gridHeight + 10));
                Window.getScene().setToLayerOne(shadow);
                Window.getScene().setToLayerTwo(object);

                System.out.println("GameObjectList: " + Window.getScene().getGameObjectList().size());
                System.out.println("Layer 1: " + Window.getScene().getRenderer(1).getRenderList().size());
                System.out.println("Layer 2: " + Window.getScene().getRenderer(2).getRenderList().size());
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
