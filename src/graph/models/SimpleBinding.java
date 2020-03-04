package graph.models;

public class SimpleBinding extends Binding {

    @Override
    public void bindVertex(Vertex v1, Vertex v2) {
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
            kStartX = v1.getWidth();
            kStartY = 50 / 2;
            kEndY = 50 / 2;
        }
        
        if (v1.getPosX() < v2.getPosX() && v1.getPosY() < v2.getPosY()) {
            kStartX = v1.getWidth();
            kStartY = 50 / 2;
            kEndY = 50 / 2;
        }
        
        if (v1.getPosX() > v2.getPosX() && v1.getPosY() > v2.getPosY()) {
            kStartY = 50 / 2;
            kEndX = v1.getWidth();
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
        
        if (v1.getPosX() > v2.getPosY() && v1.getPosY() == v2.getPosY()) {
            kStartY = 50 / 2;            
            kEndY = 50 / 2;
        }
        
        if (v1.getPosX() < v2.getPosY() && v1.getPosY() == v2.getPosY()) {
            kStartY = 50 / 2;
            kEndY = 50 / 2;
        }
        
        if (v1.getPosX() == v2.getPosX() && v1.getPosY() == v2.getPosY()) {
            kStartX = 0;
            kStartY = 0;
            kEndX = 0;
            kEndY = 0;
        }
        
        this.startX = v1.getPosX() + kStartX;
        this.startY = v1.getPosY() + kStartY;
        this.endX = v2.getPosX() + kEndX;
        this.endY = v2.getPosY() + kEndY;
        
    }
    
}
