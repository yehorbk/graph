package graph.controllers;

import graph.models.Binding;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import graph.models.Vertex;

public class GraphCanvas extends Canvas {
    
    private GraphicsContext graphicsContext;
    private double width;
    private double height;

    public GraphCanvas(double width, double height) {
        super(width, height);
        this.width = width;
        this.height = height;
        this.graphicsContext = this.getGraphicsContext2D();
    }
    
    public void clearGraph() {
        this.graphicsContext.clearRect(0, 0, this.width, this.height);
    }
    
    public void drawVertex(Vertex vertex) {
        this.graphicsContext.strokeOval(vertex.getPosX(), vertex.getPosY(),
                vertex.getWidth(), vertex.getHeight());
        this.graphicsContext.strokeText("" + vertex.getId(), 
                vertex.getPosX() + vertex.getWidth() / 2.4,
                vertex.getPosY()  + vertex.getHeight() / 1.70);
    }
    
    public void bindVertex(Binding binding) {
        this.graphicsContext.strokeLine(binding.startX, binding.startY, binding.endX, binding.endY);
    }
    
    public void directBindVertex(Binding binding) {
        this.graphicsContext.strokeLine(binding.startX, binding.startY, binding.endX, binding.endY);
        double[] xPoints = { binding.endX, binding.endX - 10, binding.endX - 10 };
        double[] yPoints = { binding.endY, binding.endY - 5, binding.endY + 5 };
        this.graphicsContext.strokePolygon(xPoints, yPoints, 3);
    }
    
}
