import java.awt.*;
import java.awt.geom.Point2D;

abstract class ClippingShape {
    Point2D position;
    Shape shape;
    double size;

    public abstract Shape getClip();

    public abstract void update(Point2D pos);
}
