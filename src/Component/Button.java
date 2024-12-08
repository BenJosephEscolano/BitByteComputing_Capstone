package Component;

import java.awt.*;

public class Button extends BoxBounds{
    public Button(float width, float height) {
        super(width, height);
    }

    @Override
    public void draw(Graphics2D g2){
        //For debugging purposes
        //g2.setColor(Color.black);
        //g2.drawRect((int)getGameObject().getX(), (int)getGameObject().getY(), (int)getWidth(), (int)getHeight());
    }
}
