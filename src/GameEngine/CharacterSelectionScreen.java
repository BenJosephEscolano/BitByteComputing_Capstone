package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Component.Button;
import Component.Player;
import Component.SpriteSheet;
import Component.Sprite;
import Component.PlayerOneControls;
import Component.PlayerTwoControls;
import Component.Gun;

import Util.*;

import java.awt.*;

public class CharacterSelectionScreen extends Scene{
    private GameObject background;
    private GameObject playbtn;
    private GameObject p1bdybtnleft, p1bdybtnright, p1eyebtnleft, p1eyebtnright, p1mthbtnleft, p1mthbtnright, p1gunbtnleft, p1gunbtnright;
    private GameObject p2bdybtnleft, p2bdybtnright, p2eyebtnleft, p2eyebtnright, p2mthbtnleft, p2mthbtnright, p2gunbtnleft, p2gunbtnright;
    private GameObject player1, player2;
    private GameObject color1, color2;
    private GameObject gun1, gun2;
    private Player playerComp1, playerComp2;
    private int currmouth1, currbody1, curreye1, currgun1;
    private int currmouth2, currbody2, curreye2, currgun2;
    private ML mouse;
    private Timer mousebuffer;

    public CharacterSelectionScreen(String name) {
        super(name);
        mouse = Window.getMouseListener();
        this.background = new GameObject("", new Transform(new Vector()));
        this.p1bdybtnleft = new GameObject("", new Transform(new Vector(165, 230)));
        this.p1bdybtnright = new GameObject("", new Transform(new Vector(545, 230)));

        this.p1eyebtnleft = new GameObject("", new Transform(new Vector(165,303)));
        this.p1eyebtnright = new GameObject("", new Transform(new Vector(545, 303)));

        this.p1mthbtnleft = new GameObject("", new Transform(new Vector(165, 376)));
        this.p1mthbtnright = new GameObject("", new Transform(new Vector(545, 376)));

        this.p1gunbtnleft = new GameObject("", new Transform(new Vector(165, 610)));
        this.p1gunbtnright = new GameObject("", new Transform(new Vector(545, 610)));

        this.p2bdybtnleft = new GameObject("", new Transform(new Vector(660, 230)));
        this.p2bdybtnright = new GameObject("", new Transform(new Vector(1040, 230)));

        this.p2eyebtnleft = new GameObject("", new Transform(new Vector(660,303)));
        this.p2eyebtnright = new GameObject("", new Transform(new Vector(1040, 303)));

        this.p2mthbtnleft = new GameObject("", new Transform(new Vector(660, 376)));
        this.p2mthbtnright = new GameObject("", new Transform(new Vector(1040, 376)));

        this.p2gunbtnleft = new GameObject("", new Transform(new Vector(660, 610)));
        this.p2gunbtnright = new GameObject("", new Transform(new Vector(1040, 610)));

        this.playbtn = new GameObject("", new Transform(new Vector(1090, 0)));

        this.player1 = new GameObject("", new Transform(new Vector(322,240)));
        this.player2 = new GameObject("", new Transform(new Vector(817,240)));

        this.color1 = new GameObject("", new Transform(new Vector(351, 388)));
        this.color2 = new GameObject("", new Transform(new Vector(845, 388)));

        this.gun1 = new GameObject("", new Transform(new Vector(255, 577)));
        this.gun2 = new GameObject("", new Transform(new Vector(750, 577)));
        this.currbody1 = 0;
        this.currbody2 = 2;
        this.curreye1 = 0;
        this.curreye2 = 2;
        this.currmouth1 = 0;
        this.currmouth2 = 2;
        this.currgun1 = 1;
        this.currgun2 = 2;
        this.mousebuffer = new Timer(0.16f);
    }

