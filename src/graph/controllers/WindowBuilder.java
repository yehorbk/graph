package graph.controllers;

import graph.controllers.TreeBuilder;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import graph.models.Menu;
import graph.models.Binding;
import graph.models.Vertex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
        
        this.printMatrix(this.adjacencyMatrix);
        // this.printMatrix(simpleMatrix);
        /*GraphBuilder.printVertexesDegrees(simpleMatrix);
        GraphBuilder.printVertexesHalfDegrees(adjacencyMatrix);
        GraphBuilder.printAllHanging(simpleMatrix);
        GraphBuilder.printAllIsolated(simpleMatrix);
        System.out.println("");
        int[][] degree2 = GraphBuilder.powMatrix(simpleMatrix);
        this.printMatrix(degree2);
        int[][] degree3 = GraphBuilder.multiplyMatrix(degree2, simpleMatrix);
        this.printMatrix(degree3);
        int[][] reachabilityMatrix = GraphBuilder.findReachabilityMatrix(simpleMatrix, 10);
        this.printMatrix(reachabilityMatrix);
        int[][] connectednessMatrix = GraphBuilder.findConnectednessMatrix(reachabilityMatrix);
        this.printMatrix(connectednessMatrix);
        int countOfConnectedComponents = GraphBuilder.findConnectedComponets(connectednessMatrix);
        System.out.println(countOfConnectedComponents); // drawCondensationBindings*/
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
        this.menu.bindStartBFSButtonEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                startBFSAlgorithm();
            }
        });
        this.menu.bindShowBFSTreeButtonEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                drawBFSTree();
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
    
    private void startBFSAlgorithm() {
        int[] result = GraphBuilder.breadthFirstSearch(generateAdjacencyList());
        this.drawBFSGraph(Arrays.stream(result).boxed().collect(Collectors.toList()));
        this.printConformityMatrix(result);
        /*for (int item : result) {
            System.out.print(item + " ");
        }*/
        // TreeBuilder treeBuilder = new TreeBuilder(result);
    }
    
    private LinkedList<Integer>[] generateAdjacencyList() {
        int count = vertexList.size();
        LinkedList<Integer> adjacencyList[] = new LinkedList[count];
        for (int i = 0; i < count; i++) {
            adjacencyList[i] = new LinkedList(); 
        }
        for (int i = 0; i < count; i++) {
            for (int j = i; j < count; j++) {
                if (this.adjacencyMatrix[i][j] == 1) {
                    adjacencyList[i].add(j); 
                }
            }
        }
        return adjacencyList;
    }
    
    private void printConformityMatrix(int[] resultRoute) {
        for (int i = 0; i < resultRoute.length; i++) {
            for (int j = 0; j < resultRoute.length; j++) {
                if (resultRoute[i] == j) {
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    private void drawBFSGraph(List<Integer> res) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int iterator = 0;
            @Override
            public void run() {
                if (iterator == res.size() - 1) {
                    drawDirectedBindings();
                    for (Vertex vertex : vertexList) {
                        Color color = Color.AQUAMARINE;
                        vertex.setId(res.get((vertex.getId() - 1)) + 1);
                        graphCanvas.drawColourVertex(vertex, color);
                    }
                    this.cancel();
                    return;
                }
                drawDirectedBindings();
                for (Vertex vertex : vertexList) {
                    Paint color = Color.BLACK;
                    if (res.indexOf(vertex.getId()) < iterator) {
                        color = Color.AQUAMARINE;
                    }
                    if (vertex.getId() == res.get(iterator)) {
                        color = Color.RED;
                    }
                    graphCanvas.drawColourVertex(vertex, color);
                }
                iterator++;
            }
        }, 0, 1000);
    }
    
    private void drawBFSTree() {
        int[] route = GraphBuilder.breadthFirstSearch(generateAdjacencyList());
        int[][] bfsMatrix = GraphBuilder.generateBFSMatrix(route);
        this.graphCanvas.clearGraph();
        for (Vertex vertex : this.vertexList) {
            vertex.setId(route[(vertex.getId() - 1)] + 1);
            this.graphCanvas.drawVertex(vertex);
        }
        for (int i = 0; i < bfsMatrix.length; i++) {
            for (int j = 0; j < bfsMatrix[i].length; j++) {
                if (bfsMatrix[i][j] == 1) {
                    Binding binding = new Binding(this.vertexList);
                    binding.bindSimpleVertex(vertexList.get(i), vertexList.get(j));
                    this.graphCanvas.simpleBindVertex(binding);
                }
            }
        }
    }
    
    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
}
