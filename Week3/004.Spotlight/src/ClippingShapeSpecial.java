import java.awt.*;
import java.awt.geom.*;

public class ClippingShapeSpecial extends ClippingShape{

    public ClippingShapeSpecial(Point2D pos, double size) {
        this.position = pos;
        this.size = size;
        this.shape = getShape(pos,size);
    }

    @Override
    public Shape getClip() {
        return this.shape;
    }

    @Override
    public void update(Point2D pos) {
        this.position = pos;
        this.shape = getShape(pos,size);
    }

    @Override
    public Shape getShape(Point2D pos, double size){

        Area body = new Area(new Ellipse2D.Double(0,0, size, size));

        double extraMargin = size/16;
        double smallSize = size/2 - extraMargin;
        Area cutter1 = new Area(new Ellipse2D.Double(0,0, smallSize, smallSize));
        Area cutter2 = new Area(new Ellipse2D.Double(0+smallSize+(extraMargin*2),0, smallSize, smallSize));
        Area cutter3= new Area(new Ellipse2D.Double(0,0+smallSize+(extraMargin*2), smallSize, smallSize));
        Area cutter4 = new Area(new Ellipse2D.Double(0+smallSize+(extraMargin*2),0+smallSize+(extraMargin*2), smallSize, smallSize));

        body.subtract(cutter1);
        body.subtract(cutter2);
        body.subtract(cutter3);
        body.subtract(cutter4);


//        body.intersect(cutter);
//
//        double intersectHeight = body.getBounds().getHeight();
//        double intersectWidth = body.getBounds().getWidth();
//        Area iris = new Area(new Ellipse2D.Double(0+(intersectWidth/2)-(intersectHeight),0,intersectHeight,intersectHeight));
//        Area pupil = new Area(new Ellipse2D.Double());
//        iris.subtract(pupil);

        //body.subtract(body);

        return AffineTransform.getTranslateInstance(pos.getX(),pos.getY()).createTransformedShape(body);
    }
}
