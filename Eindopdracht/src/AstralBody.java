import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class AstralBody {
    private String name;
    private Point2D pos;
    private double radius;
    private Shape shape;
    private Color color;
    private double rotation;
    private double speed;
    private AstralBody orbitBody;
    private boolean isCenterBodyOfSystem = false;

    public AstralBody(String name, Point2D pos, double radius, double speed, Color color, AstralBody orbitBody) {
        this.name = name;
        this.pos = pos;
        this.radius = radius;
        this.shape = new Ellipse2D.Double(orbitBody.getPosition().getX(),orbitBody.getPosition().getY(), radius, radius);
        this.color = color;
        this.speed = speed;
        this.orbitBody = orbitBody;

        System.out.println(this+" ("+ (int)this.shape.getBounds().getX() +","+ (int)this.shape.getBounds().getY() + ")  " + orbitBody);
    }


    public AstralBody(String name, Point2D pos, double radius, double speed, Color color) {
        this.name = name;
        this.pos = pos;
        this.radius = radius;
        this.shape = new Ellipse2D.Double(0,0, radius, radius);
        this.color = color;
        this.speed = speed;
        this.isCenterBodyOfSystem = true;

        System.out.println(this);
    }

    public void update(double time){
        rotation += time*speed;
    }

    public void draw(Graphics2D g2d){
        AffineTransform tx = new AffineTransform();


        if(!isCenterBodyOfSystem) {
            g2d.rotate(rotation, orbitBody.getPosition().getX(), orbitBody.getPosition().getY() );

            tx.translate(-(radius / 2) - (orbitBody.getPosition().getX() - pos.getX()), -(radius / 2) - (orbitBody.getPosition().getY() - pos.getY()));
        }else {
            g2d.rotate(rotation);

            tx.translate(-(radius / 2), -(radius / 2));
        }



        g2d.setColor(color);
        g2d.fill(tx.createTransformedShape(shape));

        if(!isCenterBodyOfSystem){
            g2d.setColor(Color.WHITE);
            double distance = orbitBody.getPosition().distance(pos)*2;
            g2d.draw(new Ellipse2D.Double(orbitBody.getPosition().getX()-(distance/2),orbitBody.getPosition().getY()-(distance/2), distance,distance));

        }




    }

    private double getRadius() {
        return this.radius;
    }

    @Override
    public String toString() {
        return name +", (" + (int)pos.getX()+","+(int)pos.getY()+")";
    }

    private String getName() {
        return this.name;
    }

    public Point2D getPosition(){
        return new Point2D.Double(this.pos.getX(),this.pos.getY());
    }
}
