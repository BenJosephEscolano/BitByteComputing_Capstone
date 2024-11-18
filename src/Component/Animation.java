package Component;

import DataStructure.AssetPool;
import GameEngine.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Animation extends Component {
    private List<Sprite> frames;
    private int currFrame;
    private float nextFrame;

    public Animation(){
        frames = new ArrayList<Sprite>();
        currFrame = 0;
        nextFrame = 0;
    }


    public void addFrame(String file){
        frames.add(AssetPool.getSprite(file));
    }

    public void draw(Graphics2D g2){
        frames.get(currFrame).draw(g2);
    }
    @Override
    public void update(double dt){
        nextFrame += (float) dt;
        if (nextFrame >= 0.16f){
            nextFrame = 0;
            currFrame = currFrame + 1 % frames.size();
        }
    }


    @Override
    public Component copy() {
        return null;
    }
}
