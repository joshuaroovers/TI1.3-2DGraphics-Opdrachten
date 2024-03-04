import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private SpriteSheet character;
    double count = 0;
    double angle;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        
        character = new SpriteSheet((8*8+1),8,9, (int)canvas.getWidth()/2, (int)canvas.getHeight()/2);
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
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

//        character.draw(graphics);
        AffineTransform tx = new AffineTransform();
        tx.translate(canvas.getWidth()/2, canvas.getHeight()/2);
        tx.rotate(Math.toRadians(angle));
        tx.translate(200, 0);
        graphics.fill(tx.createTransformedShape(new Rectangle2D.Double(-50,-50,100,100)));
    }


    public void update(double deltaTime)
    {
        count+= deltaTime;
//        character.frameStep();
        System.out.println(deltaTime);
        angle+=count;
    }

    public static void main(String[] args)
    {
        launch(MovingCharacter.class);
    }

}
