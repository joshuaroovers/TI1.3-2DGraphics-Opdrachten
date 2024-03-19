
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;



public class VerletEngine extends Application {

    private ResizableCanvas canvas;
    private ParticlesAndConstraintsSet data;
//    private ArrayList<Particle> particles = new ArrayList<>();
//    private ArrayList<Constraint> constraints = new ArrayList<>();
    private PositionConstraint mouseConstraint = new PositionConstraint(null);

    private TextField clothColumnsInput;
    private TextField clothRowsInput;
    private TextField clothMarginInput;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();

        Button saveButton = new Button("save");
        saveButton.setOnAction(event -> {
            IOController.saveObjectToFile("dataSet", data);
            IOController.saveObjectToFile("dataSet", data);
        });
        Button loadButton = new Button("load");
        loadButton.setOnAction(event -> {
            data = (ParticlesAndConstraintsSet) IOController.getObjectFromFile("dataSet");
//            for (Object particle : (ArrayList<Object>) IOController.getObjectFromFile("particles")) {
//                particles.add((Particle)particle);
//            }
//            for(Object constraint : (ArrayList<Object>) IOController.getObjectFromFile("constraints")){
//                if(constraint.getClass().equals(DistanceConstraint.class)) {
//                    constraints.add((DistanceConstraint) constraint);
//                }else if(constraint.getClass().equals(PositionConstraint.class)) {
//                    constraints.add((PositionConstraint) constraint);
//                }else if(constraint.getClass().equals(RopeConstraint.class)) {
//                    constraints.add((RopeConstraint) constraint);
//                }
//            }
//            System.out.println(constraints.get(0).getParticle().getPosition());

            data.addConstraint(mouseConstraint);
        });

        HBox clothConfigVBox = new HBox(20);

        Label clothConfigLabel = new Label("Cloth:");

        HBox columnsContainer = new HBox(5);
        Label clothColumnsLabel = new Label("Columns:");
        clothColumnsInput = new TextField();
        clothColumnsInput.setPrefWidth(50);
        columnsContainer.getChildren().addAll(clothColumnsLabel,clothColumnsInput);

        HBox rowsContainer = new HBox(5);
        Label clothRowsLabel = new Label("Rows:");
        clothRowsInput = new TextField();
        clothRowsInput.setPrefWidth(50);
        rowsContainer.getChildren().addAll(clothRowsLabel,clothRowsInput);

        HBox marginContainer = new HBox(5);
        Label clothMarginLabel = new Label("Margin:");
        clothMarginInput = new TextField();
        clothMarginInput.setPrefWidth(50);
        marginContainer.getChildren().addAll(clothMarginLabel,clothMarginInput);


        clothConfigVBox.getChildren().addAll(clothConfigLabel, columnsContainer, rowsContainer, marginContainer);

        Label controlsInfo = new Label("i");
        controlsInfo.setStyle("-fx-border-radius: 50; -fx-border-color: black; -fx-padding: 0 8; -fx-font-weight: bold;");
        Tooltip tooltip = new Tooltip(
                "Controls:" +"\n"+
                        "middle mouse button:  reset" +"\n"+
                        "alt+click:            create new cloth" +"\n"+
                        "left click:           add new particle (one connector)" +"\n"+
                        "right click:          add new particle (two connectors)" +"\n"+
                        "ctrl+left click:      add new particle with fixed position" +"\n"+
                        "ctrl+right click:     add new particle (two connectors with distance 100)" +"\n"+
                        "shift+left click:     add new particle with rope" +"\n"+
                        "shift+right click:    connect two closest particles" +"\n"
        );
        Tooltip.install(controlsInfo, tooltip);

