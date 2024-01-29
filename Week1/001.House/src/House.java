import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class House extends Application {
    private int margin = 50;
    private int houseWidth = 398;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(margin*2+312, margin*2+398);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        Scene scene =  new Scene(new Group(canvas));
        primaryStage.setScene(scene);
        primaryStage.setTitle("House");
        primaryStage.setOpacity(0.8); // sets whole window to setOpacity
        //primaryStage.initStyle(StageStyle.TRANSPARENT); //hides top window bar
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {

        ArrayList<Color> testColors = new ArrayList<>();
        testColors.add(Color.RED);
        testColors.add(Color.BLUE);
        testColors.add(Color.GREEN);
        testColors.add(Color.MAGENTA);

        ArrayList<Integer> houseBorder = new ArrayList<>();
        int houseWidth = 312;
        int houseBaseHeight = 398;
        int roofHeight = 213;

        houseBorder.add(houseWidth/2);houseBorder.add(0);
        houseBorder.add(0);houseBorder.add(roofHeight);
        houseBorder.add(0);houseBorder.add(houseBaseHeight);
        houseBorder.add(houseWidth);houseBorder.add(houseBaseHeight);
        houseBorder.add(houseWidth);houseBorder.add(roofHeight);

        ArrayList<Integer> window = new ArrayList<>();
        int windowWidth = 147;
        int windowHeight = 92;

        window.add(0);window.add(0);
        window.add(windowWidth);window.add(0);
        window.add(windowWidth);window.add(windowHeight);
        window.add(0);window.add(windowHeight);

        ArrayList<Integer> door = new ArrayList<>();
        int doorWidth = 60;
        int doorHeight = 110;

        door.add(0);door.add(0);
        door.add(0);door.add(doorHeight);
        door.add(doorWidth);door.add(doorHeight);
        door.add(doorWidth);door.add(0);

        drawShape(houseBorder, graphics, margin, margin);
        drawShape(window, graphics, margin+135, margin+258);
        drawShape(door, graphics, margin+32, margin+houseBaseHeight-doorHeight);
    }

    public void drawShape(ArrayList<Integer> shape, ArrayList<Color> colors, FXGraphics2D graphics, int offsetX, int offsetY){

        for (int i = 0; i < shape.size(); i+=2 ) {
            graphics.setColor(colors.get((i/2)));
            if(i >= shape.size()-2){
                graphics.draw(new Line2D.Double(shape.get(i)+offsetX, shape.get(i+1)+offsetY, shape.get(0)+offsetX, shape.get(1)+offsetY));

            }else {
                graphics.draw(new Line2D.Double(shape.get(i)+offsetX, shape.get(i+1)+offsetY, shape.get(i+2)+offsetX, shape.get(i + 3)+offsetY));
            }
        }
    }

    public void drawShape(ArrayList<Integer> shape, FXGraphics2D graphics , int offsetX, int offsetY){
        ArrayList<Color> black = new ArrayList<>();
        for (int i = 0; i < shape.size(); i++) {
            black.add(Color.BLACK);
        }
        drawShape(shape, black, graphics , offsetX, offsetY);
    }




    public static void main(String[] args) {
        launch(House.class);
    }

}
