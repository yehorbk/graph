package graph.models;

import graph.models.Binding;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import graph.models.Vertex;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    
    public void drawColourVertex(Vertex vertex, Paint color) {
        if (color == Color.BLACK) {
            this.drawVertex(vertex);
        } else {
            this.graphicsContext.setFill(color);
            this.graphicsContext.fillOval(vertex.getPosX(), vertex.getPosY(),
                vertex.getWidth(), vertex.getHeight());
            this.graphicsContext.strokeText("" + vertex.getId(), 
                vertex.getPosX() + vertex.getWidth() / 2.4,
                vertex.getPosY()  + vertex.getHeight() / 1.70);
        }
    }
    
    public void drawCondensationVertex(Vertex vertex) {
        this.graphicsContext.strokeOval(vertex.getPosX(), vertex.getPosY(),
                vertex.getWidth(), vertex.getHeight());
        this.graphicsContext.strokeText("K" + vertex.getId(), 
                vertex.getPosX() + vertex.getWidth() / 2.4,
                vertex.getPosY()  + vertex.getHeight() / 1.70);
    }
    
    public void simpleBindVertex(Binding binding) {
        if (!binding.isSelfConnected) {
            if (!binding.isCrosses) {
                this.graphicsContext.strokeLine(binding.startX, binding.startY, 
                binding.endX, binding.endY);
                if (binding.weight != null) {
                    double middleX = (binding.startX + binding.endX) / 2;
                    double middleY = (binding.startY + binding.endY) / 2;
                    drawWeight(binding, middleX, middleY);
                }
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
        double differenceX = binding.startX - binding.endX;
        double differenceY = binding.startY - binding.endY;
        double coefficient = differenceX > 0 && differenceY > 0 ? 1 : -1;
        coefficient *= binding.startY == binding.endY && differenceX > 0 ? 2 : 1;
        double middleX = (binding.startX + binding.endX) / 2
                + (differenceX - differenceY) * 0.03 * coefficient
                // - 25 * coefficient
                + (differenceY + binding.startY + binding.endY) * 0.02;
        double middleY = (binding.startY + binding.endY) / 2 
                + (differenceX - differenceY) * 0.15 * coefficient
                - 15 * coefficient
                + (differenceX + binding.startX + binding.endX) * 0.0005;
        this.graphicsContext.strokeLine(binding.startX, binding.startY,
                middleX, middleY);
        binding.startX = middleX;
        binding.startY = middleY;
        this.graphicsContext.strokeLine(binding.startX, binding.startY,
                binding.endX, binding.endY);
        if (binding.weight != null) {
            double coefficientX = binding.startX / binding.endX * 10;
            double coefficientY = binding.startY / binding.endY * 5;
            drawWeight(binding, middleX + coefficientX, middleY - coefficientY);
        }
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
    
    private void drawWeight(Binding binding, double middleX, double middleY) {
        this.graphicsContext.strokeText(binding.weight, middleX - 15, middleY - 12);
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
