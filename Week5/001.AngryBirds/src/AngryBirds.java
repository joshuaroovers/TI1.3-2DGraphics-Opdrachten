
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
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
//        showDebug.setSelected(true);
//        debugSelected = true;

        mainPane.setTop(showDebug);

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);
        mousePicker = new MousePicker(canvas);

        canvas.setOnMouseClicked(e -> mouseClicked(e));


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
        if(e.isShiftDown()){
            if(e.getButton() == MouseButton.PRIMARY)
                gameObjects.add(gameObjectHelper.createBird(mousePosition, world, true));
            else if(e.getButton() == MouseButton.SECONDARY)
                gameObjects.add(gameObjectHelper.createPig(mousePosition, world));
        }
        else if(e.isControlDown()){
            if(e.getButton() == MouseButton.PRIMARY)
                gameObjects.add(gameObjectHelper.createBlock(mousePosition, world));
            else if(e.getButton() == MouseButton.SECONDARY)
                gameObjects.add(gameObjectHelper.createSmallBarBlock(mousePosition, world));
        }
        else if(e.isAltDown()){
            if(e.getButton() == MouseButton.PRIMARY)
                gameObjects.add(gameObjectHelper.createLongBarBlock(mousePosition, world, false));
            else if(e.getButton() == MouseButton.SECONDARY)
                gameObjects.add(gameObjectHelper.createLongBarBlock(mousePosition, world, true));
        }
    }

    public void init() {
        world = new World();
        world.setGravity(new Vector2(0, -9.8));

        gameObjects.add(gameObjectHelper.createFloor(world));

        gameObjects.add(gameObjectHelper.createBird(new Point2D.Double(200,850), world, false));

        gameObjects.add(gameObjectHelper.createSmallBarBlock(new Point2D.Double(1540,850),world));
        gameObjects.add(gameObjectHelper.createBlock(new Point2D.Double(1750,850),world));
        gameObjects.add(gameObjectHelper.createLongBarBlock(new Point2D.Double(1650,820),world,false));

        gameObjects.add(gameObjectHelper.createLongBarBlock(new Point2D.Double(1600,650),world,true));
        gameObjects.add(gameObjectHelper.createLongBarBlock(new Point2D.Double(1650,650),world,true));
        gameObjects.add(gameObjectHelper.createLongBarBlock(new Point2D.Double(1700,650),world,true));

        gameObjects.add(gameObjectHelper.createLongBarBlock(new Point2D.Double(1650,500),world,false));
        gameObjects.add(gameObjectHelper.createLongBarBlock(new Point2D.Double(1650,480),world,false));
//        gameObjects.add(gameObjectHelper.createLongBarBlock(new Point2D.Double(1650,450),world,false));


        gameObjects.add(gameObjectHelper.createPig(new Point2D.Double(1600,450),world));
        gameObjects.add(gameObjectHelper.createSmallBarBlock(new Point2D.Double(1700,450),world));
        gameObjects.add(gameObjectHelper.createPig(new Point2D.Double(1700,350),world));


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

        graphics.setColor(Color.BLACK);
        graphics.scale(1, -1);
        graphics.drawString(
                "'Controls': " +"\n"+
                        "     shift+left click:        summon bird (with momentum)"+"\n"+
                        "     shift+right click:      summon pig"+"\n"+
                        "     ctrl+left click:          summon cube"+"\n"+
                        "     ctrl+right click:        summon half cube"+"\n"+
                        "     alt+left click:           summon long bar (horizontal)"+"\n"+
                        "     alt+right click:         summon long bar (vertical)"+"\n"
                ,-800,-400);
        graphics.scale(1, -1);

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
