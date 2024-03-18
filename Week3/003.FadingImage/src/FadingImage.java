import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

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

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    private ArrayList<FadingImageSet> imageSets;
    
    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        mainPane.setPrefWidth(650);  //todo tried resizing the canvas but that gave an error because of the immediate draw() call.
        mainPane.setPrefHeight(650);
        canvas = new ResizableCanvas(g -> draw(g), mainPane);

        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        imageSets = new ArrayList<>();

        FadingImageSet imageSet1 = new FadingImageSet(0, 0, (int)canvas.getWidth()/2, (int)canvas.getHeight()/2, 0.5);
        imageSets.add(imageSet1);
        imageSet1.addImage("Olarya Fallen.png");
        imageSet1.addImage("Olarya Weaveling 2.png");
        imageSet1.addImage("Olarya Fallen.png");
        imageSet1.addImage("Olarya Ganaro.png");

        FadingImageSet imageSet2 = new FadingImageSet((int)canvas.getWidth()/2, 0, (int)canvas.getWidth()/2, (int)canvas.getHeight()/2);
        imageSets.add(imageSet2);
        imageSet2.addImage("Eurydice Nightmare.png");
        imageSet2.addImage("Eurydice Onasis.png");

        FadingImageSet imageSet3 = new FadingImageSet(0, (int)canvas.getWidth()/2, (int)canvas.getWidth()/2, (int)canvas.getHeight()/2);
        imageSets.add(imageSet3);
        imageSet3.addImage("Cleopatra.png");
        imageSet3.addImage("Cleo.png");

        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
		if(last == -1)
                    last = now;
		update((now - last) / 1000000000.0);
		last = now;
		draw(g2d);
            }
        }.start();
        
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        for (FadingImageSet imageSet : imageSets) {
            if(imageSet != null)
                imageSet.draw(graphics);
        }
    }
    

    public void update(double deltaTime) {
        for (FadingImageSet imageSet : imageSets) {
            imageSet.update(deltaTime);
        }

    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
