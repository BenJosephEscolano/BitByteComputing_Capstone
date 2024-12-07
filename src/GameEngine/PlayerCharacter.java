package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.Constants;
import Util.Vector;
import Component.Sprite;
import Component.Player;
import Component.RigidBody;
import Component.BoxBounds;
import Component.PlayerOneControls;
import Component.Item;
import Component.Gun;

public class PlayerCharacter extends GameObject{
    private boolean aliveStatus;
    public int bulletIndex;
    public Gun weapon;

    private PlayerCharacter(int bulletIndex) {
        super("player", new Transform(new Vector()));
        this.aliveStatus = true;
        this.bulletIndex = bulletIndex;
    }

    public static PlayerCharacter createPlayer(int layerOneIndex, int layerTwoIndex, int layerThreeIndex){
        Sprite body = AssetPool.getSpriteSheet("assets/Player/character_body.png").getSprite(layerOneIndex);
        Sprite eyes = AssetPool.getSpriteSheet("assets/Player/character_eyes.png").getSprite(layerTwoIndex);
        Sprite mouth = AssetPool.getSpriteSheet("assets/Player/character_mouth.png").getSprite(layerThreeIndex);
        Player playerSprites = new Player(body, eyes, mouth);
        PlayerCharacter player = new PlayerCharacter(layerOneIndex);
        player.addComponent(playerSprites);
        player.addComponent(new RigidBody(new Vector()));
        player.addComponent(new BoxBounds(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT));
        player.getComponent(BoxBounds.class).init();
        Window.getScene().addToActiveBody(player);
        return player;
    }

    public void dead(){
        GameObject death = new GameObject("death", new Transform(new Vector()));
        death.addComponent(AssetPool.getSprite("assets/Player/character_death/character_death_2.png"));
        death.setPosition(getPosition().copy());
        death.setX(getX()-40);
        death.setY(getY()-60);
        Window.getScene().addToLayerTwo(death);
        aliveStatus = false;
    }

    public void revive(){
        aliveStatus = true;
    }

    public boolean getAliveStatus(){
        return aliveStatus;
    }

    public void setWeapon(Gun weapon){
        this.weapon = weapon;
    }

    public Gun getWeapon(){
        return weapon;
    }

}
