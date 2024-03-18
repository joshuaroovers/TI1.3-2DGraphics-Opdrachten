import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FadingImageSet {


    private ArrayList<BufferedImage> images;
    private int currentImage;
    private int secondImage;
    private float opacity;
    private double speed;
    private int posX;
    private int posY;
    private int width;
    private int height;

    //fades from 1 image to image 2, image 2 to image 3, etc.
    public FadingImageSet(ArrayList<BufferedImage> images, int posX, int posY, int width, int height, double speed) {
        this.images = images;
        this.currentImage = 0;
        this.secondImage = -1;
        this.opacity = 0.0f;
        this.speed = speed;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public FadingImageSet(int posX, int posY, int width, int height, double speed) {
        this(new ArrayList<>(), posX, posY, width, height, speed);
    }
    public FadingImageSet(int posX, int posY, int width, int height) {
        this(posX, posY, width, height, 1.0);
    }

    public BufferedImage getImage(int index){
        return images.get(index);
    }

    public void addImage(String imageName){
        try {
            BufferedImage image = ImageIO.read(getClass().getResource(imageName));
            images.add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.secondImage = images.size()-1;
    }

    public void update(double deltaTime){
//        System.out.println(opacity);
        if(opacity >= 1.0f){
            System.out.println("next image");
            if(currentImage  == images.size()-1){
                currentImage = 0;
                secondImage = images.size()-1;
            }else{
                currentImage++;
                secondImage = currentImage-1;
            }

            opacity = 0.0f;
        }else{
            opacity += deltaTime*speed;
        }
    }

    public void draw(Graphics2D g2d){
        BufferedImage image = this.getImage(currentImage);
        BufferedImage image2 = this.getImage(secondImage);
        AffineTransform transform = new AffineTransform();


        double widthScale = (double)this.width/image.getWidth();
        double heightScale = widthScale; //(double)this.height/image.getHeight();

//        System.out.println("original size: "+image.getWidth()+", "+image.getHeight());
//        System.out.println("target* size: "+this.width+", "+this.height);
//        System.out.println("scale: "+widthScale+", "+heightScale);
//        System.out.println("final size: "+this.width*widthScale+", "+this.height*heightScale);

        transform.translate(posX/*-((double)image.getWidth()*widthScale/2)*/, posY/*-((double)image.getHeight()*heightScale/2)*/);

        transform.scale(widthScale, heightScale);

        g2d.drawImage(image2, transform, null);
        if(opacity > 1.0f){
            opacity = 1.0f;
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(image, transform, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

    }
}