        FlowPane menuPane = new FlowPane(saveButton,loadButton, clothConfigVBox, controlsInfo);
        menuPane.setHgap(20);
        mainPane.setTop(menuPane);




        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        // Mouse Events
        canvas.setOnMouseClicked(e -> mouseClicked(e));
        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Verlet Engine");
        stage.show();
        draw(g2d);
    }

    public void init() {
        IOController.init();

        data = new ParticlesAndConstraintsSet();

        data.addParticle(new Particle(new Point2D.Double(50,50)));
        data.addConstraint(new PositionConstraint(data.getParticles().get(0)));

        data.addParticle(new Particle(new Point2D.Double(100,50)));
        data.addConstraint(new PositionConstraint(data.getParticles().get(1)));

        data.addConstraint(mouseConstraint);
    }

    private void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        for (Constraint c : data.getConstraints()) {
            c.draw(graphics);
        }

        for (Particle p : data.getParticles()) {
            p.draw(graphics);
        }
    }

    private void update(double deltaTime) {
        for (Particle p : data.getParticles()) {
            p.update((int) canvas.getWidth(), (int) canvas.getHeight());
        }

        for (Constraint c : data.getConstraints()) {
            c.satisfy();
        }
    }

    private void mouseClicked(MouseEvent e) {

        Point2D mousePosition = new Point2D.Double(e.getX(), e.getY());
        Particle nearest = getNearest(mousePosition);

        if (nearest.getPosition().distance(mousePosition) > 10) {
            if(e.isAltDown()){
                int columns = Integer.parseInt(clothColumnsInput.getText());
                int rows = Integer.parseInt(clothRowsInput.getText());
                int margin = Integer.parseInt(clothMarginInput.getText());

                if(columns > 0 && rows > 0 && margin >= 0){
                    ArrayList<Particle> clothParticles = new ArrayList<>();
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < columns; j++) {
                            Particle particle = new Particle(new Point2D.Double(mousePosition.getX()+((margin+50)*j) ,mousePosition.getY() +((margin+50)*i) ));
                            clothParticles.add(particle);

                            data.addParticle(particle);
                        }
                    }

//                    ArrayList<Constraint> clothConstraints = new ArrayList<>();
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < columns; j++) {
                            if(i == 0){//first row gets positionConstraint
                                data.addConstraint(new PositionConstraint(clothParticles.get(i+j)));
                            }else{
                                //Rope constraint current with particle above it
                                data.addConstraint(new RopeConstraint(clothParticles.get(i*rows+j),clothParticles.get((i-1)*rows+j)));
                                if(j != columns-1){//if it isn't the last on in the row add rope constraint to the next particle to the right
                                    data.addConstraint(new RopeConstraint(clothParticles.get(i*rows+j),clothParticles.get(i*rows+j+1)));
                                }
                            }
                        }
                    }

                }
            }
            else {
                if (!e.isShiftDown()) {
                    Particle newParticle = new Particle(mousePosition);
                    data.addParticle(newParticle);

                    if (e.getButton() == MouseButton.PRIMARY) {
                        if (e.isControlDown()) {
                            data.addConstraint(new PositionConstraint(newParticle));
                        } else {
                            data.addConstraint(new DistanceConstraint(newParticle, nearest));
                        }
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        ArrayList<Particle> sorted = new ArrayList<>();
                        sorted.addAll(data.getParticles());

                        //sorteer alle elementen op afstand tot de muiscursor. De toegevoegde particle staat op 0, de nearest op 1, en de derde op 2
                        Collections.sort(sorted, new Comparator<Particle>() {
                            @Override
                            public int compare(Particle o1, Particle o2) {
                                return (int) (o1.getPosition().distance(mousePosition) - o2.getPosition().distance(mousePosition));
                            }
                        });

                        if (e.isControlDown()) {
                            data.addConstraint(new DistanceConstraint(newParticle, nearest, 100));
                            data.addConstraint(new DistanceConstraint(newParticle, sorted.get(2), 100));
                        } else {
                            data.addConstraint(new DistanceConstraint(newParticle, nearest));
                            data.addConstraint(new DistanceConstraint(newParticle, sorted.get(2)));
                        }

                    } else if (e.getButton() == MouseButton.MIDDLE) {

                        // Reset
                        data.getParticles().clear();
                        data.getConstraints().clear();
                        init();
                    }
                }
                else {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        Particle newParticle = new Particle(mousePosition);
                        data.addParticle(newParticle);
                        data.addConstraint(new RopeConstraint(newParticle, nearest));
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        ArrayList<Particle> sorted = new ArrayList<>();
                        sorted.addAll(data.getParticles());

                        //sorteer alle elementen op afstand tot de muiscursor. De toegevoegde particle staat op 0, de nearest op 1, en de derde op 2
                        Collections.sort(sorted, new Comparator<Particle>() {
                            @Override
                            public int compare(Particle o1, Particle o2) {
                                return (int) (o1.getPosition().distance(mousePosition) - o2.getPosition().distance(mousePosition));
                            }
                        });

                        data.addConstraint(new DistanceConstraint(sorted.get(0), sorted.get(1)));
                    }
                }
            }
        }
    }

    private Particle getNearest(Point2D point) {
        Particle nearest = data.getParticles().get(0);
        for (Particle p : data.getParticles()) {
            if (p.getPosition().distance(point) < nearest.getPosition().distance(point)) {
                nearest = p;
            }
        }
        return nearest;
    }

    private void mousePressed(MouseEvent e) {
        Point2D mousePosition = new Point2D.Double(e.getX(), e.getY());
        Particle nearest = getNearest(mousePosition);
        if (nearest.getPosition().distance(mousePosition) < 10) {
            mouseConstraint.setParticle(nearest);
        }
    }

    private void mouseReleased(MouseEvent e) {
        mouseConstraint.setParticle(null);
    }

    private void mouseDragged(MouseEvent e) {
        mouseConstraint.setFixedPosition(new Point2D.Double(e.getX(), e.getY()));
    }

    public static void main(String[] args) {
        launch(VerletEngine.class);
    }

}
