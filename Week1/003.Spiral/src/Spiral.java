import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;

public class Spiral extends Application {
    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.canvas = new Canvas(1920-100, 1080-100);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Spiral");
        primaryStage.show();
    }




    public void draw(FXGraphics2D graphics) {

        graphics.translate(this.canvas.getWidth()/2, this.canvas.getHeight()/2);
        graphics.scale( 1, -1);

//        graphics.setColor(Color.red);
//        graphics.drawLine(0,0,1000,0);
//        graphics.setColor(Color.green);
//        graphics.drawLine(0,0,0,1000);
//        graphics.setColor(Color.black);

        double resolution = 0.1;
        double scale = 50.0;

        double n = 0.18;

        double lastY = n * 0 * Math.sin(0);
        double lastX = n * 0 * Math.sin(0);

        //int phi = 5;

        for(double phi = 0; phi < 100; phi += resolution)
        {
            double x = n * phi * Math.cos(phi);
            double y = n * phi * Math.sin(phi);
            graphics.draw(new Line2D.Double(x*scale, y*scale, lastX*scale, lastY*scale));
            lastX = x;
            lastY = y;
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Spiral.class);
    }

}
