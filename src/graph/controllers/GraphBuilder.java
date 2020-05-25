package graph.controllers;

import graph.Common;
import java.util.ArrayList;
import java.util.List;
import graph.models.Vertex;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
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

    public static int[][] generateAdjacencyMatrix(int seed, int n, int lab) {
        int offset1 = seed % 10;
        int offset2 = (seed / 10) % 10;
        Random rand = new Random(seed);
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double T = rand.nextDouble() + rand.nextDouble();
                double value;
                switch(lab) {
                    case 1:
                    case 6:
                    default:
                        value = Math.floor((1.0 - offset2 * 0.02 - offset1 * 0.005 - 0.25) * T);
                        break;
                    case 2:
                        value = Math.floor((1.0 - offset2 * 0.01 - offset1 * 0.001 - 0.3) * T);
                        break;
                    case 3:
                        value = Math.floor((1.0 - offset2 * 0.005 - offset1 * 0.005 - 0.27) * T);
                        break;
                    case 4:
                        value = Math.floor((1.0 - offset2 * 0.01 - offset1 * 0.005 - 0.15) * T);
                        break;
                    case 5:
                        value = Math.floor((1.0 - offset2 * 0.01 - offset1 * 0.005 - 0.05) * T);
                        break;       
                }
                matrix[i][j] = (int) value;
            }
        }
        return matrix;
    }

    public static int[][] generateWeightsMatrix(int seed, int n, int[][] adjacencyMatrix) {
        Random rand = new Random(seed);
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int Wt = (int) (rand.nextDouble() * 100 * adjacencyMatrix[i][j]); // Wt = round(rand(n,n)*100 .* A)
                int B = Wt & 1; // B = Wt & ones(n,n)
                Wt = Common.bool2s(B & ~(Common.booleanNot(B))) + Common.bool2s(B & (Common.booleanNot(B))) * Common.tril(i, j, -1) * Wt; // Wt = (bool2s(B & ~B') + bool2s(B & B') .* tril(ones(n,n),-1)) .* Wt;
                matrix[i][j] = Wt + Common.booleanNot(Wt); // W = Wt + Wt'
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int item = matrix[i][j];
                if (item > 0) {
                    matrix[i][j] = item;
                    matrix[j][i] = item;
                }
                if (i == j || item == 0) {
                    matrix[i][j] = Integer.MAX_VALUE;
                }
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

    public static String getVertexesDegrees(int[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            int count = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    count += 1;
                }
            }
            result.append("Degree of " + (int) (i + 1) + " is " + count + "\n");
        }
        return result.toString();
    }

    public static String getVertexesHalfDegrees(int[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            int in = 0;
            int out = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    out += 1;
                }
            }
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] == 1) {
                    in += 1;
                }
            }
            result.append("Half-Degrees of " + (int) (i + 1) + " is out: " + out + " and in: " + in + "\n");
        }
        return result.toString();
    }

    public static String getRegularGraphSpec(int[][] matrix) {
        int graphDegree = 0;
        for (int i = 0; i < matrix.length; i++) {
            int count = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    count += 1;
                }
            }
            if (graphDegree == 0) {
                graphDegree = count;
            }
            if (count != graphDegree) {
                return "No\n";
            }
        }
        return "Yes: " + graphDegree + "\n";
    }

    public static String getAllHanging(int[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            int count = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    count += 1;
                }
            }
            if (count == 1) {
                result.append(i + 1 + " is hanging" + "\n");
            }
        }
        return result.toString();
    }

    public static String getAllIsolated(int[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            boolean isIsolated = true;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1 || matrix[j][i] == 1) {
                    isIsolated = false;
                    break;
                }
            }
            if (isIsolated) {
                result.append(i + 1 + " is isolated" + "\n");
            }
        }
        return result.toString();
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

    public static int[][] getRoutes(int[][] matrix, int length) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][i] = 0;
        }
        List<Integer[]> bufferList = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                List<Integer> currentRoute = new ArrayList<>();
                Map<Integer, Boolean> visited = new HashMap<>();
                currentRoute.add(i);
                currentRoute.add(j);
                visited.put(i, true);
                visited.put(j, true);
                int currentVertex = j;
                do {
                    for (int k = 0; k < matrix[currentVertex].length; k++) {
                        if (matrix[currentVertex][k] == matrix[i][j] && currentVertex != i && visited.get(k) == null) {
                            currentVertex = k;
                            visited.put(currentVertex, true);
                            currentRoute.add(currentVertex);
                            // break;
                        }
                    }
                } while (currentVertex != i && visited.get(currentVertex) == null);
                bufferList.add(currentRoute.toArray(new Integer[currentRoute.size()]));
            }
        }
        bufferList.removeIf((t) -> {
            return t.length != length + 1;
        });
        int[][] result = new int[bufferList.size()][];
        for (int i = 0; i < bufferList.size(); i++) {
            int[] array = new int[bufferList.get(i).length];
            for (int j = 0; j < bufferList.get(i).length; j++) {
                array[j] = bufferList.get(i)[j];
            }
            result[i] = array;
        }
        return result;
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
            for (int j = i + 1; j < length; j++) {
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

    public static int[] breadthFirstSearch(LinkedList<Integer> adjacencyList[]) {
        int[] result = new int[adjacencyList.length];
        boolean visited[] = new boolean[adjacencyList.length];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int activeVertex, iterator = 0;
        while ((activeVertex = selectVertex(visited)) != -1) {
            visited[activeVertex] = true;
            queue.add(activeVertex);
            while (!queue.isEmpty()) {
                activeVertex = queue.poll();
                result[iterator++] = activeVertex;
                Iterator<Integer> i = adjacencyList[activeVertex].listIterator();
                while (i.hasNext()) {
                    int n = i.next();
                    if (!visited[n]) {
                        visited[n] = true;
                        queue.add(n);
                    }
                }
            }
        }
        return result;
    }

    private static int selectVertex(boolean[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                return i;
            }
        }
        return -1;
    }

    public static int[][] generateBFSMatrix(int[] route) {
        int count = route.length;
        int[][] result = new int[count][count];
        for (int i = 0; i < count / 2; i++) {
            int index = route[i];
            int left = route[i * 2 + 1];
            result[index][left] = 1;
            if ((i * 2 + 2) == count) {
                break;
            }
            int right = route[i * 2 + 2];
            result[index][right] = 1;
        }
        return result;
    }

    public static int[][] spanningSearch(int n, int[][] weightsMatrix) {
        final int INFINITY = Integer.MAX_VALUE;
        int amount = 0;
        int[] route = new int[n];
        int[] indexes = new int[n];
        boolean[] visited = new boolean[n];
        int[][] matrix = new int[n][n];
        Arrays.fill(route, INFINITY);
        Arrays.fill(visited, false);
        for (int i = 0; i < n; i++) {
            Arrays.fill(matrix[i], 0);
        }
        route[0] = 0;
        for (int i = 0; i < n; i++) {
            int minRoute = INFINITY;
            int currentIndex = 0;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && route[j] < minRoute) {
                    minRoute = route[j];
                    currentIndex = j;
                }
            }
            amount += minRoute;
            visited[currentIndex] = true;
            for (int j = 0; j < n; j++) {
                route[j] = Math.min(route[j], weightsMatrix[currentIndex][j]);
            }
            indexes[i] = currentIndex;
        }
        for (int i = 0; i < n - 1; i++) {
            matrix[indexes[i]][indexes[i + 1]] = route[i + 1];
        }
        /*for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int item = matrix[i][j]; 
                if (item > 0) {
                    matrix[i][j] = item;
                    matrix[j][i] = item;
                }
                if (i == j) {
                    matrix[i][j] = 0;
                }
            }
        }*/
        return matrix;
    }

    public static int[] generateSpanningRoute(int[][] spanningWeightsMatrix) {
        int n = spanningWeightsMatrix.length;
        int[] route = new int[n];
        int currentLine = 0, iterator = 0;
        route[iterator] = 0;
        for (int i = 0; i < n; i++) {
            if (spanningWeightsMatrix[currentLine][i] > 0) {
                currentLine = i;
                i = 0;
                route[++iterator] = currentLine;
            }
        }
        /*for (int i = 0; i < 10; i++) {
            System.out.print(route[i] + " ");
        }*/
        return route;
    }

}
