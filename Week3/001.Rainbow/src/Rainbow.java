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

import javax.tools.Tool;

public class Rainbow extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());


        int canvasW = (int) canvas.getWidth();
        int canvasH = (int) canvas.getHeight();
        String text = "Programming Is My Passion";
        int fontSize = 60;
        int distance = 250;
        boolean guidelines = true;
        Font font = new Font(Font.MONOSPACED, Font.BOLD,fontSize);
        graphics.setFont(font);

        if(guidelines){
            Ellipse2D circle = new Ellipse2D.Double(canvasW/2-(distance), canvasH/4*3-(distance), distance*2,distance*2);
            graphics.draw(circle);
            graphics.draw(new Line2D.Double(0, canvasH/4*3, canvasW,canvasH/4*3));
            graphics.draw(new Line2D.Double(canvasW/2,0,canvasW/2,1000));
        }

        drawLetterRainbow(graphics, canvasW/2, canvasH/4*3, distance,text, font);
    }

    public void drawLetterRainbow(FXGraphics2D g2d, int originX, int originY, int distance, String string, Font font){

        //there's so many numbers here and I apologise but I promise they made sense at one point!

        //maxRotateOffset is the width of the last gyphvector that gets deducted from the rotation to keep it from falling over the 180
        double maxRotateOffset = (font.createGlyphVector(g2d.getFontRenderContext(), string.substring(string.length()-1)).getOutline().getBounds().width)/3.7;
        double rotation = 180 -maxRotateOffset;
        //leftOver is the amount of degrees left over until the full rotation that's divided and added to the glyphs in an attempt to get the full rotation
        double leftOver = (rotation / string.length() / string.length());
        System.out.println(leftOver);
        //the step for i is what it is to evenly divide the amount of degrees over the amount of characters
        for(double i = 0; i <= rotation; i+=(rotation/string.length())+ leftOver ){

            System.out.println("rotation: "+i);

            g2d.setColor(Color.getHSBColor((float) -(i/rotation),1.0f,1.0f));
            System.out.println("color: "+(i/rotation));

            //j is the reversal of the i and is used to iterate over the letters
            int j = (int)(i*string.length()/(rotation+ leftOver));
            System.out.println("letter index: "+(i*string.length()/(rotation+ leftOver)));
            //don't know if this is necessary anymore, but it can't hurt
            if(j < string.length()){
                //make new shape with a GlyphVector and the current iteration of the string using substring(j,j+1)
                Shape shape = font.createGlyphVector(g2d.getFontRenderContext(), string.substring(j,j+1)).getOutline();


                AffineTransform transform = new AffineTransform();
                transform.translate(originX,originY); //set glyph to origin
                transform.rotate(Math.toRadians(i-90)); //rotate glyph
                transform.translate(0,-distance); //move glyph (in rotate direction!! :) a happy accident)
                shape = transform.createTransformedShape(shape);
                g2d.fill(shape);
                g2d.setColor(Color.BLACK);
                g2d.draw(shape);
            }

        }
    }





    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }

}
