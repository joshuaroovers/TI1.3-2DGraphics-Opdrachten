import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class AstralBody {
    private Point2D pos;
    private double radius;
    private double distance;
    private Shape shape;
    private Color color;
    private double rotation;
    private boolean rotateClockWise;
    private double rotationSpeed;
    private AstralBody orbitBody;
    private boolean isCenterBodyOfSystem = false;

    public AstralBody(Point2D pos, double radius, Color color, boolean rotateClockWise, double rotationSpeed, AstralBody orbitBody) {
        this.pos = pos;
        this.radius = radius;
        this.distance = orbitBody.getPosition().distance(pos);
        this.shape = new Ellipse2D.Double(0,0, radius, radius);
        this.color = color;
        this.rotateClockWise = rotateClockWise;
        this.rotationSpeed = rotationSpeed;
        this.orbitBody = orbitBody;
    }
    //#region planet overflow Constructors

//    public AstralBody(double distance, double radius, Color color, boolean rotateClockWise, double rotationSpeed, AstralBody orbitBody) {
//        this.pos = new Point2D.Double(orbitBody.getPosition().getX()+distance, orbitBody.getPosition().getY());
//        this.radius = radius;
//        this.distance = orbitBody.getPosition().distance(pos);
//        this.shape = new Ellipse2D.Double(0,0, radius, radius);
//        this.color = color;
//        this.rotateClockWise = rotateClockWise;
//        this.rotationSpeed = rotationSpeed;
//        this.orbitBody = orbitBody;
//    }
    //#endregion

    public AstralBody(Point2D pos, double radius, Color color, boolean rotateClockWise, double rotationSpeed) {
        this.pos = pos;
        this.radius = radius;
        this.shape = new Ellipse2D.Double(0,0, radius, radius);
        this.color = color;
        this.rotateClockWise = rotateClockWise;
        this.rotationSpeed = rotationSpeed;
        this.isCenterBodyOfSystem = true;
    }
    //#region center body overflow constructors

    //#endregion

    public void update(double time){
        if(rotateClockWise){
            rotation += time*rotationSpeed;
        }else{
            rotation -= time*rotationSpeed;
        }
    }

    public void draw(Graphics2D g2d){
        AffineTransform tx1 = new AffineTransform();
        AffineTransform tx2 = new AffineTransform();



        if(!isCenterBodyOfSystem){
            tx1.translate(orbitBody.getPosition().getX()-(radius/2)+distance, orbitBody.getPosition().getY()-(radius/2));

            g2d.setColor(Color.WHITE);
            g2d.draw(new Ellipse2D.Double(orbitBody.getPosition().getX()-distance, orbitBody.getPosition().getY()-distance, distance*2,distance*2));
        }else{
            tx1.translate(pos.getX()-(radius/2), pos.getY()-(radius/2));
        }

        Shape tempShape = tx1.createTransformedShape(shape);

        tx2.rotate(rotation);
        tx2.translate(distance,0);

//        g2d.setColor(Color.WHITE);
//        g2d.draw(tx2.createTransformedShape(shape));
        g2d.setColor(color);
//        if(!isCenterBodyOfSystem)
//            g2d.fill(new Ellipse2D.Double(orbitBody.getPosition().getX()-3,orbitBody.getPosition().getY()-3,6,6));

        g2d.fill(tx2.createTransformedShape(tempShape));






    }

    public Point2D getPosition(){
        return new Point2D.Double(this.pos.getX(),this.pos.getY());
    }
}
