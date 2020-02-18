package graph.controllers;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import models.Menu;
import models.Vertex;

public class WindowBuilder {

    private Stage stage;
    private Scene scene;
    private HBox root;
    private final int width = 1080;
    private final int height = 720;
    private final double canvasArea = 0.9;
    private final double menuArea = 0.1;
    private GraphCanvas graphCanvas;
    private Menu menu;
    
    public WindowBuilder(Stage stage, String title) {
        this.stage = stage;
        this.stage.setTitle(title);
    }
    
    public void init() {
        this.root = new HBox();
        this.scene = new Scene(this.root, this.width, this.height);
        this.stage.setScene(this.scene);
        this.graphCanvas = new GraphCanvas(this.width * this.canvasArea, this.height);
        this.menu = new Menu();
        drawScene();
        drawGraph();
    }
    
    private void drawScene() {
        HBox.setHgrow(this.graphCanvas, Priority.ALWAYS);
        HBox.setHgrow(this.menu, Priority.ALWAYS);
        this.menu.setMaxSize(this.width * this.menuArea, this.height);
        this.root.getChildren().addAll(this.graphCanvas, this.menu);
    }
    
    private void drawGraph() {
        for (Vertex vertex : CanvasBuilder.generateVertexList()) {
            this.graphCanvas.drawVertex(vertex);
        }
    }
    
}
