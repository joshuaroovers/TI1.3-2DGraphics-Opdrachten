import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class SpriteSheet {
    private double posX;
    private double posY;
    private BufferedImage[] tiles;
    private int framesCount;
    private double currentFrame;

    public SpriteSheet(String spritePath, int subImageCount, int subImageColumnCount, int subImageRowCount, int posX, int posY ) {
        System.out.println("starting to create SpriteSheet");
        this.framesCount = subImageCount;
        this.currentFrame = 0;
        this.posX = posX;
        this.posY = posY;

        try {
            BufferedImage image = ImageIO.read(getClass().getResource(spritePath));
            tiles = new BufferedImage[subImageCount];
            int subImageWidth = image.getWidth()/subImageColumnCount;
            int subImageHeight = image.getHeight()/subImageRowCount;
            for(int i = 0; i < subImageCount; i++) {
                System.out.println("adding subImage " + (i+1) + "/" + subImageCount);
                tiles[i] = image.getSubimage(subImageWidth * (i % subImageColumnCount), subImageHeight * (i / subImageRowCount), subImageWidth, subImageHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getTile() {
        int frameRow = 5;
        //why????????????????????????????????????
//        if(currentFrame < frameRow*8-1-3 || currentFrame > (frameRow+1)*8-1-3)
//            currentFrame = frameRow*8-1-3;
        System.out.println("current frame: "+ currentFrame);
        return tiles[(int)currentFrame];
    }

    public void draw(FXGraphics2D graphics){
        AffineTransform transform = new AffineTransform();
        transform.translate(posX/*-(this.getTile().getWidth()/2)*/, posY);
        transform.scale(4,4);
        graphics.drawImage(this.getTile(), transform, null);
    }

    public void update(double increment){
        this.posX += increment;
    }

    public void frameStep(double step){
        if(currentFrame >= framesCount){
            currentFrame = 0;
        }else{
            currentFrame +=step;
        }

    }


}
