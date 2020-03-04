package graph.models;

import java.util.List;
import graph.models.Vertex;


public class Binding {
    
    public double startX;
    public double startY;
    public double endX;
    public double endY;
    
    public boolean isSelfConnected = false;
    public boolean isCrosses = false;
    
    private List<Vertex> vertexList;

    public Binding(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public void bindSimpleVertex(Vertex v1, Vertex v2) {
        double kStartX = 0;
        double kStartY = 0;
        double kEndX = 0;
        double kEndY = 0;
        
        this.startX = v1.getPosX();
        this.startY = v1.getPosY();
        this.endX = v2.getPosX();
        this.endY = v2.getPosY();
        
        if (v1.getPosX() > v2.getPosX() && v1.getPosY() < v2.getPosY()) {
            kStartY = 50 / 2;
            kEndX = 50 / 2;
        }
        
        if (v1.getPosX() < v2.getPosX() && v1.getPosY() > v2.getPosY()) {
            kStartX = 50;
            kStartY = 50 / 2;
            kEndY = 50 / 2;
        }
        
        if (v1.getPosX() < v2.getPosX() && v1.getPosY() < v2.getPosY()) {
            kStartX = 50;
            kStartY = 50 / 2;
            kEndY = 50 / 2;
        }
        
        if (v1.getPosX() > v2.getPosX() && v1.getPosY() > v2.getPosY()) {
            kStartY = 50 / 2;
            kEndX = 50;
            kEndY = 50 / 2;
        }
        
        if (v1.getPosX() == v2.getPosX() && v1.getPosY() < v2.getPosY()) {
            kStartX = 50 / 2;
            kStartY = 50;
            kEndX = 50 / 2;
        }
        
        if (v1.getPosX() == v2.getPosX() && v1.getPosY() > v2.getPosY()) {
            kStartX = 50 / 2;
            kEndY = 50;
            kEndX = 50 / 2;
        }
        
        if (v1.getPosX() > v2.getPosX() && v1.getPosY() == v2.getPosY()) {
            kStartY = 50 / 2;     
            kEndX = 50;
            kEndY = 50 / 2;
        }
        
        if (v1.getPosX() < v2.getPosX() && v1.getPosY() == v2.getPosY()) {
            kStartX = 50;
            kStartY = 50 / 2;
            kEndY = 50 / 2;
        }
        
        if (v1.getPosX() == v2.getPosX() && v1.getPosY() == v2.getPosY()) {
            kStartX = 0;
            kStartY = 0;
            kEndX = 0;
            kEndY = 0;
            this.isSelfConnected = true;
        }
        
        this.startX = v1.getPosX() + kStartX;
        this.startY = v1.getPosY() + kStartY;
        this.endX = v2.getPosX() + kEndX;
        this.endY = v2.getPosY() + kEndY;
        this.isCrosses = this.checkIsCross(v1, v2);
    }
    
    private boolean checkIsCross(Vertex v1, Vertex v2) {
        boolean result = false;
        // double medianX = (v1.getPosX() + v2.getPosX()) / 2;
        // double medianY = (v1.getPosY() + v2.getPosY()) / 2;
        for (Vertex vertex : this.vertexList) {
            if (vertex.getPosY() == v1.getPosY() && vertex.getPosY() == v2.getPosY()) {
                result = true;
                break;
            }
        }
        return result;
    }
    
}










