import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Spotlight extends Application {
    private ResizableCanvas canvas;
    private Stage mainStage;
    private ClippingShape[] clips;
    private ClippingShape currentClip;
    private int currentClipIndex = 0;
    private Line2D[] background;
    private Color[] backgroundColors;

    @Override
    public void start(Stage stage) throws Exception
    {
        mainStage = stage;
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());


        canvas.setOnMousePressed(e -> mousePressed(e));

        Random r = new Random();
        int lines = 500;
        backgroundColors = new Color[lines];
        background = new Line2D[lines];
        for(int i = 0; i < lines; i++) {
            Color color = Color.getHSBColor(r.nextFloat(),1,1);
            backgroundColors[i] = color;
            background[i] = new Line2D.Double((int) (r.nextInt() % canvas.getWidth()), (int) (r.nextInt() % canvas.getHeight()), (int) (r.nextInt() % canvas.getWidth()), (int) (r.nextInt() % canvas.getHeight()));
        }

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
        stage.setTitle("Spotlight");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics)
    {

        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.setColor(Color.BLACK);
        graphics.draw(currentClip.getClip());
        graphics.setClip(currentClip.getClip());

        for (int i = 0; i < background.length; i++) {
            graphics.setColor(backgroundColors[i]);
            graphics.draw(background[i]);
        }

        graphics.setClip(null);
    }


    private void mousePressed(MouseEvent e)
    {
        currentClipIndex++;
        if(currentClipIndex >= clips.length){
            currentClipIndex =0;
        }
        currentClip = clips[currentClipIndex];
    }

    public void init()
    {
        int size = 200;
        clips = new ClippingShape[4];
        clips[0] = new ClippingShapeCircle(new Point2D.Double(MouseInfo.getPointerInfo().getLocation().getX()-(size/2),MouseInfo.getPointerInfo().getLocation().getY()-(size/2)), size);
        clips[1] = new ClippingShapeSquare(new Point2D.Double(MouseInfo.getPointerInfo().getLocation().getX()-(size/2),MouseInfo.getPointerInfo().getLocation().getY()-(size/2)), size);
        clips[2] = new ClippingShapeCircle(new Point2D.Double(MouseInfo.getPointerInfo().getLocation().getX()-(size),MouseInfo.getPointerInfo().getLocation().getY()-(size)), size*2);
        clips[3] = new ClippingShapeSquare(new Point2D.Double(MouseInfo.getPointerInfo().getLocation().getX()-(size),MouseInfo.getPointerInfo().getLocation().getY()-(size)), size*2);

        currentClip = clips[currentClipIndex];
    }


    public void update(double deltaTime)
    {
        currentClip.update(new Point2D.Double(MouseInfo.getPointerInfo().getLocation().getX()-(currentClip.size/2)-mainStage.getX(),MouseInfo.getPointerInfo().getLocation().getY()-(currentClip.size/2)-mainStage.getY()));
    }

    public static void main(String[] args)
    {
        launch(Spotlight.class);
    }

}
