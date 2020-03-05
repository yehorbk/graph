package graph.controllers;

import graph.models.Binding;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import graph.models.Vertex;
import javafx.scene.shape.ArcType;

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
    
    public void simpleBindVertex(Binding binding) {
        if (!binding.isSelfConnected) {
            if (!binding.isCrosses) {
                this.graphicsContext.strokeLine(binding.startX, binding.startY, 
                binding.endX, binding.endY);
            } else {
                this.makeBypassBinding(binding);
            }
        } else {
            this.graphicsContext.strokeArc(binding.startX - 10, 
                    binding.startY - 10, 40, 40, 30, 210, ArcType.OPEN);
        }
    }
    
    public void directBindVertex(Binding binding) {
        this.simpleBindVertex(binding);
        this.drawTriangleArrow(binding);
    }
    
    private void makeBypassBinding(Binding binding) {
        double middleX = (binding.startX + binding.endX) / 2;
        double middleY = binding.endY + 40;
        this.graphicsContext.strokeLine(binding.startX, binding.startY,
                middleX, middleY);
        binding.startX = middleX;
        binding.startY = middleY;
        this.graphicsContext.strokeLine(binding.startX, binding.startY,
                binding.endX, binding.endY);
    }
    
    private void drawTriangleArrow(Binding binding) {
        double[] angle = this.getAngle(binding);
        double sin = angle[0];
        double cos = angle[1];
        double pointSize = 5;
        double dx1 = binding.endX - 2 * pointSize * cos - pointSize * sin;
        double dy1 = binding.endY - 2 * pointSize * sin + pointSize * cos;
        double dx2 = binding.endX - 2 * pointSize * cos + pointSize * sin;
        double dy2 = binding.endY - 2 * pointSize * sin - pointSize * cos;
        double[] xPoints = { binding.endX, dx1, dx2 };
        double[] yPoints = { binding.endY, dy1, dy2 };
        this.graphicsContext.fillPolygon(xPoints, yPoints, 3);
    }
    
    private double[] getAngle(Binding binding) {
        double[] result = new double[2];
        double path = Math.sqrt(Math.pow((binding.endX - binding.startX), 2) 
                + Math.pow((binding.endY - binding.startY), 2));
        result[0] = (binding.endY - binding.startY) / path;
        result[1] = (binding.endX - binding.startX) / path;
        return result;
    }
    
}
