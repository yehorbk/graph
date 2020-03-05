package graph;

import graph.controllers.WindowBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

public class Graph extends Application {
    
    private final int studentIdentifier = 9405;
    private final int vertexCount = 10;
    
    @Override
    public void start(Stage primaryStage) {
        WindowBuilder windowBuilder = new WindowBuilder(primaryStage, "Graph");
        windowBuilder.init(studentIdentifier, vertexCount);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
