package Component;

import GameEngine.Component;
import GameEngine.GameObject;
import GameEngine.LevelEditorScene;
import GameEngine.LevelScene;
import GameEngine.Window;
import Util.Constants;

import java.awt.*;
import java.io.Serializable;

/**
* This Component was meant for a former prototype, back when I was trying to make a platformer and
* I need an infinite scrolling ground. We probably don't need this anymore, but if your curious
* It's just a Rectangle class that moves horizontally when the camera moves horizontally
 */
public class Ground extends Component implements Serializable {
    @Override
    public void update(double dt){
        float newX;
        /*
          Looking back what I should have done is to make the update method work regardless of the scene
          it is in
        */
        if (!Window.getWindow().isInEditor){
            LevelScene scene =  (LevelScene) Window.getWindow().getScene();
            GameObject player = scene.getPlayer();
            if (player.getY() + player.getComponent(BoxBounds.class).getHeight() > getGameObject().getY()){
                player.setY(getGameObject().getY() - player.getComponent(BoxBounds.class).getHeight());
            }
            newX =(float) scene.getCamera().getX() - 10;
            getGameObject().setX(newX);
        } else {
            LevelEditorScene scene = (LevelEditorScene) Window.getWindow().getScene();
            newX = (float) scene.getCamera().getX() - 10;
            getGameObject().setX(newX);
        }
    }

    @Override
    public void draw(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.drawRect((int)getGameObject().getX() - 10, (int) getGameObject().getY(), Constants.SCREEN_WIDTH + 20, Constants.SCREEN_HEIGHT);
    }

    @Override
    public Component copy() {
        return null;
    }
}
