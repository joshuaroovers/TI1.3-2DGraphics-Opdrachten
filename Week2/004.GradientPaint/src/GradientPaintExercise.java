import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static java.awt.SystemColor.window;
import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g,canvas.getWidth()/2,canvas.getHeight()/2), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),canvas.getWidth()/2,canvas.getHeight()/2);
        canvas.setOnMouseDragged(e ->{

            draw(new FXGraphics2D(canvas.getGraphicsContext2D()), MouseInfo.getPointerInfo().getLocation().x-primaryStage.getX(), MouseInfo.getPointerInfo().getLocation().y-primaryStage.getY());
        });
    }


    public void draw(FXGraphics2D graphics, double focusX, double focusY)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        drawGradiant(graphics, focusX, focusY);
    }


    public void drawGradiant(FXGraphics2D graphics, double focusX, double focusY){
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        Rectangle2D rectangle = new Rectangle2D.Double(0,0, width, height);

        Point2D focus = new Point2D.Double(focusX,focusY);
        float radius = 50;
        float[] dist = {0.16f, 0.32f, 0.48f, 0.64f, 0.80f, 0.96f};
        Color[] colors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
        RadialGradientPaint paint = new RadialGradientPaint(focus, radius, dist, colors, MultipleGradientPaint.CycleMethod.REPEAT);
        graphics.setPaint(paint);
        graphics.fill(rectangle);
        graphics.draw(rectangle);
    }


    public static void main(String[] args)
    {
        launch(GradientPaintExercise.class);
    }

}
