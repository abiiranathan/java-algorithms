package algorithms;

import java.util.List;

/**
 * The Algebra class encapsulates logic for performing common
 * algebra and linear algebra operations.
 */
public class Algebra {
    /**
     * Calculates the Newton forward interpolation given a list of points
     * See https://atozmath.com/example/CONM/NumeInterPola.aspx?q=A&q1=E1
     * 
     * @param points List of (x,y) points.
     * @param value  double
     * @return The approx. value of y at x=value.
     */
    double NewtonForwardInterpolation(List<Point> points, double value) {
        // y(x) = y0 + p∇y0 + (p(p-1)/2!)*∇2y0 + (p(p-1)(p-2)/3!)*∇2y0 + ....

        int n = points.size();
        double x[] = new double[n];
        double y[][] = new double[n][n];

        for (int i = 0; i < n; i++) {
            x[i] = points.get(i).x;
            y[i][0] = points.get(i).y;
        }

        // Calculating the forward difference table
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                y[j][i] = y[j + 1][i - 1] - y[j][i - 1];
            }
        }

        // Initialize p, h and sum
        double sum = y[0][0];
        double h = (x[1] - x[0]); // h= x1-x0
        // p = (x - x0) / h
        double p = (value - x[0]) / h;

        for (int i = 1; i < n; i++) {
            sum += (ForwardPValue(p, i) * y[0][i]) / factorial(i);
        }

        return sum;
    }

    /**
     * Calculates the Newton backward interpolation given a list of points
     * See https://atozmath.com/example/CONM/NumeInterPola.aspx?q=B&q1=E1
     * 
     * @param points List of (x,y) points.
     * @param value  double
     * @return The approx. value of y at x=value.
     */
    double NewtonBackwardInterpolation(List<Point> points, double value) {
        // y(x) = yn + p∇yn + (p(p+1)/2!)*∇2yn + (p(p+1)(p+2)/3!)*∇2yn + ....

        int n = points.size();

        double x[] = new double[n];
        double y[][] = new double[n][n];

        for (int i = 0; i < n; i++) {
            x[i] = points.get(i).x;
            y[i][0] = points.get(i).y;
        }

        // Calculating the backward difference table
        for (int i = 1; i < n; i++) {
            for (int j = n - 1; j >= i; j--) {
                y[j][i] = y[j][i - 1] - y[j - 1][i - 1];
            }
        }

        // Initialize p, h and sum
        double sum = y[n - 1][0];

        double h = (x[1] - x[0]); // h= x1-x0

        // p = (x - xn) / h
        double p = (value - x[n - 1]) / h;

        for (int i = 1; i < n; i++) {
            sum += (backPValue(p, i) * y[n - 1][i]) / factorial(i);
        }

        return sum;
    }

    /**
     * Calculate the p value when doing backward interpolation at index n;
     * See formular.
     * 
     * @param u
     * @param n
     * @return
     */
    private static double backPValue(double u, int n) {
        var temp = u;
        for (int i = 1; i < n; i++) {
            temp *= u + i;
        }
        return temp;
    }

    /**
     * Calculate the p value when doing forward interpolation at index n;
     * See formular.
     * 
     * @param u
     * @param n
     * @return
     */
    private static double ForwardPValue(double u, int n) {
        var temp = u;
        for (int i = 1; i < n; i++) {
            temp *= u - i;
        }
        return temp;
    }

    /**
     * Compute the factorial of n
     * 
     * @param n
     * @return
     */
    private static int factorial(int n) {
        int fact = 1;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    /**
     * Check whether n is a prime number
     * 
     * @param n
     * @return
     */
    boolean IsPrime(int n) {
        // 0 & 1 are not prime numbers
        if (n == 0 || n == 1)
            return false;

        boolean prime = true;
        for (int i = 2; i <= n / 2; i++) {
            // If n is devisible by i, then n is not prime
            if (n % i == 0) {
                prime = false;
                break;
            }
        }
        return prime;
    }
}
