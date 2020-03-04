package graph.models;

public class SimpleBinding extends Binding {

    @Override
    public void bindVertex(Vertex v1, Vertex v2) {
        this.startX = v1.getPosX();
        this.startY = v1.getPosY();
        this.endX = v2.getPosX();
        this.endY = v2.getPosY();
    }
    
}
