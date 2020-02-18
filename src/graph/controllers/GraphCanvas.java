package graph.controllers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import models.Vertex;

public class GraphCanvas extends Canvas {
    
    private GraphicsContext graphicsContext;

    public GraphCanvas(double width, double height) {
        super(width, height);
        this.graphicsContext = this.getGraphicsContext2D();
    }
    
    public void drawVertex(Vertex vertex) {
        this.graphicsContext.strokeOval(vertex.getPosX(), vertex.getPosY(), vertex.getWidth(), vertex.getHeight());
    }
    
}
