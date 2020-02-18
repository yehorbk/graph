package graph.controllers;

import java.util.ArrayList;
import java.util.List;
import models.Vertex;

public class CanvasBuilder {
    
    public static List<Vertex> generateVertexList () {
        List<Vertex> vertexList = new ArrayList<>();
        final int beginX = 500;
        final int beginY = -50;
        int x = beginX;
        int y = beginY;
        int kx = -1;
        int ky = 1;
        for (int i = 0; i < 10; i++) {
            x += 150 * kx;
            y += 200 * ky;
            vertexList.add(new Vertex(i + 1, x, y, 50, 50));
            if (x <= beginY || y >= beginX) {
                ky = 0;
                kx = 1;
            }
            if (x >= beginX * 1.5) {
                kx = -1;
                ky = -1;
            }
            if (x >= beginX && y <= beginY) {
                kx = -1;
                ky = 1;
            }
        }
        return vertexList;
    } 
    
}
