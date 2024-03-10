import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Screensaver extends Application {
    private ResizableCanvas canvas;
    private ScreensaverPoint[] screensaverPoints;

    @Override
    public void start(Stage stage) throws Exception
    {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now)
            {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Screensaver");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.BLACK);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());


        graphics.setColor(new Color(255, 0, 200, 100));
        for (ScreensaverPoint screensaverPoint : screensaverPoints) {
            //System.out.println(point.posX +" "+ point.posY +" "+ point);
            screensaverPoint.draw(graphics);
        }
    }

    public void init()
    {
        int pointCount = 5;
        int maxHistory = 30;

        screensaverPoints = new ScreensaverPoint[pointCount];
        Random r = new Random();

        for (int i = 0; i < pointCount; i++) {
            if(i == 0)
                screensaverPoints[0] = new ScreensaverPoint(r.nextInt(500) , r.nextInt(500), null, maxHistory);
            else
                screensaverPoints[i] = new ScreensaverPoint(r.nextInt(500) , r.nextInt(500), screensaverPoints[i-1], maxHistory);
        }
        screensaverPoints[0].setEndpoint(screensaverPoints[pointCount-1]);

    }

    public void update(double deltaTime)
    {
        for (ScreensaverPoint screensaverPoint : screensaverPoints) {
            screensaverPoint.update(canvas);
        }
    }

    public static void main(String[] args)
    {
        launch(Screensaver.class);
    }

}
