import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class ClippingShapeCircle extends ClippingShape{

    public ClippingShapeCircle(Point2D pos, double size) {
        this.position = pos;
        this.size = size;
        this.shape = new Ellipse2D.Double(pos.getX(),pos.getY(),size,size);
    }

    @Override
    public Shape getClip() {
        return this.shape;
    }

    @Override
    public void update(Point2D pos) {
        this.position = pos;
        this.shape = new Ellipse2D.Double(pos.getX(),pos.getY(),size,size);
    }
}
