package graph.models;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
    
    private Button showSimpleGraphButton = new Button("Simple Graph");
    private Button showDirectedGraphButton = new Button("Directed Graph");
    private Button showGraphSpecs = new Button("Graph Specs");
    private Button showCondensationGraphButton = new Button("Condensation Graph");
    private Button startBFSButton = new Button("Start BFS");
    private Button showBFSTreeButton = new Button("BFS Tree");
    private Button startPrimeButton = new Button("Start Prime's Algorithm");
    private Button showSpanningTreeButton = new Button("Show Spanning Tree");

    public Menu() {
        this.applyStyles();
        this.getChildren().add(this.showSimpleGraphButton);
        this.getChildren().add(this.showDirectedGraphButton);
        this.getChildren().add(this.showGraphSpecs);
        this.getChildren().add(this.showCondensationGraphButton);
        this.getChildren().add(this.startBFSButton);
        this.getChildren().add(this.showBFSTreeButton);
        this.getChildren().add(this.startPrimeButton);
        this.getChildren().add(this.showSpanningTreeButton);
    }
    
    private void applyStyles() {
        this.setStyle("-fx-background-color: rgba(217, 217, 217, 0.5);");
        this.showSimpleGraphButton.setMaxWidth(Double.MAX_VALUE);
        this.showDirectedGraphButton.setMaxWidth(Double.MAX_VALUE);
        this.showGraphSpecs.setMaxWidth(Double.MAX_VALUE);
        this.showCondensationGraphButton.setMaxWidth(Double.MAX_VALUE);
        this.startBFSButton.setMaxWidth(Double.MAX_VALUE);
        this.showBFSTreeButton.setMaxWidth(Double.MAX_VALUE);
        this.startPrimeButton.setMaxWidth(Double.MAX_VALUE);
        this.showSpanningTreeButton.setMaxWidth(Double.MAX_VALUE);
    }
    
    public void bindShowSimpleGraphButtonEvent(EventHandler eventHandler) {
        this.showSimpleGraphButton.setOnMouseClicked(eventHandler);
    }
    
    public void bindShowDirectedGraphButtonEvent(EventHandler eventHandler) {
        this.showDirectedGraphButton.setOnMouseClicked(eventHandler);
    }
    
    public void bindShowGraphSpecs(EventHandler eventHandler) {
        this.showGraphSpecs.setOnMouseClicked(eventHandler);
    }
    
    public void bindShowCondensationGraphButtonEvent(EventHandler eventHandler) {
        this.showCondensationGraphButton.setOnMouseClicked(eventHandler);
    }
    
    public void bindStartBFSButtonEvent(EventHandler eventHandler) {
        this.startBFSButton.setOnMouseClicked(eventHandler);
    }
    
    public void bindShowBFSTreeButtonEvent(EventHandler eventHandler) {
        this.showBFSTreeButton.setOnMouseClicked(eventHandler);
    }
    
    public void bindStartPrimeButtonEvent(EventHandler eventHandler) {
        this.startPrimeButton.setOnMouseClicked(eventHandler);
    }
    
    public void bindShowSpanningTreeEvent(EventHandler eventHandler) {
        this.showSpanningTreeButton.setOnMouseClicked(eventHandler);
    }
    
}
