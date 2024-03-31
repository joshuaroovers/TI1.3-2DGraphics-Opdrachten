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
//        stage.setTitle("");
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
            astralBody.update(deltaTime/100);
        }
    }

    public void init()
    {

        double sizeScale = 0.005;
        double baseDistance = 200;
        double baseSpeed = 50;
        double additionalSizeScale = 1;
        double additionalDistanceScale = 1;
        if(true){
            additionalSizeScale = 0.1;
            additionalDistanceScale = 0.5;
        }


        AstralBody sun = (new AstralBody("sun", new Point2D.Double(0,0), (1392000*sizeScale)*additionalSizeScale*0.1, 0, Color.YELLOW));
        astralBodies.add(sun);


        AstralBody mercury = (new AstralBody("mercury", new Point2D.Double(baseDistance*0.39,0), 4878*sizeScale, baseSpeed/0.24, Color.GRAY, sun ));
        astralBodies.add(mercury);

        AstralBody venus = (new AstralBody("venus",new Point2D.Double(baseDistance*0.72,0), 12104*sizeScale, baseSpeed/0.61, Color.LIGHT_GRAY, sun ));
        astralBodies.add(venus);

        AstralBody mars = (new AstralBody("mars",new Point2D.Double(baseDistance*1.52,0), 6788*sizeScale, baseSpeed/1.88, Color.RED, sun ));
        astralBodies.add(mars);

        AstralBody jupiter = (new AstralBody("jupiter",new Point2D.Double(baseDistance*5.2*(additionalDistanceScale*0.8),0), 142769*sizeScale*additionalSizeScale, baseSpeed/11.86, Color.ORANGE, sun ));
        astralBodies.add(jupiter);

        AstralBody saturn = (new AstralBody("saturn",new Point2D.Double(baseDistance*9.54*(additionalDistanceScale*0.6),0), 120000*sizeScale*additionalSizeScale, baseSpeed/29.46, Color.ORANGE, sun ));
        astralBodies.add(saturn);

        AstralBody uranus = (new AstralBody("uranus",new Point2D.Double(baseDistance*19.2*(additionalDistanceScale*0.4),0), 50800*sizeScale*additionalSizeScale, baseSpeed/84.1, Color.CYAN, sun ));
        astralBodies.add(uranus);

        AstralBody neptune = (new AstralBody("neptune",new Point2D.Double(baseDistance*30.06*(additionalDistanceScale*0.3),0), 48600*sizeScale*additionalSizeScale, baseSpeed/146.8, Color.CYAN, sun ));
        astralBodies.add(neptune);


        AstralBody earth = (new AstralBody("earth", new Point2D.Double(baseDistance*1,0), 12756*sizeScale, baseSpeed/1, Color.BLUE, sun ));
        astralBodies.add(earth);

        AstralBody moon = (new AstralBody("moon",new Point2D.Double(baseDistance*((1-0.0025)+0.2),0), 3474*sizeScale, baseSpeed/0.07, Color.lightGray, earth ));
        astralBodies.add(moon);
    }
}
