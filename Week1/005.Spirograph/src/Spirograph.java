import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Spirograph extends Application {
    private TextField v1;
    private TextField v2;
    private TextField v3;
    private TextField v4;
    private Canvas canvas;
    private  VBox mainBox;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas = new Canvas(1080-100, 1080-100);
       
        mainBox = new VBox();
        HBox topBar = new HBox();
        mainBox.getChildren().add(topBar);
        mainBox.getChildren().add(canvas);
        
        topBar.getChildren().add(v1 = new TextField("200")); //main arm offset
        topBar.getChildren().add(v2 = new TextField("1"));  //main arm angle
        topBar.getChildren().add(v3 = new TextField("400")); //second arm offset
        topBar.getChildren().add(v4 = new TextField("4")); //second arm angle (# of loops-1?)

        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        graphics.translate(canvas.getWidth()/2, canvas.getHeight()/2); //using translate in draw() keeps moving it further
        graphics.scale( 0.8, 0.8);
                
        v1.textProperty().addListener(e -> {
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        });
        v2.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v3.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v4.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
    }

    //you can use Double.parseDouble(v1.getText()) to get a double value from the first textfield

    public void draw(FXGraphics2D graphics) {
//        mainBox.getChildren().remove(1);
//        this.canvas = new Canvas(1080-100, 1080-100);
//        mainBox.getChildren().add(canvas);


//        graphics.setColor(Color.red);
//        graphics.drawLine(0,0,1000,0);
//        graphics.setColor(Color.green);
//        graphics.drawLine(0,0,0,1000);
        graphics.setColor(Color.getHSBColor((float) Math.random(), 1.0f, 1.0f));

        double resolution = 0.1;
        double scale = 0.9;

        double a = Double.parseDouble(v1.getText());
        double b = Double.parseDouble(v2.getText());
        double c = Double.parseDouble(v3.getText());
        double d = Double.parseDouble(v4.getText());

        float lastX = (float) (a * cos(Math.toRadians(b) * 0) + c * cos(Math.toRadians(d) * 0));
        float lastY = (float) (a * sin(Math.toRadians(b) * 0) + c * sin(Math.toRadians(d) * 0));

//        double rad1 = Math.toRadians(b);
//        double rad2 = Math.toRadians(d);
//        float x1 = (float) (a * Math.cos(rad1*0));
//        float y1 = (float) (a * Math.sin(rad1*0));
//        float x2 = (float) (c * Math.cos(rad2*0));
//        float y2 = (float) (c * Math.sin(rad2*0));
//
//        float lastX = x1 + x2;
//        float lastY = y1 + y2;

        for(double phi = 0; phi < 10000; phi += resolution)
        {
//             rad1 = Math.toRadians(b);
//             rad2 = Math.toRadians(d);
//             x1 = (float) (a * Math.cos(rad1*phi));
//             y1 = (float) (a * Math.sin(rad1*phi));
//             x2 = (float) (c * Math.cos(rad2*phi));
//             y2 = (float) (c * Math.sin(rad2*phi));
//
//            float x = x1 + x2;
//            float y = y1 + y2;
            float x = (float) (a * cos(Math.toRadians(b) * phi) + c * cos(Math.toRadians(d) * phi));
            float y = (float) (a * sin(Math.toRadians(b) * phi) + c * sin(Math.toRadians(d) * phi));
            graphics.draw(new Line2D.Double(x*scale, y*scale, lastX*scale, lastY*scale));
            lastX = x;
            lastY = y;
        }
    }




    public static void main(String[] args) {
        launch(Spirograph.class);
    }

}
