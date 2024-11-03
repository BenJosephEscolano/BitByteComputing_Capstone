package GameEngine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KL extends KeyAdapter implements KeyListener {
    private boolean[] keys;

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

    public boolean isKeyPressed(int keyCode){
        return keys[keyCode];
    }
}
