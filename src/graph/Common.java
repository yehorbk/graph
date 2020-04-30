package graph;

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
        /*if (k > 0) {
            return i > j ? 1 : 0;
        } else if (k < 0) {
            return i < j ? 1 : 0;
        }*/
        return (k > 0) ? (i > j ? 1 : 0) : (i < j ? 1 : 0);
    }
    
    public static int booleanNot(int value) {
        return Common.bool2int(Common.int2bool(value));
    }
    
}
