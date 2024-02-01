import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;

public class Rainbow extends Application {
    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.canvas = new Canvas(1920-800, 1080-500);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Rainbow");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {

        graphics.translate(this.canvas.getWidth()/2, this.canvas.getHeight()-50);
        graphics.scale( -0.3, -0.3);

//        graphics.setColor(Color.red);
//        graphics.drawLine(0,0,1000,0);
//        graphics.setColor(Color.green);
//        graphics.drawLine(0,0,0,1000);
//        graphics.setColor(Color.black);


        double resolution = 0.05;
        double scale = 500.0;

        double radiusBinnen = 1.2;
        double radiusBuiten = 2.2;

        graphics.draw(new Line2D.Double(0.5*scale, 0*scale, 3.5*scale, 0*scale));
        graphics.draw(new Line2D.Double(0*scale, 1*scale, 0*scale, 3.5*scale));
        graphics.draw(new Line2D.Double(-0.5*scale, 0*scale, -3.5*scale, 0*scale));

        for(double hoek = 0; hoek <= 180; hoek+=resolution)
        {

            graphics.setColor(Color.getHSBColor((float) -(hoek/180),1.0f,1.0f));

            double rad = Math.toRadians(hoek);
            float x1 = (float) (radiusBuiten * Math.cos(rad));
            float y1 = (float) (radiusBuiten * Math.sin(rad));
            float x2 = (float) (radiusBinnen * Math.cos(rad));
            float y2 = (float) (radiusBinnen * Math.sin(rad));

            graphics.draw(new Line2D.Double(x1*scale, y1*scale, x2*scale, y2*scale));
        }
    }




    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
