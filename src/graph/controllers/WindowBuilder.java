package graph.controllers;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import graph.models.Menu;
import graph.models.SimpleBinding;
import graph.models.Vertex;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;

public class WindowBuilder {
    
    private final int width = 1080;
    private final int height = 720;
    private final double canvasArea = 0.9;
    private final double menuArea = 0.1;

    private Stage stage;
    private Scene scene;
    private HBox root;
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
        setMenuEvents();
        drawScene();
        drawSimpleGraph();
    }
    
    public void setMenuEvents() {
        this.menu.bindShowSimpleGraphEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                drawSimpleGraph();
            }
        });
        this.menu.bindShowDirectedGraphEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                drawDirectedGraph();
            }
        });
    }
    
    private void drawScene() {
        HBox.setHgrow(this.graphCanvas, Priority.ALWAYS);
        HBox.setHgrow(this.menu, Priority.ALWAYS);
        this.menu.setMaxSize(this.width * this.menuArea, this.height);
        this.root.getChildren().addAll(this.graphCanvas, this.menu);
    }
    
    private void drawSimpleGraph() {
        this.graphCanvas.clearGraph();
        List<Vertex> vertexList = CanvasBuilder.generateVertexList();
        for (Vertex vertex : vertexList) {
            this.graphCanvas.drawVertex(vertex);
        }
        int[][] matrix = CanvasBuilder.generateAdjacencyMatrix(9405, 10);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    SimpleBinding simpleBinding = new SimpleBinding();
                    simpleBinding.bindVertex(vertexList.get(i), vertexList.get(j));
                    this.graphCanvas.bindVertex(simpleBinding);
                }
            }
        }
    }
    
    private void drawDirectedGraph() {
        this.graphCanvas.clearGraph();
        List<Vertex> vertexList = CanvasBuilder.generateVertexList();
        for (Vertex vertex : vertexList) {
            this.graphCanvas.drawVertex(vertex);
        }
        int[][] matrix = CanvasBuilder.generateAdjacencyMatrix(9405, 10);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    SimpleBinding simpleBinding = new SimpleBinding();
                    simpleBinding.bindVertex(vertexList.get(i), vertexList.get(j));
                    this.graphCanvas.directBindVertex(simpleBinding);
                }
            }
        }
    }
    
}
