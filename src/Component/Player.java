package Component;

import DataStructure.AssetPool;
import DataStructure.Transform;
import GameEngine.Component;
import GameEngine.GameObject;
import GameEngine.Window;
import Util.Constants;
import Util.Timer;
import Util.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player extends Component implements Serializable {
    private Sprite layerOne, layerTwo, layerThree;
    private final int width, height;

    public Player(Sprite layerOne,
                  Sprite layerTwo,
                  Sprite layerThree
                  ){
        this.layerOne = layerOne;
        this.layerTwo = layerTwo;
        this.layerThree = layerThree;
        this.width = Constants.PLAYER_WIDTH;
        this.height = Constants.PLAYER_HEIGHT;
    }

    @Override
    public void draw(Graphics2D g2){
        AffineTransform transform = new AffineTransform();
        transform.setToIdentity();
        transform.translate(getGameObject().getX(), getGameObject().getY());
        transform.rotate(getGameObject().getVectorRotation(),
                width * getGameObject().getScale().getX() /2.0,
                height * getGameObject().getScale().getX() /2.0);
        transform.scale(getGameObject().getScale().getX(), getGameObject().getScale().getY());
        g2.drawImage(layerOne.getImage(), transform, null);
        g2.drawImage(layerTwo.getImage(), transform, null);
        g2.drawImage(layerThree.getImage(), transform, null);
    }

    @Override
    public Component copy() {
        return null;
    }

}
