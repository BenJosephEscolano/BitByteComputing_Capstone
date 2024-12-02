package GameEngine;

import java.util.ArrayList;
import java.util.List;

public class CollisionLayer {
    private List<GameObject> gameObjectList;

    public CollisionLayer(){
        gameObjectList = new ArrayList<>();
    }

    public List<GameObject> getCollisionLayer() {
        return gameObjectList;
    }

    public void addToLayer(GameObject g){
        gameObjectList.add(g);
    }

    public void remove(GameObject g){
        gameObjectList.remove(g);
    }

    public void removeAll(){
        gameObjectList.removeAll(gameObjectList);
    }
}
