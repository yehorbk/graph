package graph.controllers;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TreeBuilder {
    
    private int[] treeArray;
    private GraphicsContext graphicsContext;
    private Scene scene;

    public TreeBuilder(int[] treeArray) {
        this.treeArray = treeArray;
        Canvas canvas = new Canvas(1200, 720);
        this.buildScene(canvas);
        this.drawWindow();
        this.drawTree();
        
    }
    
    private void buildScene(Canvas canvas) {
        this.graphicsContext = canvas.getGraphicsContext2D();
        HBox hbox = new HBox();
        hbox.getChildren().add(canvas);
        this.scene = new Scene(hbox);
    }
    
    public void drawTree() {
        
    }
    
    private void drawWindow() {
        Stage treeStage = new Stage();
        treeStage.setScene(new Scene(new HBox(), 1200, 720));
        treeStage.setTitle("Tree");
        treeStage.show();
    }
    
    
}
