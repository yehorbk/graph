package graph.models;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
    
    private Button showSimpleGraphButton = new Button("Simple Graph");
    private Button showDirectedGraphButton = new Button("Directed Graph");

    public Menu() {
        this.applyStyles();
        this.getChildren().add(this.showSimpleGraphButton);
        this.getChildren().add(this.showDirectedGraphButton);
    }
    
    private void applyStyles() {
        this.setStyle("-fx-background-color: rgba(217, 217, 217, 0.5);");
        this.showSimpleGraphButton.setMaxWidth(Double.MAX_VALUE);
        this.showDirectedGraphButton.setMaxWidth(Double.MAX_VALUE);
    }
    
    public void bindShowSimpleGraphButtonEvent(EventHandler eventHandler) {
        this.showSimpleGraphButton.setOnMouseClicked(eventHandler);
    }
    
    public void bindShowDirectedGraphButtonEvent(EventHandler eventHandler) {
        this.showDirectedGraphButton.setOnMouseClicked(eventHandler);
    }
    
}
