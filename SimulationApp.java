import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SimulationApp extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Environment environment;
    private AnimationTimer timer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ecosystem Simulation");

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        environment = new Environment();

        // Sidebar with controls
        VBox controls = new VBox(10);
        controls.setStyle("-fx-padding: 10;");
        Label speedLabel = new Label("Simulation Speed:");
        Slider speedSlider = new Slider(1, 10, 5);

        Label sizeLabel = new Label("Organism Size:");
        Slider sizeSlider = new Slider(5, 50, 20);

        Label speedOrgLabel = new Label("Organism Speed:");
        Slider speedOrgSlider = new Slider(0.5, 5.0, 2.0);

        Label senseLabel = new Label("Sense Radius:");
        Slider senseSlider = new Slider(50, 200, 100);

        Button addOrgButton = new Button("Add Organism");
        Button addFoodButton = new Button("Add Food");

        Button startButton = new Button("Start Simulation");
        Button stopButton = new Button("Stop Simulation");

        controls.getChildren().addAll(
            speedLabel, speedSlider, 
            sizeLabel, sizeSlider, 
            speedOrgLabel, speedOrgSlider, 
            senseLabel, senseSlider, 
            addOrgButton, addFoodButton, 
            startButton, stopButton
        );

        // Set up the root layout
        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setRight(controls);

        Scene scene = new Scene(root, WIDTH + 200, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add initial organisms to the environment
        environment.addOrganism(new Organism(
            20, 2, 100.0, 
            environment.getSafeZoneX(), environment.getSafeZoneY(), 
            environment.getSafeZoneWidth(), environment.getSafeZoneHeight()
        ));
        environment.addOrganism(new Organism(
            30, 1.5, 80.0, 
            environment.getSafeZoneX(), environment.getSafeZoneY(), 
            environment.getSafeZoneWidth(), environment.getSafeZoneHeight()
        ));

        // Add initial food sources
        environment.addFood(new Food(100, 100));
        environment.addFood(new Food(200, 150));
        environment.addFood(new Food(400, 300));

        // Animation loop
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, WIDTH, HEIGHT);

                // Draw the safe zone
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(environment.getSafeZoneX(), environment.getSafeZoneY(), environment.getSafeZoneWidth(), environment.getSafeZoneHeight());

                // Draw food
                gc.setFill(Color.GREEN);
                for (Food food : environment.getFoodSources()) {
                    gc.fillOval(food.getX(), food.getY(), 5, 5);
                }

                // Draw organisms
                gc.setFill(Color.BLUE);
                for (Organism organism : environment.getOrganisms()) {
                    gc.fillOval(organism.getX(), organism.getY(), organism.getSize(), organism.getSize());
                }

                environment.simulateStep();
            }
        };

        // Start button action
        startButton.setOnAction(e -> timer.start());

        // Stop button action
        stopButton.setOnAction(e -> timer.stop());

        // Add organism button action
        addOrgButton.setOnAction(e -> {
            double size = sizeSlider.getValue();
            double speed = speedOrgSlider.getValue();
            double senseRadius = senseSlider.getValue();
            environment.addOrganism(new Organism(
                size, speed, senseRadius, 
                environment.getSafeZoneX(), environment.getSafeZoneY(), 
                environment.getSafeZoneWidth(), environment.getSafeZoneHeight()
            ));
        });

        // Add food button action
        addFoodButton.setOnAction(e -> {
            double x = Math.random() * WIDTH;
            double y = Math.random() * HEIGHT;
            environment.addFood(new Food(x, y));
        });

        // Adjust simulation speed based on slider
        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            timer.stop();
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    gc.clearRect(0, 0, WIDTH, HEIGHT);

                    // Draw the safe zone
                    gc.setFill(Color.LIGHTGRAY);
                    gc.fillRect(environment.getSafeZoneX(), environment.getSafeZoneY(), environment.getSafeZoneWidth(), environment.getSafeZoneHeight());

                    // Draw food
                    gc.setFill(Color.GREEN);
                    for (Food food : environment.getFoodSources()) {
                        gc.fillOval(food.getX(), food.getY(), 5, 5);
                    }

                    // Draw organisms
                    gc.setFill(Color.BLUE);
                    for (Organism organism : environment.getOrganisms()) {
                        gc.fillOval(organism.getX(), organism.getY(), organism.getSize(), organism.getSize());
                    }

                    environment.simulateStep();
                }
            };
            timer.start();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

