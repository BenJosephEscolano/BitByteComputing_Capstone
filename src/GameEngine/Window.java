package GameEngine;

import Util.Constants;
import Util.SceneCode;
import Util.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Window extends JFrame implements Runnable {
    private static Window window;
    private boolean isRunning = true;
    private final ML mouseListener;
    private final KL keyListener;
    private Scene currentScene;
    private Image doubleBufferImage = null;
    private Graphics doubleBufferGraphics = null;
    public boolean isInEditor = true;

    private Window(){
        this.mouseListener = new ML();
        this.keyListener = new KL();
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.SCREEN_TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.addKeyListener(keyListener);
    }
    public static Window getWindow(){
        if (Window.window == null){
            Window.window = new Window();
        }
        return Window.window;
    }

    @Override
    public void run() {
        double lastFrameTime = 0.0;
        try {
            while(isRunning){
                double time = Time.getTime();
                double deltaTime = time - lastFrameTime;
                lastFrameTime = time;
                update(deltaTime);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        isRunning = false;
    }

    public void init(){
        changeScene(SceneCode.LevelEditor);
    }

    public void update(double dt){
        currentScene.update(dt);
        draw(getGraphics());
    }

    public void draw(Graphics g){
        if (doubleBufferImage == null){
            doubleBufferImage = createImage(getWidth(), getHeight());
            doubleBufferGraphics = doubleBufferImage.getGraphics();
        }
        renderOffScreen(doubleBufferGraphics);
        g.drawImage(doubleBufferImage, 0, 0, getWidth(), getHeight(), null);
    }

    public void renderOffScreen(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        currentScene.draw(g2);
    }

    public void changeScene(SceneCode scene){
        if (scene == SceneCode.Level){
            isInEditor = false;
            currentScene = new LevelScene("Level");
            currentScene.init();
        }

        if (scene == SceneCode.LevelEditor){
            isInEditor = true;
            currentScene = new LevelEditorScene("Level editor");
            currentScene.init();
        }

    }

    public Scene getScene(){
        return currentScene;
    }

    public ML getMouseListener() {
        return mouseListener;
    }
}
