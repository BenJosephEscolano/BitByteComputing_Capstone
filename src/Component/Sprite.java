package Component;

import DataStructure.AssetPool;
import GameEngine.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite extends Component{
    private String pictureFile;
    private BufferedImage image;
    private int width, height;

    public Sprite(String pictureFile){
        this.pictureFile = pictureFile;

        try {
            File file = new File(pictureFile);
            if (AssetPool.hasSprite(pictureFile)){
                throw new Exception("Asset already exists: " + file.getAbsolutePath());
            }
            this.image = ImageIO.read(file);
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public Sprite(BufferedImage image){
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public BufferedImage getImage(){
        return image;
    }

    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(image, (int)getGameObject().getX(), (int)getGameObject().getY(), width, height, null);
    }

    @Override
    public Component copy() {
        return new Sprite(this.image);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
