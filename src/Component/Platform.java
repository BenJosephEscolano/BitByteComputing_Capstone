package Component;

import GameEngine.Component;
import GameEngine.GameObject;
import GameEngine.LevelScene;
import GameEngine.Window;
import Util.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.List;

//joseph
public class Platform extends Component implements Serializable {
    private float width, height;
    private Color color;
    private static final float COLLISION_TOLERANCE = 5.0f;

    public Platform(float width, float height) {
        this.width = width;
        this.height = height;
        this.color = Color.DARK_GRAY;
    }

    public float getWidth() {
        return width;
    }
    public float getHeight(){
        return height;
    }

    @Override
    public void update(double dt) {
        /*if (!Window.getWindow().isInEditor) {
            LevelScene scene = (LevelScene) Window.getWindow().getScene();
            GameObject player1 = scene.getPlayer1();
            GameObject player2 = scene.getPlayer2();

            Collision.checkPlatformPlayerCollision(this, player1);
            Collision.checkPlatformPlayerCollision(this, player2);
        }*/
        List<GameObject> activeBodies = Window.getScene().getActiveBodies();
        for (int i = 0; i < activeBodies.size(); i++){
            Collision.checkPlatformPlayerCollision(this, activeBodies.get(i));
        }
    }






    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Component copy() {
        /*Platform platform = new Platform( new Vector(getGameObject().getX(), getGameObject().getY()), width, height);
        platform.setColor(this.color);
        return platform;*/
        return null;
    }
}