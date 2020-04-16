package graph.controllers;

import java.util.ArrayList;
import java.util.List;
import graph.models.Vertex;
import java.util.Arrays;
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
                // double value = Math.floor((1.0 - offset2 * 0.02 - offset1 * 0.005 - 0.25) * T);
                // double value = Math.floor((1.0 - offset2 * 0.01 - offset1 * 0.001 - 0.3) * T);
                // double value = Math.floor((1.0 - offset2 * 0.005 - offset1 * 0.005 - 0.27) * T);
                double value = Math.floor((1.0 - offset2 * 0.01 - offset1 * 0.005 - 0.15) * T);
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
                if (item == 1) {
                    result[i][j] = item;
                    result[j][i] = item;
                }
                if (i == j) {
                    result[i][j] = 0;
                }
            }
        }
        return result;
    }
    
    public static void printVertexesDegrees(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            int count = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    count +=1;
                }
            }
            System.out.println("Degree of " + (int)(i + 1) + " is " + count);
        }
    }
    
    public static void printVertexesHalfDegrees(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            int in = 0;
            int out = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    out +=1;
                }
            }
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] == 1) {
                    in +=1;
                }
            }
            System.out.println("Half-Degrees of " + (int)(i + 1) + " is out: " + out + " and in: " + in);
        }
    }
    
    public static void printAllHanging(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            int count = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    count +=1;
                }
            }
            if (count == 1) {
                System.out.println(i + 1 + " is hanging");
            }
        }
    }
    
    public static void printAllIsolated(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            boolean isIsolated = true;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1 || matrix [j][i] == 1) {
                    isIsolated = false;
                    break;
                }
            }
            if (isIsolated) {
                System.out.println(i + 1 + " is isolated" );
            }
        }
    }
    
    public static int[][] multiplyMatrix(int[][] matrix1, int[][] matrix2) {
        int length = matrix1.length;
        int[][] result = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < length; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j]; 
                }
            }
        }
        return result;
    }
    
    public static int[][] powMatrix(int[][] matrix) {
        return multiplyMatrix(matrix, matrix);
    }
    
    public static int[][] findReachabilityMatrix(int[][] matrix, int n) {
        int length = matrix.length;
        int[][] result = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                result[i][j] = 1;
            }
        }
        int[][] temp = matrix;
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    result[i][j] += temp[i][j];
                }
            }
            temp = powMatrix(temp);
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (result[i][j] > 0) {
                    result[i][j] = 1;
                }
            }
        }
        return result;
    }
    
    public static int[][] findConnectednessMatrix(int[][] reachabilityMatrix) {
        int length = reachabilityMatrix.length;
        int[][] transposedMatrix = Arrays.copyOf(reachabilityMatrix, length);
        for (int i = 0; i < length; i++) {
            for (int j = i+1; j < length; j++) {
                int temp = transposedMatrix[i][j];
                transposedMatrix[i][j] = transposedMatrix[j][i];
                transposedMatrix[j][i] = temp;
            }
        }
        return multiplyMatrix(reachabilityMatrix, transposedMatrix);
    }
    
    public static int findConnectedComponets(int[][] connectednessMatrix) {
        int count = 0;
        int length = connectednessMatrix.length;
        int[][] squareConnectednessMatrix = powMatrix(connectednessMatrix);
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                int item = squareConnectednessMatrix[i][j];
                if (!numbers.contains(item)) {
                    count++;
                    numbers.add(item);
                }
            }
        }
        return count;
    }
    
    // public static int findVertex 
    
}
