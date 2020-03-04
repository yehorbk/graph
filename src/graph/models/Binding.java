package graph.models;

public abstract class Binding {
    
    public double startX;
    public double startY;
    public double endX;
    public double endY;
    
    abstract void bindVertex(Vertex v1, Vertex v2);
    
}
