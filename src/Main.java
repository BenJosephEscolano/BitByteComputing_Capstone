import GameEngine.Window;

/**
     * This is the Main class, nothing really happens here except for the creation of the
     * window and thread classes. Even though its short this where everything starts
 */
public class Main {
    public static void main(String[] args) {
        //This block of code creates and initialize (init) the window object
        Window window = Window.getWindow();
        window.init();
        //thread.start() calls on the window's run method
        Thread mainThread = new Thread(window);
        mainThread.start();
    }
}