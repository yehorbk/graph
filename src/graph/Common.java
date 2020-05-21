package graph;

import java.lang.StringBuilder;

public class Common {
    
    public static boolean int2bool(int value) {
        return value == 1 ? true : false;
    }
    
    public static int bool2int(boolean value) {
        return value ? 1 : 0;
    }
    
    public static int bool2s(boolean value) {
        return value ? 1 : 0;
    }
    
    public static int bool2s(int value) {
        return value > 0 ? 1 : 0;
    }
    
    public static int tril(int i, int j, int k) {
        return (k > 0) ? (i > j ? 1 : 0) : (i < j ? 1 : 0);
    }
    
    public static int booleanNot(int value) {
        return Common.bool2int(Common.int2bool(value));
    }
    
    public static String matrix2string(int[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result.append(matrix[i][j] + " ");
            }
            result.append("\n");
        }
        return result.toString();
    }
    
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
}
