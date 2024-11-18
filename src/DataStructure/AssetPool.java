package DataStructure;

import Component.Sprite;
import Component.SpriteSheet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    static Map<String, Sprite> sprites = new HashMap<>();
    static Map<String, SpriteSheet> spriteSheets = new HashMap<>();

    public static boolean hasSprite(String pictureFile){
        return AssetPool.sprites.containsKey(pictureFile);
    }

    public static boolean hasSpriteSheet(String pictureFile){
        return AssetPool.spriteSheets.containsKey(pictureFile);
    }

    public static Sprite getSprite(String pictureFile){
        //"assets/pic.png" and "pic.png" will now return the same file everytime
        File file = new File(pictureFile);
        if (AssetPool.hasSprite(file.getAbsolutePath())){
            return AssetPool.sprites.get(file.getAbsolutePath());
        } else {
            Sprite sprite = new Sprite(pictureFile);
            AssetPool.addSprite(pictureFile, sprite);
            return AssetPool.sprites.get(file.getAbsolutePath());
        }
    }
    public static SpriteSheet getSpriteSheet(String pictureFile){
        File file = new File(pictureFile);
        if (AssetPool.hasSpriteSheet(file.getAbsolutePath())){
            return AssetPool.spriteSheets.get(file.getAbsolutePath());
        } else {
            System.out.println("Spritesheet '" + pictureFile + "' does not exist.");
            System.exit(-1);
        }
        return null;
    }

    /**
     * @param pictureFile the absolute path to the picture
     * @param sprite
     */
    public static void addSprite(String pictureFile, Sprite sprite){
        File file = new File(pictureFile);
        if (!AssetPool.hasSprite(file.getAbsolutePath())){
            AssetPool.sprites.put(file.getAbsolutePath(), sprite);
        } else {
            System.out.println("Asset pool already has sprite: " + file.getAbsolutePath());
            System.exit(-1);
        }
    }
    public static void addSpriteSheet(String pictureFile, SpriteSheet spriteSheet){
        File file = new File(pictureFile);
        if (!AssetPool.hasSpriteSheet(file.getAbsolutePath())){
            AssetPool.spriteSheets.put(file.getAbsolutePath(), spriteSheet);
        } else {
            System.out.println("Asset pool already has sprite sheet: " + file.getAbsolutePath());
            System.exit(-1);
        }
    }

    

}
