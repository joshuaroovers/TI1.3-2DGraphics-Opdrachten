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

public class YingYang extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Ying Yang");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        drawYinYang(graphics, 50, 50, 200, Color.WHITE, Color.BLACK);
        //drawYinYang(graphics, 255, 50, 200, Color.WHITE, Color.GREEN);
    }

    public static void drawYinYang(FXGraphics2D graphics, int x, int y, int size, Color colorBase, Color colorSecondary){
        GeneralPath path = new GeneralPath();
        path.moveTo(x+(size/2),y);
        path.curveTo(size*1.162+x, y, size*1.162+x,size+y, x+(size/2),size+y);
        path.closePath();

        Area body = new Area(path);
        Area circleBase = new Area(new Ellipse2D.Double(x,y,size,size));
        Area circleSubtract = new Area(new Ellipse2D.Double(x+(size/4),y, size/2, size/2));
        Area circleAdd = new Area(new Ellipse2D.Double(x+(size/4),y+(size/2), size/2, size/2));
        Area smallCircleSubtract = new Area(new Ellipse2D.Double(x+((size/2)-(size/16)), y+((size/2)+(size/16*3)), size/8, size/8));
        Area smallCircleAdd = new Area(new Ellipse2D.Double(x+((size/2)-(size/16)), y+(size/16*3), size/8, size/8));

        body.add(circleAdd);
        body.subtract(circleSubtract);
        body.subtract(smallCircleSubtract);
        body.add(smallCircleAdd);

        graphics.setColor(colorBase);
        graphics.fill(circleBase);

        graphics.setColor(colorSecondary);
        graphics.fill(body);

        graphics.draw(circleBase);
        graphics.draw(body);
    }
    public static void main(String[] args)
    {
        launch(YingYang.class);
    }

}
