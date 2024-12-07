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
        level.add(new LevelData("game_data/level02_layer1", "game_data/level02_layer2"));
        level.get(level.size()-1).setSpawnPoint1(new Vector(252, 300.0f));
        level.get(level.size()-1).setSpawnPoint2(new Vector(966, 300.0f));
        level.add(new LevelData("game_data/level03_layer1", "game_data/level03_layer2"));
        level.get(level.size()-1).setSpawnPoint1(new Vector(609, 300.0f));
        level.get(level.size()-1).setSpawnPoint2(new Vector(609, 100.0f));
        level.add(new LevelData("game_data/level04_layer1", "game_data/level04_layer2"));
        level.get(level.size()-1).setSpawnPoint1(new Vector(288, 400.0f));
        level.get(level.size()-1).setSpawnPoint2(new Vector(930, 250.0f));
        level.add(new LevelData("game_data/level05_layer1", "game_data/level05_layer2"));
        level.get(level.size()-1).setSpawnPoint1(new Vector(336, 250.0f));
        level.get(level.size()-1).setSpawnPoint2(new Vector(882, 250.0f));
        level.add(new LevelData("game_data/level06_layer1", "game_data/level06_layer2"));
        level.get(level.size()-1).setSpawnPoint1(new Vector(336.0f, 300.0f));
        level.get(level.size()-1).setSpawnPoint2(new Vector(882, 300.0f));
        level.add(new LevelData("game_data/level07_layer1", "game_data/level07_layer2"));
        level.get(level.size()-1).setSpawnPoint1(new Vector(504, 300.0f));
        level.get(level.size()-1).setSpawnPoint2(new Vector(714, 300.0f));
        System.out.println(level.size());
    }

    protected List<LevelData> getLevels(){
        return level;
    }

}
