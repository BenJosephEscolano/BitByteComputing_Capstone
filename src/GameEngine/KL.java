package GameEngine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class is in charge of adding keyboard functionality to our program
 */
public class KL extends KeyAdapter implements KeyListener {
    private final boolean[] keys;

    public KL (){
        keys = new boolean[128];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    /*
    isKeyPressed method is the one that you are usually calling most of the time
     */
    public boolean isKeyPressed(int keyCode){
        return keys[keyCode];
    }
}
