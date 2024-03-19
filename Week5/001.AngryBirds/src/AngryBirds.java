
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class AngryBirds extends Application {

    private ResizableCanvas canvas;
    private World world;
    private MousePicker mousePicker;
    private Camera camera;
    private boolean debugSelected = false;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private GameObjectHelper gameObjectHelper = new GameObjectHelper();

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();

        // Add debug button
        javafx.scene.control.CheckBox showDebug = new CheckBox("Show debug");
        showDebug.setOnAction(e -> {
            debugSelected = showDebug.isSelected();
        });
        showDebug.setSelected(true);
        debugSelected = true;

        mainPane.setTop(showDebug);

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);
        mousePicker = new MousePicker(canvas);

        canvas.setOnMouseClicked(e -> mouseClicked(e));

        Body floor = new Body();
        BodyFixture fixture = new BodyFixture(Geometry.createRectangle(20,0.2));
//        fixture.setRestitution(.25);
//        fixture.setFriction(0.5);
        floor.addFixture(fixture);
        floor.getTransform().setTranslation(new Vector2(0,-4.5));
//        floor.setMass(MassType.NORMAL);
        world.addBody(floor);
        gameObjects.add(new GameObject("angry-birds-png-29.png", floor, new Vector2(0,0), 0.8));


        Body sling = new Body();
        BodyFixture fixture2 = new BodyFixture(Geometry.createRectangle(0.5,1.5));
//        fixture2.setRestitution(.25);
//        fixture2.setFriction(0.5);

        sling.addFixture(fixture2);
        sling.getTransform().setTranslation(new Vector2(-7,-2));
//        floor.setMass(MassType.NORMAL);
        world.addBody(sling);
        gameObjects.add(new GameObject("angry-birds-png-29.png", sling, new Vector2(0,0), 0.8));





        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane, 1920, 1080));
        stage.setTitle("Angry Birds");
        stage.show();
        draw(g2d);
    }

    private void mouseClicked(MouseEvent e) {
        //I know this isn't reliable for when the camera zooms out but it's nice for testing
        Point2D mousePosition = new Point2D.Double(e.getX(),e.getY());
        if(e.isAltDown()){
            gameObjects.add(gameObjectHelper.createBird(mousePosition, world));
        }
        else if(e.isShiftDown()){
            gameObjects.add(gameObjectHelper.createBlock(mousePosition ,world));
        }
        else if(e.isControlDown()){

        }
    }

    public void init() {
        world = new World();
        world.setGravity(new Vector2(0, -9.8));
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform originalTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics.scale(1, -1);

        for (GameObject go : gameObjects) {
            go.draw(graphics);
        }

        if (debugSelected) {
            graphics.setColor(Color.blue);
            DebugDraw.draw(graphics, world, 100);
        }

        graphics.setTransform(originalTransform);
    }

    public void update(double deltaTime) {
        mousePicker.update(world, camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()), 100);
        world.update(deltaTime);
    }

    public static void main(String[] args) {
        launch(AngryBirds.class);
    }

}
