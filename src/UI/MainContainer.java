package UI;

import DataStructure.Transform;
import GameEngine.Component;
import GameEngine.GameObject;
import Component.SpriteSheet;
import Component.Sprite;
import Util.Constants;
import Util.Vector;

import java.awt.*;
import java.util.ArrayList;

public class MainContainer extends Component {
    private ArrayList<GameObject> menuItems;

    public MainContainer() {
        this.menuItems = new ArrayList<GameObject>();
        init();
    }

    private void init(){
        SpriteSheet groundSprites = new SpriteSheet("assets/groundSprites.png", 42, 42, 2, 6, 12);
        SpriteSheet buttonSprites = new SpriteSheet("assets/buttonSprites.png", 60, 60, 2, 2, 2 );
        for (int i = 0; i < groundSprites.getSize(); i++){
            Sprite currentSprite = groundSprites.getSprite(i);
            int x = Constants.BUTTON_OFFSET_X + (Constants.BUTTON_WIDTH + Constants.BUTTON_SPACING_HZ) * currentSprite.getColumn();
            int y = Constants.BUTTON_OFFSET_Y + (Constants.BUTTON_HEIGHT + Constants.BUTTON_SPACING_VT) * currentSprite.getRow();

            GameObject obj = new GameObject("Generated", new Transform(new Vector(x, y)));
            obj.addComponent(currentSprite.copy());
            MenuItem menuItem = new MenuItem(x, y, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT,
                    buttonSprites.getSprite(0), buttonSprites.getSprite(1));
            obj.addComponent(menuItem);
            menuItems.add(obj);
        }

    }

    @Override
    public void start(){
        for (GameObject g: menuItems){
            for (Component c: g.getAllComponents()){
                c.start();
            }
        }
    }

    @Override
    public void draw(Graphics2D g2){
        for (GameObject g: menuItems){
            g.draw(g2);
        }
    }

    @Override
    public void update(double dt){
        for (GameObject g: menuItems){
            g.update(dt);
        }
    }

    @Override
    public Component copy() {
        return null;
    }
}
