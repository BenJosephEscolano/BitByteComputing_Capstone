package GameEngine;

import java.util.ArrayList;
import java.util.List;

public class CollisionLayer {
    List<GameObject> gameObjectList;

    public CollisionLayer(){
        gameObjectList = new ArrayList<>();
    }

    public void addToLayer(GameObject g){
        gameObjectList.add(g);
    }

    public void remove(GameObject g){
        gameObjectList.remove(g);
    }
}
