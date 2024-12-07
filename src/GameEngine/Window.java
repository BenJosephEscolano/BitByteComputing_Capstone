package GameEngine;

import Util.Constants;
import Util.SceneCode;
import Util.Time;
import Component.Gun;

import javax.swing.*;
import java.awt.*;


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
    private boolean play = true;

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
                    if (play){
                        update(deltaTime);
                    } else {
                        update(0);
                    }
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

    public void pause(){
        play = false;
    }

    public boolean isPause(){
        return !play;
    }

    public void play(){
        play = true;
    }

    public void init(){
        changeScene(SceneCode.SplashScreen);
    }

    /*
    The draw method is in charge of drawing elements to the window. Specifically the draw and renderOffScreen
    method employs a double buffer technique were renderOffScreen draws on an image called doubleBufferImage
    that will display once it finishes drawing all the related elements of the scene.
     */
    private void draw(Graphics g){
        if (doubleBufferImage == null){
            doubleBufferImage = createImage(getWidth(), getHeight());
            doubleBufferGraphics = doubleBufferImage.getGraphics();
        }
        renderOffScreen(doubleBufferGraphics);
        g.drawImage(doubleBufferImage, 0, 0, getWidth(), getHeight(), null);
    }

    private void renderOffScreen(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        currentScene.draw(g2);
    }
    /*
    The changeScene method is in charge of switching between scenes
     */
    public static void changeScene(SceneCode scene){
        switch (scene){
            case SceneCode.LevelEditor:
                getWindow().currentScene = new LevelEditorScene("Level editor");
                getWindow().currentScene.init();
                break;
            case SceneCode.SplashScreen:
                getWindow().currentScene = new SplashScene("Splash Screen");
                getWindow().currentScene.init();
                break;
            case SceneCode.CharacterSelection:
                getWindow().currentScene = new CharacterSelectionScreen("Character Selection");
                getWindow().currentScene.init();
                break;
        }
    }

    public static void changeScene(SceneCode scene, PlayerCharacter player1, PlayerCharacter player2, Gun gun1, Gun gun2){
        if (scene == SceneCode.Level){
            getWindow().currentScene = new LevelScene("Level", player1, player2, gun1, gun2);
            getWindow().currentScene.init();
        }
    }

    public static void changeScene(SceneCode scene, int player, int color){
        if (scene == SceneCode.WinScreen){
            getWindow().currentScene = new WinScreen("Win Screen", player, color);
            getWindow().currentScene.init();
        }
    }

    public static Scene getScene(){
        return getWindow().currentScene;
    }

    public static ML getMouseListener() {
        return getWindow().mouseListener;
    }

    public static KL getKeyListener(){
        return getWindow().keyListener;
    }
}
