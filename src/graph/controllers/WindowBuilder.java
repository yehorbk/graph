package graph.controllers;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import graph.models.Menu;
import graph.models.Binding;
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
    
    private List<Vertex> vertexList;
    private int[][] adjacencyMatrix;
    
    public WindowBuilder(Stage stage, String title) {
        this.stage = stage;
        this.stage.setTitle(title);
    }
    
    public void init(int identifier, int count) {
        this.root = new HBox();
        this.scene = new Scene(this.root, this.width, this.height);
        this.stage.setScene(this.scene);
        this.graphCanvas = new GraphCanvas(this.width * this.canvasArea, this.height);
        this.menu = new Menu();
        setMenuEvents();
        this.vertexList = GraphBuilder.generateVertexList(count);
        this.adjacencyMatrix = GraphBuilder.generateAdjacencyMatrix(identifier, count);
        drawScene();
        drawSimpleBindings();
    }
    
    public void setMenuEvents() {
        this.menu.bindShowSimpleGraphEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                drawSimpleBindings();
            }
        });
        this.menu.bindShowDirectedGraphEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                drawDirectedBindings();
            }
        });
    }
    
    private void drawScene() {
        HBox.setHgrow(this.graphCanvas, Priority.ALWAYS);
        HBox.setHgrow(this.menu, Priority.ALWAYS);
        this.menu.setMaxSize(this.width * this.menuArea, this.height);
        this.root.getChildren().addAll(this.graphCanvas, this.menu);
    }
    
    private void drawGraph() {
        this.graphCanvas.clearGraph();
        for (Vertex vertex : this.vertexList) {
            this.graphCanvas.drawVertex(vertex);
        }
    }
    
    private void drawSimpleBindings() {
        this.drawGraph();
        for (int i = 0; i < this.adjacencyMatrix.length; i++) {
            for (int j = 0; j < this.adjacencyMatrix[i].length; j++) {
                if (this.adjacencyMatrix[i][j] == 1) {
                    Binding binding = new Binding(this.vertexList);
                    binding.bindSimpleVertex(vertexList.get(i), vertexList.get(j));
                    this.graphCanvas.simpleBindVertex(binding);
                }
            }
        }
    }
    
    private void drawDirectedBindings() {
        this.drawGraph();
        for (int i = 0; i < this.adjacencyMatrix.length; i++) {
            for (int j = 0; j < this.adjacencyMatrix[i].length; j++) {
                if (this.adjacencyMatrix[i][j] == 1) {
                    Binding binding = new Binding(this.vertexList);
                    binding.bindSimpleVertex(vertexList.get(i), vertexList.get(j));
                    this.graphCanvas.directBindVertex(binding);
                }
            }
        }
    }
    
    private void printAdjacencyMatrix() {
        for (int i = 0; i < this.adjacencyMatrix.length; i++) {
            for (int j = 0; j < this.adjacencyMatrix[i].length; j++) {
                System.out.print(this.adjacencyMatrix[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
}
