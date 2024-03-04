import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FadingImageSet {

    private ArrayList<BufferedImage> images;
    private int currentImage;
    private int posX;
    private int posY;
    private int width;
    private int height;

    //fades from 1 image to image 2, image 2 to image 3, etc.
    public FadingImageSet(ArrayList<BufferedImage> images, int posX, int posY, int width, int height) {
        this.images = images;
        this.currentImage = 0;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public FadingImageSet(int posX, int posY, int width, int height) {
        this.images = new ArrayList<>();
        this.currentImage = 0;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public BufferedImage getCurrentImage(){
        return images.get(currentImage);
    }

    public void addImage(String imageName){
        try {
            BufferedImage image = ImageIO.read(getClass().getResource(imageName));
            images.add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(){
        currentImage++;
    }

    public void draw(Graphics2D g2d){
        BufferedImage image = this.getCurrentImage();
        AffineTransform transform = new AffineTransform();


        double widthScale = (double)this.width/image.getWidth();
        double heightScale = widthScale; //(double)this.height/image.getHeight();

        System.out.println("original size: "+image.getWidth()+", "+image.getHeight());
        System.out.println("target* size: "+this.width+", "+this.height);
        System.out.println("scale: "+widthScale+", "+heightScale);
        System.out.println("final size: "+this.width*widthScale+", "+this.height*heightScale);

        transform.translate(posX-((double)image.getWidth()*widthScale/2), posY-((double)image.getHeight()*heightScale/2));

        transform.scale(widthScale, heightScale);


        g2d.drawImage(image, transform, null);
    }
}
