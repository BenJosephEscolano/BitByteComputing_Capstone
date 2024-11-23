package Component;

import GameEngine.Component;
import GameEngine.GameObject;
import GameEngine.LevelScene;
import GameEngine.Window;
import Util.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
//joseph
public class Platform extends Component implements Serializable {
    private Rectangle2D platform;
    private int width;
    private int height;
    private Color color;
    private static final float COLLISION_TOLERANCE = 5.0f;

    public Platform(Vector position, int width, int height) {
        this.width = width;
        this.height = height;
        this.color = Color.DARK_GRAY;
        platform = new Rectangle((int) position.getX(), (int) position.getY(), width, height);
    }

    public int getWidth() {
        return width;
    }
    public int getHeight(){
        return height;
    }

    @Override
    public void update(double dt) {
        if (!Window.getWindow().isInEditor) {
            LevelScene scene = (LevelScene) Window.getWindow().getScene();
            GameObject player1 = scene.getPlayer1();
            GameObject player2 = scene.getPlayer2();

            Collision.checkPlatformPlayerCollision(this, player1);
            Collision.checkPlatformPlayerCollision(this, player2);
        }
    }




    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect( (int)getGameObject().getX(), (int)getGameObject().getY(), width, height);

        g2.setColor(Color.BLACK);
        g2.drawRect( (int)getGameObject().getX(), (int)getGameObject().getY(), width, height);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Component copy() {
        Platform platform = new Platform( new Vector(getGameObject().getX(), getGameObject().getY()), width, height);
        platform.setColor(this.color);
        return platform;
    }
}