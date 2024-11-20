package GameEngine;

import Util.SceneCode;

public class LevelEditor {
    public static class Main {
        public static void main(String[] args) {
            //This block of code creates and initialize (init) the window object
            Window window = Window.getWindow();
            window.init();
            window.changeScene(SceneCode.LevelEditor);
            //thread.start() calls on the window's run method
            Thread mainThread = new Thread(window);
            mainThread.start();
        }
    }
}


