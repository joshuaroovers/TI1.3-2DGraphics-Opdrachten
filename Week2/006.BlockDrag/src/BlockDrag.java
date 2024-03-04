import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class BlockDrag extends Application {
    ResizableCanvas canvas;
    ArrayList<Block> renderables;
    Block selected;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        renderables = new ArrayList<>();
        renderables.add(new Block(0,0,100,Color.BLUE, new Rectangle2D.Double(0,0,100,100)));
        renderables.add(new Block(100,0,100,Color.RED, new Rectangle2D.Double(100,0,100,100)));
        renderables.add(new Block(0,100,100,Color.GREEN, new Rectangle2D.Double(0,100,100,100)));
        renderables.add(new Block(100,100,100,Color.YELLOW, new Rectangle2D.Double(100,100,100,100)));

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Block Dragging");
        primaryStage.show();

        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        for(Block ren : renderables){
            ren.draw(graphics);
        }

    }


    public static void main(String[] args)
    {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e)
    {
        for(Block ren : renderables){
            if(ren.contains(e.getX(),e.getY())){
                selected = ren;
                draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
            }
        }
    }

    private void mouseReleased(MouseEvent e)
    {
        selected = null;
    }

    private void mouseDragged(MouseEvent e)
    {
        if(selected != null) {
            selected.setPos(e.getX(), e.getY());
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        }
    }

}
