package graph;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class Node extends StackPane {
    Node (Object label, int x, int y, int radius) {
        // Create a circle and set color properties
        Circle circle = new Circle(radius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        // Text is a number of node
        Text text = new Text(label.toString());
        text.setFont(Font.font(18));
        // Create a group of circle and text and set coordinates
        this.getChildren().addAll(circle, text);
        this.setLayoutX(x - radius);
        this.setLayoutY(y - radius);
    }
}

public class Graph extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Graph");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
