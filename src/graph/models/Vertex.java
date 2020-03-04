package graph.models;

public class Vertex {
    
    private int id;
    private double posX;
    private double posY;
    private double width;
    private double height;

    public Vertex(int id, double posX, double posY, double width, double height) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Vertex{" + "id=" + id + ", posX=" + posX + ", posY=" + posY + ", width=" + width + ", height=" + height + '}';
    }    
    
}
