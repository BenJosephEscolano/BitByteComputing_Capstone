package GameEngine;

import DataStructure.AssetPool;
import DataStructure.Transform;
import Util.SceneCode;
import Util.Vector;
import Component.Button;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

public class SplashScene extends Scene{
    private GameObject background;
    private GameObject startbtn;
    private GameObject exitbtn;
    private ML mouse;

    public SplashScene(String name) {
        super(name);
        this.background = new GameObject("", new Transform(new Vector()));
        this.startbtn = new GameObject("", new Transform(new Vector()));
        this.exitbtn = new GameObject("", new Transform(new Vector()));
        this.mouse = Window.getMouseListener();
    }

    @Override
    public void init() {
        background.addComponent(AssetPool.getSprite("assets/SplashScreen/SplashScreen_background_1.png"));
        addToBackground(background);
        startbtn.addComponent(new Button(200.0f, 100.0f));
        startbtn.setPosition(new Vector(530,475));
        exitbtn.addComponent(new Button(200.0f, 100.0f));
        exitbtn.setPosition(new Vector(530, 620));
        Sound.getInstance().loadMusic("assets/Music/music_splash_screen.wav");
        Sound.getInstance().playMusic();
    }

    @Override
    public void update(double dt) {
        if (mouse.isMousePressed()
                && mouse.getX() > startbtn.getX()
                && mouse.getX() < startbtn.getX() + startbtn.getComponent(Button.class).getWidth()
                && mouse.getY() > startbtn.getY()
                && mouse.getY() < startbtn.getY()+ startbtn.getComponent(Button.class).getHeight()
            ){
            Window.changeScene(SceneCode.CharacterSelection);
        }

        if (mouse.isMousePressed()
                && mouse.getX() > exitbtn.getX()
                && mouse.getX() < exitbtn.getX() + exitbtn.getComponent(Button.class).getWidth()
                && mouse.getY() > exitbtn.getY()
                && mouse.getY() < exitbtn.getY()+ exitbtn.getComponent(Button.class).getHeight()
        ){
            Window.getWindow().close();
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        background.draw(g2);
        startbtn.draw(g2);
        exitbtn.draw(g2);
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
