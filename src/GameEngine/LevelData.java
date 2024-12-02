package GameEngine;

import Util.Vector;

public class LevelData {
    private String levelLayer1;
    private String levelLayer2;
    private Vector spawnpoint1;
    private Vector spawnpoint2;

    public LevelData(String levelLayer1, String levelLayer2){
        this.levelLayer1 = levelLayer1;
        this.levelLayer2 = levelLayer2;
        spawnpoint1 = new Vector();
        spawnpoint2 = new Vector();
    }

    public void setSpawnPoint1(Vector spawnpoint1){
        this.spawnpoint1 = spawnpoint1;
    }

    public void setSpawnPoint2(Vector spawnpoint2){
        this.spawnpoint2 = spawnpoint2;
    }

    public Vector getSpawnPoint(int n){
        if (n == 1){
            return spawnpoint1;
        } else if (n == 2){
            return spawnpoint2;
        }
        return null;
    }


    public String getLevelLayer(int n){
        if (n == 1){
            return levelLayer1;
        } else if (n == 2){
            return levelLayer2;
        }
        return null;
    }

    public String toString(){
        return "Loading: " + levelLayer1 + " | " + levelLayer2;
    }
}
