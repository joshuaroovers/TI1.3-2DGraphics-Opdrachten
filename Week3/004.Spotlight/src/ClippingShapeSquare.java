import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ClippingShapeSquare extends ClippingShape{

    public ClippingShapeSquare(Point2D pos, double size) {
        this.position = pos;
        this.size = size;
        this.shape = new Rectangle2D.Double(pos.getX(),pos.getY(),size,size);
    }

    @Override
    public Shape getClip() {
        return this.shape;
    }

    @Override
    public void update(Point2D pos) {
        this.position = pos;
        this.shape = new Rectangle2D.Double(pos.getX(),pos.getY(),size,size);
    }
}
