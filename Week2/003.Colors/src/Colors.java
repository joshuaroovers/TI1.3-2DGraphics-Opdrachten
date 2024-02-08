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

public class Colors extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Colors");
        primaryStage.show();

    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        double width = canvas.getWidth()/13; // 13 == amount of colors
        //double height = canvas.getHeight();
        double height = width;
        int y = 0;
        drawStandardColours(graphics, y, width, height);
    }

    public void drawStandardColours(FXGraphics2D graphics, int y, double width, double height){
        ArrayList<Color> colors = new ArrayList<>();

        colors.add(Color.MAGENTA);
        colors.add(Color.PINK);
        colors.add(Color.RED);
        colors.add(Color.ORANGE);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.BLUE);
        colors.add(Color.BLACK);
        colors.add(Color.DARK_GRAY);
        colors.add(Color.GRAY);
        colors.add(Color.LIGHT_GRAY);
        colors.add(Color.WHITE);


        for (int i = 0; i < colors.size(); i++) {
            Rectangle2D square = new Rectangle2D.Double(width*i, y, width, height);
            graphics.setColor(colors.get(i));
            graphics.fill(square);
            graphics.draw(square);
        }
    }


    public static void main(String[] args)
    {
        launch(Colors.class);
    }

}
