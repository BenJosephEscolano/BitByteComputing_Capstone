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
        level.add(new LevelData("game_data/level02_layer1", "game_data/level02_layer2"));
        level.add(new LevelData("game_data/level03_layer1", "game_data/level03_layer2"));
        level.add(new LevelData("game_data/level04_layer1", "game_data/level04_layer2"));
        level.add(new LevelData("game_data/level05_layer1", "game_data/level05_layer2"));
    }

    protected List<LevelData> getLevels(){
        return level;
    }

}
