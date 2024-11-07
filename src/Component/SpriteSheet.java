package Component;

import DataStructure.AssetPool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet implements Serializable {
    private List<Sprite> spriteList;
    private int tileWidth;
    private int tileHeight;
    private int spacing;
    private int columns;
    private int size;

    public SpriteSheet(String pictureFile, int tileWidth, int tileHeight, int spacing, int columns, int size){
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.spacing = spacing;
        this.columns = columns;
        this.size = size;
        Sprite parent = AssetPool.getSprite(pictureFile);
        spriteList = new ArrayList<>();

        int count = 0;
        for (int i = 0; count < size;i++){
            for (int j = 0; j < columns && count < size; j++){
                int imgX = (j * tileWidth) + (j * spacing);
                int imgY = (i * tileHeight) + (i * spacing);
                spriteList.add(new Sprite(parent.getImage().getSubimage(imgX, imgY, tileWidth, tileHeight),
                        i, j, count));
                count++;
            }
        }
        AssetPool.addSpriteSheet(pictureFile,this);
    }

    public Sprite getSprite(int index){
        return spriteList.get(index);
    }

    public int getSize(){
        return size;
    }

}
