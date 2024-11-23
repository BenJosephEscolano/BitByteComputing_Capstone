package Component;

import DataStructure.AssetPool;
import DataStructure.Transform;
import GameEngine.Component;
import GameEngine.GameObject;
import Util.Constants;
import Util.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Player extends Component implements Serializable {
    Sprite layerOne, layerTwo, layerThree;
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

    public static GameObject createPlayer(int layerOneIndex, int layerTwoIndex, int layerThreeIndex){
        Sprite body = AssetPool.getSpriteSheet("assets/character_body.png").getSprite(layerOneIndex);
        Sprite eyes = AssetPool.getSpriteSheet("assets/character_eyes.png").getSprite(layerTwoIndex);
        Sprite mouth = AssetPool.getSpriteSheet("assets/character_mouth.png").getSprite(layerThreeIndex);
        Player playerSprites = new Player(body, eyes, mouth);
        GameObject player = new GameObject("", new Transform(new Vector()));
        player.addComponent(playerSprites);
        player.addComponent(new RigidBody(new Vector()));
        player.addComponent(new BoxBounds(body.getWidth(), body.getHeight()));
        player.getComponent(BoxBounds.class).init();
        return player;
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
