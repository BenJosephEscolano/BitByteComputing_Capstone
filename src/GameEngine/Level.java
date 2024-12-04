package GameEngine;

import Util.Vector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level implements Serializable {
    protected List<LevelData> level;
    
    public Level(){
        this.level = new ArrayList<>();
        loadLevelData();
    }

    private void loadLevelData(){
        level.add(new LevelData("game_data/level01_layer1", "game_data/level01_layer2"));
        level.get(level.size()-1).setSpawnPoint1(new Vector(336.0f, 300.0f));
        level.get(level.size()-1).setSpawnPoint2(new Vector(882, 300.0f));
        //level.getLast().setSpawnPoint1(new Vector(336.0f, 300.0f));
        //level.getLast().setSpawnPoint1();
        //level.getLast().setSpawnPoint2(new Vector(882.0f, 300.0f));
        level.add(new LevelData("game_data/level02_layer1", "game_data/level02_layer2"));
        level.get(level.size()-1).setSpawnPoint1(new Vector(504, 300.0f));
        level.get(level.size()-1).setSpawnPoint2(new Vector(714, 300.0f));
        //level.getLast().setSpawnPoint1(new Vector(504.0f, 350.0f));
        //level.getLast().setSpawnPoint2(new Vector(714.0f, 350.0f));
        level.add(new LevelData("game_data/level03_layer1", "game_data/level03_layer2"));
        level.get(level.size()-1).setSpawnPoint1(new Vector(336, 300.0f));
        level.get(level.size()-1).setSpawnPoint2(new Vector(882, 300.0f));
        //level.getLast().setSpawnPoint1(new Vector(336.0f, 350.0f));
        //level.getLast().setSpawnPoint2(new Vector(882.0f, 350.0f));
        level.add(new LevelData("game_data/level04_layer1", "game_data/level04_layer2"));
        level.get(level.size()-1).setSpawnPoint1(new Vector(336, 300.0f));
        level.get(level.size()-1).setSpawnPoint2(new Vector(882, 300.0f));
        //level.getLast().setSpawnPoint1(new Vector(336.0f, 400.0f));
        //level.getLast().setSpawnPoint2(new Vector(882.0f, 400.0f));
        level.add(new LevelData("game_data/level05_layer1", "game_data/level05_layer2"));
        level.get(level.size()-1).setSpawnPoint1(new Vector(336, 300.0f));
        level.get(level.size()-1).setSpawnPoint2(new Vector(882, 300.0f));
        //level.getLast().setSpawnPoint1(new Vector(336.0f, 400.0f));
        //level.getLast().setSpawnPoint2(new Vector(882.0f, 400.0f));
    }

    protected List<LevelData> getLevels(){
        return level;
    }

}
