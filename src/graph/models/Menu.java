package graph.models;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
    
    private Button showSimpleGraph = new Button("Simple Graph");
    private Button showDirectedGraph = new Button("Directed Graph");

    public Menu() {
        this.applyStyles();
        this.getChildren().add(this.showSimpleGraph);
        this.getChildren().add(this.showDirectedGraph);
    }
    
    private void applyStyles() {
        this.setStyle("-fx-background-color: rgba(217, 217, 217, 0.5);");
    }
    
    public void bindShowSimpleGraphEvent(EventHandler eventHandler) {
        this.showSimpleGraph.setOnMouseClicked(eventHandler);
    }
    
    public void bindShowDirectedGraphEvent(EventHandler eventHandler) {
        this.showDirectedGraph.setOnMouseClicked(eventHandler);
    }
    
}
