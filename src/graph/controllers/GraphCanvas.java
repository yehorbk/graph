package graph.controllers;

import graph.models.Binding;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import graph.models.Vertex;
import javafx.scene.transform.Affine;

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
        
        //double angle = Math.atan2(binding.endX - binding.startX, binding.endY - binding.startY);
        /*double angle = (1) / Math.PI;
        double dx1 = ((binding.endX - 10) * Math.cos(angle)) - ((binding.endY - 5) * Math.sin(angle));
        double dx2 = ((binding.endX - 10) * Math.cos(angle)) - ((binding.endY + 5) * Math.sin(angle));
        double dy1 = ((binding.endX - 10) * Math.cos(angle)) + ((binding.endY - 5) * Math.sin(angle));
        double dy2 = ((binding.endX - 10) * Math.cos(angle)) + ((binding.endY + 5) * Math.sin(angle));*/
        
        double dx1 = binding.endX - 10;
        double dx2 = binding.endX - 10;
        double dy1 = binding.endY - 5;
        double dy2 = binding.endY + 5;
        
        //this.graphicsContext.rotate(angle);
        //System.out.println(angle);
        
        double[] xPoints = { binding.endX, dx1, dx2 };
        double[] yPoints = { binding.endY, dy1, dy2 };
        this.graphicsContext.fillPolygon(xPoints, yPoints, 3);
    }
    
}
