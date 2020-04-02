package graph.controllers;

import java.util.ArrayList;
import java.util.List;
import graph.models.Vertex;
import java.util.Random;

public class GraphBuilder {
    
    public static List<Vertex> generateVertexList(int n) {
        List<Vertex> vertexList = new ArrayList<>();
        final int beginX = 500;
        final int beginY = -50;
        int x = beginX;
        int y = beginY;
        int kx = -1;
        int ky = 1;
        for (int i = 0; i < n; i++) {
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
    
    public static int[][] generateAdjacencyMatrix(int seed, int n) {
        int offset1 = seed % 10;
        int offset2 = (seed / 10) % 10;
        Random rand = new Random(seed);
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double T = rand.nextDouble() + rand.nextDouble();
                double value = Math.floor((1.0 - offset2 * 0.02 - offset1 * 0.005 - 0.25) * T);
                matrix[i][j] = (int)value;
            }
        }
        return matrix;
    }
    
    public static int[][] generateSimpleMatrix(int[][] adjacencyMatrix) {
        int length = adjacencyMatrix.length;
        int[][] result = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                int item = adjacencyMatrix[i][j];
                result[i][j] = item;
                result[j][i] = item;
            }
        }
        return result;
    }
    
}
