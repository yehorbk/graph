package graph.controllers;

import graph.Common;
import graph.models.Binding;
import graph.models.GraphCanvas;
import graph.models.Vertex;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GraphController {
    
    private List<Vertex> vertexList;
    private int[][] adjacencyMatrix;
    private int[][] simpleMatrix;
    private int[][] weightsMatrix;
    
    GraphCanvas graphCanvas;

    public GraphController(int identifier, int count, GraphCanvas graphCanvas) {
        this.graphCanvas = graphCanvas;
        this.vertexList = GraphBuilder.generateVertexList(count);
        this.adjacencyMatrix = GraphBuilder.generateAdjacencyMatrix(identifier, count);
        this.simpleMatrix = GraphBuilder.generateSimpleMatrix(this.adjacencyMatrix);
        this.weightsMatrix = GraphBuilder.generateWeightsMatrix(identifier, count, simpleMatrix);
        drawSimpleBindings();
    }
    
    public String generateGraphSpecs() {
        StringBuilder result = new StringBuilder();
        int[][] degree2 = GraphBuilder.powMatrix(simpleMatrix);
        int[][] degree3 = GraphBuilder.multiplyMatrix(degree2, simpleMatrix);
        int[][] reachabilityMatrix = GraphBuilder.findReachabilityMatrix(simpleMatrix, 10);
        int[][] connectednessMatrix = GraphBuilder.findConnectednessMatrix(reachabilityMatrix);
        int countOfConnectedComponents = GraphBuilder.findConnectedComponets(connectednessMatrix);
        result.append("Матриця сумiжностi не напрямленого графа: \n");
        result.append(Common.matrix2string(simpleMatrix) + "\n");
        result.append("Матриця сумiжностi напрямленого графа: \n");
        result.append(Common.matrix2string(adjacencyMatrix) + "\n");
        result.append("Матриця ваг: \n");
        result.append(Common.matrix2string(weightsMatrix) + "\n");
        result.append("Степенi вершин: \n");
        result.append(GraphBuilder.getVertexesDegrees(simpleMatrix) + "\n");
        result.append("Напiв-степенi вершин: \n");
        result.append(GraphBuilder.getVertexesHalfDegrees(adjacencyMatrix) + "\n");
        result.append("Граф однороднiй: \n");
        result.append(GraphBuilder.getRegularGraphSpec(simpleMatrix) + "\n");
        result.append("Усі висячі вершини: \n");
        result.append(GraphBuilder.getAllHanging(simpleMatrix) + "\n");
        result.append("Усі ізольовані вершини: \n");
        result.append(GraphBuilder.getAllIsolated(simpleMatrix) + "\n");
        result.append("Матриця A2: \n");
        result.append(Common.matrix2string(degree2) + "\n");
        result.append("Матриця A3: \n");
        result.append(Common.matrix2string(degree3) + "\n");
        result.append("Шляхи довжини 2: \n");
        result.append(Common.matrix2string(GraphBuilder.getRoutes(degree2, 2)) + "\n");
        result.append("Шляхи довжини 3: \n");
        result.append(Common.matrix2string(GraphBuilder.getRoutes(degree3, 3)) + "\n");
        result.append("Матриця досяжності: \n");
        result.append(Common.matrix2string(reachabilityMatrix) + "\n");
        result.append("Матриця зв'язності: \n");
        result.append(Common.matrix2string(connectednessMatrix) + "\n");
        result.append("Кількість компонент: \n");
        result.append(countOfConnectedComponents + "\n"); // drawCondensationBindings*/
        return result.toString();
    }
    
    public void drawGraph() {
        this.graphCanvas.clearGraph();
        for (Vertex vertex : this.vertexList) {
            this.graphCanvas.drawVertex(vertex);
        }
    }

    public void drawSimpleBindings() {
        this.drawGraph();
        for (int i = 0; i < this.simpleMatrix.length; i++) {
            for (int j = i; j < this.simpleMatrix[i].length; j++) {
                if (this.simpleMatrix[i][j] == 1) {
                    Binding binding = new Binding(this.vertexList);
                    binding.bindSimpleVertex(vertexList.get(i), vertexList.get(j), null);
                    this.graphCanvas.simpleBindVertex(binding);
                }
            }
        }
    }

    public void drawDirectedBindings() {
        this.drawGraph();
        for (int i = 0; i < this.adjacencyMatrix.length; i++) {
            for (int j = 0; j < this.adjacencyMatrix[i].length; j++) {
                if (this.adjacencyMatrix[i][j] == 1) {
                    Binding binding = new Binding(this.vertexList);
                    binding.bindSimpleVertex(vertexList.get(i), vertexList.get(j), null);
                    this.graphCanvas.directBindVertex(binding);
                }
            }
        }
    }
 
    public void drawDirectedWeightBindings(int[][] weights) {
        this.drawGraph();
        for (int i = 0; i < this.adjacencyMatrix.length; i++) {
            for (int j = 0; j < this.adjacencyMatrix[i].length; j++) {
                if (this.adjacencyMatrix[i][j] == 1) {
                    Binding binding = new Binding(this.vertexList);
                    binding.bindSimpleVertex(vertexList.get(i), vertexList.get(j), weights[i][j] + "");
                    this.graphCanvas.directBindVertex(binding);
                }
            }
        }
    }

    public void drawCondensationBindings() {
        int[][] reachabilityMatrix = GraphBuilder.findReachabilityMatrix(simpleMatrix, 10);
        int[][] connectednessMatrix = GraphBuilder.findConnectednessMatrix(reachabilityMatrix);
        int n = GraphBuilder.findConnectedComponets(connectednessMatrix);
        List<Vertex> condensationVertexList = GraphBuilder.generateVertexList(n);
        this.graphCanvas.clearGraph();
        for (Vertex vertex : condensationVertexList) {
            this.graphCanvas.drawCondensationVertex(vertex);
        }
        for (int i = 0; i < n - 1; i++) {
            Binding binding = new Binding(condensationVertexList);
            binding.bindSimpleVertex(condensationVertexList.get(i), condensationVertexList.get(i + 1), null);
            this.graphCanvas.simpleBindVertex(binding);
        }
    }

    public void startBFSAlgorithm() {
        int[] result = GraphBuilder.breadthFirstSearch(generateAdjacencyList());
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i] + " ");
        }
        this.drawBFSGraph(Arrays.stream(result).boxed().collect(Collectors.toList()));
        this.printConformityMatrix(result);
    }

    public LinkedList<Integer>[] generateAdjacencyList() {
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

    public void printConformityMatrix(int[] resultRoute) {
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

    public void drawBFSGraph(List<Integer> res) {
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

    public void drawBFSTree() {
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
                    binding.bindSimpleVertex(vertexList.get(i), vertexList.get(j), null);
                    this.graphCanvas.simpleBindVertex(binding);
                }
            }
        }
    }

    public void startPrimeAlgorithm() {
        int count = this.weightsMatrix.length;
        int[][] primeMatrix = GraphBuilder.spanningSearch(count, this.weightsMatrix);
        int[] primeRoute = GraphBuilder.generateSpanningRoute(primeMatrix);
        drawPrimeGraph(Arrays.stream(primeRoute).boxed().collect(Collectors.toList()), this.weightsMatrix);
    }

    public void drawPrimeGraph(List<Integer> res, int[][] weights) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int iterator = 0;
            @Override
            public void run() {
                if (iterator == res.size() - 1) {
                    graphCanvas.clearGraph();
                    drawDirectedWeightBindings(weights);
                    for (Vertex vertex : vertexList) {
                        Color color = Color.AQUAMARINE;
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

    public void drawSpanningTree() {
        int count = this.weightsMatrix.length;
        int[][] spanningMatrix = GraphBuilder.spanningSearch(count, this.weightsMatrix);
        Common.printMatrix(spanningMatrix);
        this.graphCanvas.clearGraph();
        for (Vertex vertex : this.vertexList) {
            this.graphCanvas.drawVertex(vertex);
        }
        for (int i = 0; i < spanningMatrix.length; i++) {
            for (int j = 0; j < spanningMatrix[i].length; j++) {
                if (spanningMatrix[i][j] > 0) {
                    Binding binding = new Binding(this.vertexList);
                    binding.bindSimpleVertex(vertexList.get(i), vertexList.get(j), spanningMatrix[i][j] + "");
                    this.graphCanvas.simpleBindVertex(binding);
                }
            }
        }
    }

}
