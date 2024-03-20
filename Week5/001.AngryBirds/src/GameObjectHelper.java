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

    public  GameObject createFloor(World world){
        Body floor = new Body();
        BodyFixture fixture = new BodyFixture(Geometry.createRectangle(20.5,1.6));
        floor.addFixture(fixture);
        floor.getTransform().setTranslation(new Vector2(0,-4.5));

        world.addBody(floor);
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("background.jpg"))/*.getSubimage(0,1240,2560,200)*/;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GameObject(image, floor, new Vector2(0,-620), 0.8);

    }

    public GameObject createBird(Point2D pos, World world, boolean motion){
        int sizing = 1;

        Body ball = new Body();
        BodyFixture fixture = new BodyFixture(Geometry.createCircle(sizing*0.5));
        fixture.setRestitution(.25);
        fixture.setDensity(2.0);
        ball.addFixture(fixture);
        ball.getTransform().setTranslation(new Vector2((pos.getX()/100)-9.5,-(pos.getY()/100)+5.5));
        ball.setMass(MassType.NORMAL);

        if(motion)
            ball.applyForce(new Vector2(1000,500));

        world.addBody(ball);
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("birdAndPig.png")).getSubimage(0,0,140,140);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GameObject(image, ball, new Vector2(-10,-12), sizing*0.9);
    }

    public GameObject createPig(Point2D pos, World world){
        int sizing = 1;

        Body ball = new Body();
        BodyFixture fixture = new BodyFixture(Geometry.createCircle(sizing*0.5));
        fixture.setRestitution(.25);
        fixture.setDensity(1.0);
        ball.addFixture(fixture);
        ball.getTransform().setTranslation(new Vector2((pos.getX()/100)-9.5,-(pos.getY()/100)+5.5));
        ball.setMass(MassType.NORMAL);
//        ball.applyForce(new Vector2(1000,0));
        world.addBody(ball);
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("birdAndPig.png")).getSubimage(140,0,148,144);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GameObject(image, ball, new Vector2(-2,-5), sizing*0.8);
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

    public GameObject createSmallBarBlock(Point2D pos, World world){
        int sizing = 1;

        Body cube = new Body();
        BodyFixture fixture = new BodyFixture(Geometry.createRectangle(sizing*1,sizing*0.5));
        fixture.setRestitution(.1);
        fixture.setDensity(4.0);
        cube.addFixture(fixture);
        cube.rotate(Math.toRadians(90));
        cube.getTransform().setTranslation(new Vector2((pos.getX()/100)-9.5,-(pos.getY()/100)+5.5));
        cube.setMass(MassType.NORMAL);

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("wood_blocks.png")).getSubimage(41*3,41,42,21);
        } catch (IOException e) {
            e.printStackTrace();
        }

        world.addBody(cube);
        return new GameObject(image, cube, new Vector2(0,0), sizing*2.4);
    }

    public GameObject createLongBarBlock(Point2D pos, World world, boolean rotated){
        int sizing = 1;

        Body cube = new Body();
        BodyFixture fixture = new BodyFixture(Geometry.createRectangle(sizing*2.4,sizing*0.25));
        fixture.setRestitution(.1);
        fixture.setDensity(4.0);
        cube.addFixture(fixture);
        if(rotated)
            cube.rotate(Math.toRadians(90));

        cube.getTransform().setTranslation(new Vector2((pos.getX()/100)-9.5,-(pos.getY()/100)+5.5));
        cube.setMass(MassType.NORMAL);

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("wood_blocks.png")).getSubimage((int)(41*3.5)+1,41*2+2,(int)(41*2.5),11);
        } catch (IOException e) {
            e.printStackTrace();
        }

        world.addBody(cube);
        return new GameObject(image, cube, new Vector2(0,0), sizing*2.4);
    }
}