    @Override
    public void init() {
        loadAssets();
        background.addComponent(AssetPool.getSprite("assets/CharacterSelection/charcter_selection_2.png"));
        p1bdybtnleft.addComponent(new Button(35,63));
        p1bdybtnright.addComponent(new Button(35, 63));
        p1eyebtnleft.addComponent(new Button(35, 63));
        p1eyebtnright.addComponent(new Button(35, 63));
        p1mthbtnleft.addComponent(new Button(35, 63));
        p1mthbtnright.addComponent(new Button(35, 63));
        p1gunbtnleft.addComponent(new Button(35, 63));
        p1gunbtnright.addComponent(new Button(35, 63));
        p2bdybtnleft.addComponent(new Button(35,63));
        p2bdybtnright.addComponent(new Button(35, 63));
        p2eyebtnleft.addComponent(new Button(35, 63));
        p2eyebtnright.addComponent(new Button(35, 63));
        p2mthbtnleft.addComponent(new Button(35, 63));
        p2mthbtnright.addComponent(new Button(35, 63));
        p2gunbtnleft.addComponent(new Button(35, 63));
        p2gunbtnright.addComponent(new Button(35, 63));
        playbtn.addComponent(new Button(200, 80));
        this.playerComp1 = new Player(
                AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_body.png").getSprite(currbody1),
                AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_eyes.png").getSprite(curreye1),
                AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_mouth.png").getSprite(currmouth1)
        );
        this.playerComp2 = new Player(
                AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_body.png").getSprite(currbody2),
                AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_eyes.png").getSprite(curreye2),
                AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_mouth.png").getSprite(currmouth2)
        );
        player1.addComponent(playerComp1);
        player2.addComponent(playerComp2);
        color1.addComponent(AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_colors.png").getSprite(currbody1));
        color2.addComponent(AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_colors.png").getSprite(currbody2));
        gun1.addComponent(AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_guns.png").getSprite(currgun1).copy());
        gun2.addComponent(AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_guns.png").getSprite(currgun2).copy());
    }


    private void loadAssets(){
        if (!AssetPool.hasSpriteSheet("assets/CharacterSelection/character_selection_body.png")){
            new SpriteSheet("assets/CharacterSelection/character_selection_body.png",
                    110, 119, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/CharacterSelection/character_selection_eyes.png")){
            new SpriteSheet("assets/CharacterSelection/character_selection_eyes.png",
                    110, 119, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/CharacterSelection/character_selection_mouth.png")){
            new SpriteSheet("assets/CharacterSelection/character_selection_mouth.png",
                    110, 119, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/CharacterSelection/character_selection_colors.png")){
            new SpriteSheet("assets/CharacterSelection/character_selection_colors.png",
                    57, 60, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/CharacterSelection/character_selection_guns.png")){
            new SpriteSheet("assets/CharacterSelection/character_selection_guns.png",
                    233, 119, 0, 3, 3);
        }
        if (!AssetPool.hasSpriteSheet("assets/Player/character_body.png")){
            new SpriteSheet("assets/Player/character_body.png",
                    Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/Player/character_eyes.png")){
            new SpriteSheet("assets/Player/character_eyes.png",
                    Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/Player/character_mouth.png")){
            new SpriteSheet("assets/Player/character_mouth.png",
                    Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, 0, 4, 4);
        }
        if (!AssetPool.hasSpriteSheet("assets/Bullet/bullets.png")){
            new SpriteSheet("assets/Bullet/bullets.png",
                    20, 20, 0, 4, 4);
        }
    }

    @Override
    public void update(double dt) {
        mousebuffer.addTime(dt);
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p1bdybtnleft.getX()
                && mouse.getX() < p1bdybtnleft.getX() + p1bdybtnleft.getComponent(Button.class).getWidth()
                && mouse.getY() > p1bdybtnleft.getY()
                && mouse.getY() < p1bdybtnleft.getY()+ p1bdybtnleft.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftbody(-1,1);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p1bdybtnright.getX()
                && mouse.getX() < p1bdybtnright.getX() + p1bdybtnright.getComponent(Button.class).getWidth()
                && mouse.getY() > p1bdybtnright.getY()
                && mouse.getY() < p1bdybtnright.getY()+ p1bdybtnright.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftbody(1,1);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p1eyebtnleft.getX()
                && mouse.getX() < p1eyebtnleft.getX() + p1eyebtnleft.getComponent(Button.class).getWidth()
                && mouse.getY() > p1eyebtnleft.getY()
                && mouse.getY() < p1eyebtnleft.getY()+ p1eyebtnleft.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shifteye(-1,1);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p1eyebtnright.getX()
                && mouse.getX() < p1eyebtnright.getX() + p1eyebtnright.getComponent(Button.class).getWidth()
                && mouse.getY() > p1eyebtnright.getY()
                && mouse.getY() < p1eyebtnright.getY()+ p1eyebtnright.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shifteye(1,1);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p1mthbtnleft.getX()
                && mouse.getX() < p1mthbtnleft.getX() + p1mthbtnleft.getComponent(Button.class).getWidth()
                && mouse.getY() > p1mthbtnleft.getY()
                && mouse.getY() < p1mthbtnleft.getY()+ p1mthbtnleft.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftmouth(-1,1);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p1mthbtnright.getX()
                && mouse.getX() < p1mthbtnright.getX() + p1mthbtnright.getComponent(Button.class).getWidth()
                && mouse.getY() > p1mthbtnright.getY()
                && mouse.getY() < p1mthbtnright.getY()+ p1mthbtnright.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftmouth(1,1);
        }
        
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p2bdybtnleft.getX()
                && mouse.getX() < p2bdybtnleft.getX() + p2bdybtnleft.getComponent(Button.class).getWidth()
                && mouse.getY() > p2bdybtnleft.getY()
                && mouse.getY() < p2bdybtnleft.getY()+ p2bdybtnleft.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftbody(-1,2);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p2bdybtnright.getX()
                && mouse.getX() < p2bdybtnright.getX() + p2bdybtnright.getComponent(Button.class).getWidth()
                && mouse.getY() > p2bdybtnright.getY()
                && mouse.getY() < p2bdybtnright.getY()+ p2bdybtnright.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftbody(1,2);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p2eyebtnleft.getX()
                && mouse.getX() < p2eyebtnleft.getX() + p2eyebtnleft.getComponent(Button.class).getWidth()
                && mouse.getY() > p2eyebtnleft.getY()
                && mouse.getY() < p2eyebtnleft.getY()+ p2eyebtnleft.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shifteye(-1,2);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p2eyebtnright.getX()
                && mouse.getX() < p2eyebtnright.getX() + p2eyebtnright.getComponent(Button.class).getWidth()
                && mouse.getY() > p2eyebtnright.getY()
                && mouse.getY() < p2eyebtnright.getY()+ p2eyebtnright.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shifteye(1,2);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p2mthbtnleft.getX()
                && mouse.getX() < p2mthbtnleft.getX() + p2mthbtnleft.getComponent(Button.class).getWidth()
                && mouse.getY() > p2mthbtnleft.getY()
                && mouse.getY() < p2mthbtnleft.getY()+ p2mthbtnleft.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftmouth(-1,2);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p2mthbtnright.getX()
                && mouse.getX() < p2mthbtnright.getX() + p2mthbtnright.getComponent(Button.class).getWidth()
                && mouse.getY() > p2mthbtnright.getY()
                && mouse.getY() < p2mthbtnright.getY()+ p2mthbtnright.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftmouth(1,2);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p2gunbtnleft.getX()
                && mouse.getX() < p2gunbtnleft.getX() + p2gunbtnleft.getComponent(Button.class).getWidth()
                && mouse.getY() > p2gunbtnleft.getY()
                && mouse.getY() < p2gunbtnleft.getY()+ p2gunbtnleft.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftgun(-1,2);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p2gunbtnright.getX()
                && mouse.getX() < p2gunbtnright.getX() + p2gunbtnright.getComponent(Button.class).getWidth()
                && mouse.getY() > p2gunbtnright.getY()
                && mouse.getY() < p2gunbtnright.getY()+ p2gunbtnright.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftgun(1,2);
        }

        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p1gunbtnleft.getX()
                && mouse.getX() < p1gunbtnleft.getX() + p1gunbtnleft.getComponent(Button.class).getWidth()
                && mouse.getY() > p1gunbtnleft.getY()
                && mouse.getY() < p1gunbtnleft.getY()+ p1gunbtnleft.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftgun(-1,1);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > p1gunbtnright.getX()
                && mouse.getX() < p1gunbtnright.getX() + p1gunbtnright.getComponent(Button.class).getWidth()
                && mouse.getY() > p1gunbtnright.getY()
                && mouse.getY() < p1gunbtnright.getY()+ p1gunbtnright.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            shiftgun(1,1);
        }
        if (mousebuffer.isTime(0) && mouse.isMousePressed()
                && mouse.getX() > playbtn.getX()
                && mouse.getX() < playbtn.getX() + playbtn.getComponent(Button.class).getWidth()
                && mouse.getY() > playbtn.getY()
                && mouse.getY() < playbtn.getY()+ playbtn.getComponent(Button.class).getHeight()
        ){
            mousebuffer.resetTime();
            PlayerCharacter player1 = PlayerCharacter.createPlayer(currbody1, curreye1, currmouth1);
            PlayerOneControls controller1 = new PlayerOneControls(player1);
            player1.addComponent(controller1);

            PlayerCharacter player2 = PlayerCharacter.createPlayer(currbody2, curreye2, currmouth2);
            PlayerTwoControls controller2 = new PlayerTwoControls(player2);
            player2.addComponent(controller2);

            Gun player1Gun = getGun(currgun1);
            Gun player2Gun = getGun(currgun2);
            Sound.getInstance().closeMusic();
            Window.changeScene(SceneCode.Level, player1, player2, player1Gun, player2Gun);
        }
    }

    private Gun getGun(int gunIndex){
        switch (gunIndex){
            case 0:
                return Gun.createGun(GunCode.Sniper);
            case 1:
                return Gun.createGun(GunCode.Rifle);
            case 2:
                return Gun.createGun(GunCode.Pistol);

            default:
                return null;
        }
    }
    
    private void shiftgun(int direction, int player){
        if (player == 1){
            currgun1 = (currgun1 + 3 + direction) % 3;
            gun1.removeComponent(Sprite.class);
            gun1.addComponent(AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_guns.png").getSprite(currgun1).copy());
        } else {
            currgun2 = (currgun2 + 3 + direction) % 3;
            gun2.removeComponent(Sprite.class);
            gun2.addComponent(AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_guns.png").getSprite(currgun2).copy());
        }
    }
    
    private void shiftbody(int direction, int player){
        if (direction > 0){
            swapParts(player, 1,0,0);
        } else {
            swapParts(player, -1, 0, 0);
        }
    }
    
    private void shiftmouth(int direction, int player){
        if (direction > 0){
            swapParts(player, 0,1,0);
        } else {
            swapParts(player, 0, -1, 0);
        }
    }

    private void shifteye(int direction, int player){
        if (direction > 0){
            swapParts(player, 0,0,1);
        } else {
            swapParts(player, 0, 0, -1);
        }
    }
    
    private void swapParts(int player, int body, int mouth, int eye){
        if (player == 1){
            player1.removeComponent(Player.class);
            color1.removeComponent(Sprite.class);
            currbody1 = (body + currbody1 + 4) % 4;
            curreye1 = (eye + curreye1 + 4) % 4;
            currmouth1 = (mouth + currmouth1 + 4) % 4;
            Player p = new Player(
                    AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_body.png").getSprite(currbody1),
                    AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_eyes.png").getSprite(curreye1),
                    AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_mouth.png").getSprite(currmouth1)
            );
            color1.addComponent(AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_colors.png").getSprite(currbody1).copy());
            player1.addComponent(p);
        } else {
            player2.removeComponent(Player.class);
            color2.removeComponent(Sprite.class);
            currbody2 = (body + currbody2 + 4) % 4;
            curreye2 = (eye + curreye2 + 4) % 4;
            currmouth2 = (mouth + currmouth2 + 4) % 4;
            Player p = new Player(
                    AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_body.png").getSprite(currbody2),
                    AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_eyes.png").getSprite(curreye2),
                    AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_mouth.png").getSprite(currmouth2)
            );
            color2.addComponent(AssetPool.getSpriteSheet("assets/CharacterSelection/character_selection_colors.png").getSprite(currbody2).copy());
            player2.addComponent(p);
        }
        
        
    }

    @Override
    public void draw(Graphics2D g2) {
        background.draw(g2);
        p1bdybtnleft.draw(g2);
        p1bdybtnright.draw(g2);
        p1eyebtnleft.draw(g2);
        p1eyebtnright.draw(g2);
        p1mthbtnleft.draw(g2);
        p1mthbtnright.draw(g2);
        p1gunbtnleft.draw(g2);
        p1gunbtnright.draw(g2);
        p2bdybtnleft.draw(g2);
        p2bdybtnright.draw(g2);
        p2eyebtnleft.draw(g2);
        p2eyebtnright.draw(g2);
        p2mthbtnleft.draw(g2);
        p2mthbtnright.draw(g2);
        p2gunbtnleft.draw(g2);
        p2gunbtnright.draw(g2);
        playbtn.draw(g2);
        player1.draw(g2);
        player2.draw(g2);
        color1.draw(g2);
        color2.draw(g2);
        gun1.draw(g2);
        gun2.draw(g2);
    }

    @Override
    public PlayerCharacter getPlayer1() {
        return null;
    }

    @Override
    public PlayerCharacter getPlayer2() {
        return null;
    }
}
