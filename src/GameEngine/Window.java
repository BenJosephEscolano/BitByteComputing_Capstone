package GameEngine;

import Util.Constants;
import Util.SceneCode;
import Util.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * This class handles the creation of the window and the game loop (update-draw cycle).
 * This class also has methods that dictate which scene it is displaying
 */
public class Window extends JFrame implements Runnable {
    private static Window window;
    private boolean isRunning = true;
    private final ML mouseListener = new ML();
    private final KL keyListener = new KL();
    private Scene currentScene;
    private Image doubleBufferImage = null;
    private Graphics doubleBufferGraphics = null;
    public boolean isInEditor = true; // This is the only field that I am contemplating of removing

    private Window(){
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
    /*
    The singleton design pattern
     */
    public static Window getWindow(){
        if (Window.window == null){
            Window.window = new Window();
        }
        return Window.window;
    }
    /*
    The run method is the blood of the program one isRunning is change to false, the window and program
    terminates. Whatever you do please don't use Window.getWindow().run() it will crash the problem and
    no I can't change the access modifier to private because Runnable requires the run method to be public.
     */
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
            dispose();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void update(double dt){
        currentScene.update(dt);
        draw(getGraphics());
    }

    public void close(){
        isRunning = false;
    }

    public void init(){
        changeScene(SceneCode.LevelEditor);
    }


    /*
    The draw method is in charge of drawing elements to the window. Specifically the draw and renderOffScreen
    method employs a double buffer technique were renderOffScreen draws on an image called doubleBufferImage
    that will display once it finishes drawing all the related elements of the scene.
     */
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
    /*
    The changeScene method is in charge of switching between scenes
     */
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

    public KL getKeyListener(){
        return keyListener;
    }
}
