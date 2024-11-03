package Component;

import GameEngine.Component;
import GameEngine.GameObject;
import GameEngine.LevelEditorScene;
import GameEngine.LevelScene;
import GameEngine.Window;
import Util.Constants;

import java.awt.*;

public class Ground extends Component {
    @Override
    public void update(double dt){
        float newX;
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
