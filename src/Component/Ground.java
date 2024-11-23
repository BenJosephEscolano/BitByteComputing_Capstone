package Component;

import GameEngine.Component;
import GameEngine.GameObject;
import GameEngine.LevelEditorScene;
import GameEngine.LevelScene;
import GameEngine.Window;
import Util.Constants;
import Util.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
* This Component was meant for a former prototype, back when I was trying to make a platformer and
* I need an infinite scrolling ground. We probably don't need this anymore, but if your curious
* It's just a Rectangle class that moves horizontally when the camera moves horizontally
 */
public class Ground extends Component implements Serializable {
    private Rectangle2D ground;
    public Ground(Vector position){
        ground = new Rectangle((int) position.getX(), (int) position.getY(), Constants.SCREEN_WIDTH + 20, Constants.SCREEN_HEIGHT);
    }
    @Override
    public void update(double dt){
        float newX;
        /*
          Looking back what I should have done is to make the update method work regardless of the scene
          it is in
        */
        if (!Window.getWindow().isInEditor){
            LevelScene scene =  (LevelScene) Window.getWindow().getScene();
            GameObject player1 = scene.getPlayer1();
            GameObject player2 = scene.getPlayer2();
            if (player1.getY() + player1.getComponent(BoxBounds.class).getHeight() > getGameObject().getY()){
                player1.setY(getGameObject().getY() - player1.getComponent(BoxBounds.class).getHeight());
                player1.getComponent(PlayerOneControls.class).hasJumped = false;
            }
            if (player2.getY() + player2.getComponent(BoxBounds.class).getHeight() > getGameObject().getY()){
                player2.setY(getGameObject().getY() - player2.getComponent(BoxBounds.class).getHeight());
                player2.getComponent(PlayerOneControls.class).hasJumped = false;
            }
            /*if (player1.getComponent(BoxBounds.class).getBoundbox().intersects(ground)){
                player1.setY(getGameObject().getY() - player1.getComponent(BoxBounds.class).getHeight());
                //player1.setY(getGameObject().getY() - player1.getComponent(BoxBounds.class).getHeight());
                player1.getComponent(PlayerOneControls.class).hasJumped = false;
                System.out.println("is intersecting");
            }*/

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
        g2.setColor(Color.white);
        g2.drawRect((int)getGameObject().getX() , (int) getGameObject().getY(), Constants.SCREEN_WIDTH + 20, Constants.SCREEN_HEIGHT);
    }

    @Override
    public Component copy() {
        return null;
    }
}
