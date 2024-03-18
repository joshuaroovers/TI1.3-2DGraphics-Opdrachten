import java.awt.*;
import java.awt.geom.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private Character character;
    double count = 0;
    double angle;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        
        character = new Character("/images/sprite.png",(8*8+1),8,9, /*(int)canvas.getWidth()/2*/ 0, (int)canvas.getHeight()/2);
//        character = new SpriteSheet("/images/testingSprite.png",(8*8),8,8, /*(int)canvas.getWidth()/2*/0, (int)canvas.getHeight()/2);

        canvas.setOnMousePressed(e -> mousePressed(e));

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now)
            {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                draw(g2d);
                //System.out.println(now-last);
                last = now;

            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);

        character.drawSpriteSheet(g2d);
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        //character.drawSpriteSheet(graphics);
        character.draw(graphics);

//        AffineTransform tx = new AffineTransform();
//        tx.translate(canvas.getWidth()/2, canvas.getHeight()/2);
//        tx.rotate(Math.toRadians(angle));
//        tx.translate(200, 0);
//        graphics.fill(tx.createTransformedShape(new Rectangle2D.Double(-50,-50,100,100)));
    }




    public void update(double deltaTime)
    {
//        count+= deltaTime/10;
        character.update(deltaTime*15);
        //this.draw(new FXGraphics2D());
//        System.out.println(deltaTime);
//        angle+=count;
    }

    private void mousePressed(MouseEvent e)
    {
        character.setState(Character.characterState.JUMPING);
    }

    public static void main(String[] args)
    {
        launch(MovingCharacter.class);
    }

}
