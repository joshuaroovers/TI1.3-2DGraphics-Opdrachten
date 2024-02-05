import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Moon extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Moon");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        //graphics.translate(150,150);
        graphics.setColor(Color.red);
        graphics.drawLine(150,150,1000,150);
        graphics.setColor(Color.green);
        graphics.drawLine(150,150,150,1000);
        graphics.setColor(Color.black);
        //graphics.translate(-150,-150);


        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        int size = 200;
        int offset = 100;

        Shape moon = moon(offset,offset, size);
        graphics.setColor(Color.BLACK);
        graphics.fill(moon);
        graphics.draw(moon);


        //QuadCurve2D.Double(double x1, double y1, double ctrlx, double ctrly, double x2, double y2)
//        GeneralPath path = new GeneralPath();
//        path.moveTo(offset+(circleSize/2),offset);
//        //path.curveTo(circleSize*1.2+offset, offset, circleSize*1.2+offset,circleSize+offset, offset+(circleSize/2),circleSize+offset);
////        path.moveTo(offset+(circleSize/2), offset);
////        //quadTo(Xsupport,Ysupport, XendPoint, YendPoint);
//        //path.quadTo(offset+(circleSize*1.5), offset+(circleSize/2), (circleSize/2)+offset, circleSize+offset);
////        path.quadTo(0+offset+circleSize,0+offset+circleSize, -circleSize/4+offset,circleSize*2-(circleSize/4)+offset);
////        path.quadTo(circleSize*2,circleSize*2, 0+offset,0+offset);
//        path.closePath();
//        graphics.setColor(Color.green);
//        graphics.fill(path);
//        graphics.setColor(Color.black);
//        graphics.draw(path);


    }

    public static Shape moon(int x, int y, int size){
        Area body = new Area(new Ellipse2D.Double(x,y, size, size));
        Area cutter = new Area(new Ellipse2D.Double(x/2,y*0.75, size, size));

        body.subtract(cutter);

        return body;
    }


    public static void main(String[] args)
    {
        launch(Moon.class);
    }

}
