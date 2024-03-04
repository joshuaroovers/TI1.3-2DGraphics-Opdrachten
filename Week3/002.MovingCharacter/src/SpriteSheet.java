import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class SpriteSheet {
    private int posX;
    private int posY;
    private BufferedImage[] tiles;
    private int framesCount;
    private int currentFrame;

    public SpriteSheet(int subImageCount, int subImageColumnCount, int subImageRowCount, int posX, int posY ) {
        System.out.println("starting to create SpriteSheet");
        this.framesCount = subImageCount;
        this.currentFrame = 0;
        this.posX = posX;
        this.posY = posY;

        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/images/sprite.png"));
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
        return tiles[currentFrame];
    }

    public void draw(FXGraphics2D graphics){
        AffineTransform transform = new AffineTransform();
        transform.translate(posX-(this.getTile().getWidth()/2), posY);

        graphics.drawImage(this.getTile(), transform, null);
    }

    public void update(){

    }

    public void frameStep(){
        if(currentFrame < framesCount-1){
            currentFrame +=1;
        }else{
            currentFrame = 0;
        }

    }


}
