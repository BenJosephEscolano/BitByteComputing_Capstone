package Component;

import GameEngine.KL;
import GameEngine.Window;
import Util.Vector;

import java.awt.event.KeyEvent;

public class PlayerTwoControls extends PlayerOneControls{
    //private KL keyListener = Window.getWindow().getKeyListener();
    @Override
    public void update(double dt){
        if (keyLisentner.isKeyPressed(KeyEvent.VK_UP)){
            jump();
        }else if (keyLisentner.isKeyPressed(KeyEvent.VK_LEFT)){
            moveLeft();
        }else if (keyLisentner.isKeyPressed(KeyEvent.VK_RIGHT)){
            moveRight();
        } else {
            stop();
        }
    }




    }




