package graph;

import graph.controllers.WindowBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

public class Graph extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        WindowBuilder windowBuilder = new WindowBuilder(primaryStage, "Graph");
        windowBuilder.init();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
