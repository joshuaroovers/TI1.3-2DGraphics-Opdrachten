import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class SpriteSheet {
    private double marginX;
    private double marginY;
    private double posX;
    private double posY;
    private BufferedImage[] tiles;
    private int framesCount;
    private double currentFrame;

    private int rows;
    private int columns;
    private int count;

    public SpriteSheet(String spritePath, int subImageCount, int subImageColumnCount, int subImageRowCount, double marginX, double marginY, int posX, int posY ) {
        System.out.println("starting to create SpriteSheet");
        this.framesCount = subImageCount;
        this.currentFrame = 0;
        this.marginX = marginX;
        this.marginY = marginY;
        this.posX = posX;
        this.posY = posY;

        this.rows = subImageRowCount;
        this.columns = subImageColumnCount;
        this.count = subImageCount;

        try {
            BufferedImage image = ImageIO.read(getClass().getResource(spritePath));
            tiles = new BufferedImage[subImageCount];

            double subImageWidth = image.getWidth()/subImageColumnCount - marginX*2;
            double subImageHeight = image.getHeight()/subImageRowCount - marginY*2;

            if(false) {//debugging
                System.out.println("sprite sheet size: " + image.getWidth() + " x " + image.getHeight());
                System.out.println("subImage size: " + subImageWidth + " x " + subImageHeight);
                System.out.println("sprite sheet size calculated by subImage: " + (subImageWidth + (marginX * 2)) * subImageColumnCount + " x " + (subImageHeight + (marginY * 2)) * subImageRowCount);
            }

            for (int i = 0; i < subImageRowCount; i++) {
                for (int j = 0; j < subImageColumnCount; j++) {
                    if(i*8+j < count) {
                        System.out.println("adding subImage " + (i*8+j+1) + "/" + subImageCount +"("+i+","+j+")");

                        tiles[i*8+j] = image.getSubimage((int) ((j*(subImageWidth+(marginX*2))) + marginX), (int) ((i*(subImageHeight+(marginY*2))) + marginY), (int)subImageWidth, (int)subImageHeight);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(tiles.length);

    }

    public SpriteSheet(String spritePath, int subImageCount, int subImageColumnCount, int subImageRowCount, int posX, int posY ) {
        this(spritePath,subImageCount,subImageColumnCount,subImageRowCount,0,0, posX, posY);
    }


    private BufferedImage getTile() {
        int frameOffset = 4;
        int frameLimit = 8 + frameOffset; //12
        if(currentFrame < frameOffset || currentFrame > frameLimit) // < 3 || > 12
            currentFrame = frameOffset;
        System.out.println("current frame: "+ currentFrame);
        return tiles[(int)currentFrame];
    }

    public void draw(FXGraphics2D graphics){
        AffineTransform transform = new AffineTransform();
        transform.translate(posX/*-(this.getTile().getWidth()/2)*/, posY);
        transform.scale(2,2);
        graphics.drawImage(this.getTile(), transform, null);

//        graphics.drawString(Integer.toString((int)this.currentFrame), (int)transform.getTranslateX()+5, (int)transform.getTranslateY()+5);
    }

    public void drawSpriteSheet(Graphics2D g2d){
        int sc = 1;
        int sc2 = sc/**2*/;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(i*8+j < count) {
                    //System.out.println(i*8+j);

                    AffineTransform transform = new AffineTransform();
                    transform.translate(j * tiles[0].getWidth()*sc2, i * tiles[0].getHeight()*sc2);
                    transform.scale(sc,sc);
                    g2d.drawImage(tiles[i*8+j], transform, null);
                    g2d.drawString(j + "," + i, j * tiles[0].getWidth()*sc2 + 10, i * tiles[0].getHeight()*sc2 + 20);
                }
            }
        }
    }

    public void update(double increment){
        //todo skips over last tile :T  (kinda fixed by puting draw under update in the animation timer
        //todo but it still gives an error????? but it doesn't stop anymore atleast..

        if(currentFrame >= framesCount){
            currentFrame = 0;
        }else{
            currentFrame +=increment;
        }

        //this.posX += increment;
    }




}
