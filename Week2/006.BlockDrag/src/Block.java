import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Block {
    private double posX;
    private double posY;
    private int size;
    private Color color;
    private Rectangle2D rectangle;


    public Block(double posX, double posY, int size, Color color, Rectangle2D rectangle) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.color = color;
        this.rectangle = rectangle;
    }

    public void setPos(double x, double y){
        this.posX = x - (size/2);
        this.posY = y - (size/2);
    }

    public boolean contains(double x, double y){
        //System.out.println(x + " " + y + " :" + shape.contains(x,y));
        return rectangle.contains(x,y);
    }

    public void draw(Graphics2D graphics){
        this.rectangle = new Rectangle2D.Double(posX, posY, size, size);
        graphics.setColor(color);
        graphics.fill(rectangle);

        graphics.draw(rectangle);
    }
}
