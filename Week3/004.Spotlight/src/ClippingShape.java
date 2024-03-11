import java.awt.*;
import java.awt.geom.Point2D;

abstract class ClippingShape {
    Point2D position;
    Shape shape;
    double size;

    abstract Shape getClip();

    abstract void update(Point2D pos);

    abstract Shape getShape(Point2D pos, double size);
}
