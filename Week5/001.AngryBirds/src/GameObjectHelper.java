import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameObjectHelper {

    public GameObject createBird(Point2D pos, World world){
        int sizing = 1;

        Body ball = new Body();
        BodyFixture fixture = new BodyFixture(Geometry.createCircle(sizing*0.5));
        fixture.setRestitution(.25);
        ball.addFixture(fixture);
        ball.getTransform().setTranslation(new Vector2((pos.getX()/100)-9.5,-(pos.getY()/100)+5.5));
        ball.setMass(MassType.NORMAL);
//        ball.applyForce(new Vector2(1000,0));
        world.addBody(ball);
        return new GameObject("angry-birds-png-29.png", ball, new Vector2(-15,-25), sizing*0.4);
    }

    public GameObject createBlock(Point2D pos, World world){
        int sizing = 1;

        Body cube = new Body();
        BodyFixture fixture = new BodyFixture(Geometry.createRectangle(sizing*1,sizing*1));
        fixture.setRestitution(.1);
        fixture.setDensity(5.0);
        cube.addFixture(fixture);
        cube.getTransform().setTranslation(new Vector2((pos.getX()/100)-9.5,-(pos.getY()/100)+5.5));
        cube.setMass(MassType.NORMAL);

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("wood_blocks.png")).getSubimage(0,0,42,42);
        } catch (IOException e) {
            e.printStackTrace();
        }

        world.addBody(cube);
        return new GameObject(image, cube, new Vector2(0,0), sizing*2.4);
    }
}
