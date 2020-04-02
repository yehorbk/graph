package graph.models;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
    
    private Button showSimpleGraphButton = new Button("Simple Graph");
    private Button showDirectedGraphButton = new Button("Directed Graph");
    private Button showCondensationGraphButton = new Button("Condensation Graph");

    public Menu() {
        this.applyStyles();
        this.getChildren().add(this.showSimpleGraphButton);
        this.getChildren().add(this.showDirectedGraphButton);
        this.getChildren().add(this.showCondensationGraphButton);
    }
    
    private void applyStyles() {
        this.setStyle("-fx-background-color: rgba(217, 217, 217, 0.5);");
        this.showSimpleGraphButton.setMaxWidth(Double.MAX_VALUE);
        this.showDirectedGraphButton.setMaxWidth(Double.MAX_VALUE);
        this.showCondensationGraphButton.setMaxWidth(Double.MAX_VALUE);
    }
    
    public void bindShowSimpleGraphButtonEvent(EventHandler eventHandler) {
        this.showSimpleGraphButton.setOnMouseClicked(eventHandler);
    }
    
    public void bindShowDirectedGraphButtonEvent(EventHandler eventHandler) {
        this.showDirectedGraphButton.setOnMouseClicked(eventHandler);
    }
    
    public void bindShowCondensationGraphButtonEvent(EventHandler eventHandler) {
        this.showCondensationGraphButton.setOnMouseClicked(eventHandler);
    }
    
}
