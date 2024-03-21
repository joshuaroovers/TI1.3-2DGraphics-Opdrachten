import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Eindopdracht extends Application {
    private ResizableCanvas canvas;
    private ArrayList<AstralBody> astralBodies = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);

        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());


        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.black);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
        graphics.translate((int)canvas.getWidth()/2, (int)canvas.getHeight()/2);

        graphics.setColor(Color.RED);
        graphics.drawLine(0,0,500,0);
        graphics.setColor(Color.BLUE);
        graphics.drawLine(0,0,0,500);
        graphics.setColor(Color.YELLOW);
        graphics.drawLine(-500,0,0,0);
        graphics.setColor(Color.GREEN);
        graphics.drawLine(0,-500,0,0);


        for (AstralBody astralBody : astralBodies) {
            astralBody.draw(graphics);
        }

    }


    public void update(double deltaTime) {
        for (AstralBody astralBody : astralBodies) {
            astralBody.update(deltaTime);
        }
    }

    public void init()
    {
        AstralBody sun = (new AstralBody("sun", new Point2D.Double(0,0), 100, 0.2, Color.YELLOW));
        astralBodies.add(sun);
        AstralBody planet1 = (new AstralBody("planet1",new Point2D.Double(300,0), 30, 1, Color.BLUE, sun ));
        astralBodies.add(planet1);
        AstralBody moon1 = new AstralBody("moon1", new Point2D.Double(200,0), 10, 3, Color.WHITE, planet1);
        astralBodies.add(moon1);
        AstralBody moon2 = new AstralBody("moon2", new Point2D.Double(150,0), 10, -1, Color.GREEN, planet1);
        astralBodies.add(moon2);
    }
}
