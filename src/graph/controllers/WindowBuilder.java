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
    private int[][] simpleMatrix;
    
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
        this.simpleMatrix = GraphBuilder.generateSimpleMatrix(this.adjacencyMatrix);
        drawScene();
        drawSimpleBindings();
        
        this.printMatrix(this.simpleMatrix);
        // GraphBuilder.printVertexesDegrees(simpleMatrix);
        GraphBuilder.printVertexesHalfDegrees(adjacencyMatrix);
        GraphBuilder.printAllHanging(simpleMatrix);
        GraphBuilder.printAllIsolated(simpleMatrix);
        int[][] degree2 = GraphBuilder.powMatrix(simpleMatrix);
        int[][] degree3 = GraphBuilder.powMatrix(degree2);
        int[][] reachabilityMatrix = GraphBuilder.findReachabilityMatrix(simpleMatrix, 10);
        int[][] connectednessMatrix = GraphBuilder.findConnectednessMatrix(reachabilityMatrix);
        int countOfConnectedComponents = GraphBuilder.findConnectedComponets(connectednessMatrix);
        System.out.println(countOfConnectedComponents); // drawCondensationBindings
    }
    
    public void setMenuEvents() {
        this.menu.bindShowSimpleGraphButtonEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                drawSimpleBindings();
            }
        });
        this.menu.bindShowDirectedGraphButtonEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                drawDirectedBindings();
            }
        });
        this.menu.bindShowCondensationGraphButtonEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                drawCondensationBindings(1);
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
        for (int i = 0; i < this.simpleMatrix.length; i++) {
            for (int j = i; j < this.simpleMatrix[i].length; j++) {
                if (this.simpleMatrix[i][j] == 1) {
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
    
    private void drawCondensationBindings(int n) {
        List<Vertex> condensationVertexList = GraphBuilder.generateVertexList(n);
        this.graphCanvas.clearGraph();
        for (Vertex vertex : condensationVertexList) {
            this.graphCanvas.drawCondensationVertex(vertex);
        }
        for (int i = 0; i < n - 1; i++) {
            Binding binding = new Binding(condensationVertexList);
            binding.bindSimpleVertex(condensationVertexList.get(i), condensationVertexList.get(i + 1));
            this.graphCanvas.simpleBindVertex(binding);
        }
    }
    
    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
}
