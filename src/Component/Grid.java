package Component;

import GameEngine.Camera;
import GameEngine.Component;
import GameEngine.Window;
import Util.Constants;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;

public class Grid extends Component implements Serializable {

    Camera camera;
    public int gridWidth, gridHeight;
    public int gridColumns, gridRows;

    public Grid(){
        this.camera = Window.getScene().getCamera();
        this.gridHeight = Constants.TILE_HEIGHT;
        this.gridWidth = Constants.TILE_WIDTH;
        this.gridColumns = 31;
        this.gridRows = 18;
    }

    @Override
    public void draw(Graphics2D g2){
        float bottom = (float) Constants.SCREEN_HEIGHT;
        float startX = (float) Math.floor(camera.getX() / gridWidth) * gridWidth - (float) camera.getX();
        float startY = (float) Math.floor(camera.getY() / gridHeight) * gridWidth - (float) camera.getY(); //+ Window.getWindow().getInsets().top;
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(Color.GRAY);
        for (int i = 0; i < gridRows; i++){
            for (int j = 0; j < gridColumns; j++){
                g2.draw(new Line2D.Float(startX, 0, startX, bottom));
                startX += gridWidth;
            }
            if (camera.getY() + startY < Constants.SCREEN_HEIGHT){
                g2.draw(new Line2D.Float(0, startY, Constants.SCREEN_WIDTH, startY));
                startY += gridHeight;
            }
        }
    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public void update(double dt){

    }
}
