package cmp;

public class Compare {
    final static double EPISILON = 0.0001;

    public static boolean equal(double a, double b) {
        return Math.abs(a - b) < EPISILON;
    }

    public static boolean equal(float a, float b) {
        return Math.abs(a - b) < EPISILON;
    }
}
