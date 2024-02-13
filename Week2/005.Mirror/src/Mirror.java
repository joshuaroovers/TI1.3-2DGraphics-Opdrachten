import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Mirror extends Application {
    ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());


        graphics.translate((int) canvas.getWidth()/2, (int) canvas.getHeight()-5);
        graphics.scale( 1, -1);

        graphics.setColor(Color.RED);
        graphics.drawLine(0,0,1000,0);
        graphics.setColor(Color.GREEN);
        graphics.drawLine(0,0,0,1000);
        graphics.setColor(Color.BLACK);

        double resolution = 0.1;
        double scale = 20;
        double lastY = 2.5*0;
        //  Y = 2.5 × X
        for(double x = 0; x < 10; x += resolution)
        {
            double y = 2.5*x;
            graphics.draw(new Line2D.Double(x*scale, y*scale, (x-resolution)*scale, lastY*scale));
            lastY = y;
        }
        int squareSize = 100;
        int cornerX = 0-(squareSize/2); //center point - half width to get x-coordinate
        int cornerY = 150-(squareSize/2); //center point - half height to get y-coordinate
//        Rectangle2D square = new Rectangle2D.Double(cornerX,cornerY, squareSize, squareSize);
//        graphics.draw(square);
//        Rectangle2D square2 = new Rectangle2D.Double(0-5,150-5, 10, 10);
//        graphics.draw(square2);
        Color[] colors = new Color[4];
        colors[0] = Color.RED;
        colors[1] = Color.GREEN;
        colors[2] = Color.BLUE;
        colors[3] = Color.ORANGE;

        drawColorSquare(graphics, cornerX, cornerY, 100, colors);
        drawColorSquare(graphics, cornerX, cornerY, 100, colors, getMirrorTransform(2.5));
    }

    public void drawColorSquare(Graphics2D g2d, double posX, double posY, double size, Color[] colors, AffineTransform transform){
        Line2D[] lines = new Line2D[4];

        Line2D line1 = new Line2D.Double(posX,posY,posX+size,posY);
        lines[0] = line1;
        Line2D line2 = new Line2D.Double(posX+size,posY,posX+size,posY+size);
        lines[1] = line2;
        Line2D line3 = new Line2D.Double(posX+size,posY+size,posX,posY+size);
        lines[2] = line3;
        Line2D line4 = new Line2D.Double(posX,posY+size,posX,posY);
        lines[3] = line4;

        for (int i = 0; i < 4; i++) {
            g2d.setColor(colors[i]);
            g2d.fill(lines[i]);
            g2d.draw(transform.createTransformedShape(lines[i]));
        }
    }
    public void drawColorSquare(Graphics2D g2d, int posX, int posY, int size, Color[] colors){
        drawColorSquare(g2d, posX, posY, size, colors, new AffineTransform());
    }

    public AffineTransform getMirrorTransform(double k){
//        Y = K*X
//        ( 2/(1+k^2-1)   2k/(1+k^2)       0 )
//        ( 2k/(1+k^2)  (2k^2/(1+k^2))-1   0 )
//        (     0              0           1 )

//        ( math1 math2 0 )  (m00 m01 m02)
//        ( math3 math4 0 )  (m10 m11 m12)   AffineTransform(m00,m10,m01,m11,m02,m12)
//        (   0     0   1 )  ( 0   0   1 )

        float math1 = (float) ((2/(1+Math.pow(k,2)))-1);
        float math2 = (float) ((2*k)/(1+Math.pow(k,2)));
        float math3 = (float) ((2*k)/(1+Math.pow(k,2)));
        float math4 = (float) ((2*Math.pow(k,2))/(1+Math.pow(k,2))-1);

        return new AffineTransform(math1,math3,math2,math4,0,0);
    }


    public static void main(String[] args)
    {
        launch(Mirror.class);
    }

}
