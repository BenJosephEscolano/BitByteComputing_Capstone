package Component;

import DataStructure.AssetPool;
import GameEngine.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Sprite extends Component implements Serializable {
    private String pictureFile;
    private transient BufferedImage image;
    private int width, height;
    private int row, column, index;

    private boolean isSubSprite = false;

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

    public Sprite(BufferedImage image, int row, int column, int index){
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.row = row;
        this.column = column;
        this.index = index;
        this.isSubSprite = true;
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
        if (!isSubSprite){
            return new Sprite(this.image);
        }
        return new Sprite(this.image, this.row, this.column, this.index);
    }


    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        ImageIO.write(image, "png", out);
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        image = ImageIO.read(in);
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isSubSprite() {
        return isSubSprite;
    }
}
