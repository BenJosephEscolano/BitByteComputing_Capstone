package UI;

import Component.*;
import GameEngine.*;
import GameEngine.Component;
import GameEngine.Window;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuItem extends Component {
    private int x, y, width, height;
    private Sprite buttonSprite, hoverSprite, myImage;
    private boolean isSelected;

    public MenuItem(int x, int y, int width, int height, Sprite buttonSprite, Sprite hoverSprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonSprite = buttonSprite;
        this.hoverSprite = hoverSprite;
        this.isSelected = false;
    }

    @Override
    public void start(){
        myImage = getGameObject().getComponent(Sprite.class);
    }

    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(this.buttonSprite.getImage(), this.x, this.y, this.width, this.height, null);
        g2.drawImage(this.myImage.getImage(), this.x, this.y, myImage.getWidth(), myImage.getHeight(), null);
        if (isSelected){
            g2.drawImage(this.hoverSprite.getImage(), this.x, this.y, this.width, this.height, null);
        }
    }

    @Override
    public void update(double dt){
        ML mouseListener = Window.getWindow().getMouseListener();
        if (!isSelected &&
                mouseListener.getX() > this.x && mouseListener.getX() < this.x + this.width &&
                mouseListener.getY() > this.y && mouseListener.getY() < this.y + this.height){
            if (mouseListener.isMousePressed() && mouseListener.getMouseButton() == MouseEvent.BUTTON1){
                GameObject obj = getGameObject().copy();
                obj.removeComponent(MenuItem.class);
                LevelEditorScene scene = (LevelEditorScene) Window.getWindow().getScene();

                SnapToGrid snapToGrid = scene.getMouseCursor().getComponent(SnapToGrid.class);
                obj.addComponent(snapToGrid);
                scene.setMouseCursor(obj);
                isSelected = true;
            }
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public Component copy() {
        return null;
    }
}
