package algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class AlgebraTest {
    class TestCase {
        int value;
        boolean isPrime;

        public TestCase(int val, boolean isPrime) {
            this.value = val;
            this.isPrime = isPrime;
        }
    }

    final double episilon = 0.000001d;
    private static Algebra linalg = new Algebra();

    @Test
    void testIsPrime() {
        TestCase cases[] = {
                new TestCase(1, false),
                new TestCase(2, true),
                new TestCase(3, true),
                new TestCase(5, true),
                new TestCase(6, false),
                new TestCase(7, true),
                new TestCase(8, false),
        };

        for (int i = 0; i < cases.length; i++) {
            boolean isPrime = linalg.IsPrime(cases[i].value);
            assertEquals(cases[i].isPrime, isPrime);
        }
    }

    @Test
    void testNewtonBackwardInterpolation() {
        List<Point> points = new ArrayList<>(5);
        points.add(new Point(1891, 46));
        points.add(new Point(1901, 66));
        points.add(new Point(1911, 81));
        points.add(new Point(1921, 93));
        points.add(new Point(1931, 101));

        double x = 1925;
        var val = linalg.NewtonBackwardInterpolation(points, x);
        assertEquals(96.836800, val, episilon);

        // y=x^2
        points = new ArrayList<>(10);
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 4));
        points.add(new Point(3, 9));
        points.add(new Point(4, 16));
        points.add(new Point(5, 25));
        points.add(new Point(6, 36));
        points.add(new Point(7, 49));
        points.add(new Point(8, 64));
        points.add(new Point(9, 81));
        points.add(new Point(10, 100));

        // predict y at x=20 should be 400
        x = 20;
        assertEquals(400, linalg.NewtonBackwardInterpolation(points, x));
    }

    @Test
    void testNewtonForwardInterpolation() {
        List<Point> points = new ArrayList<>(5);
        points.add(new Point(45, 0.7071));
        points.add(new Point(50, 0.7660));
        points.add(new Point(55, 0.8192));
        points.add(new Point(60, 0.8660));

        // Predict y at x = 12.5
        double x = 52;

        var val = linalg.NewtonForwardInterpolation(points, x);
        assertEquals(0.788003, val, episilon);

        points = new ArrayList<>(4);
        points.add(new Point(45, 0.7071));
        points.add(new Point(50, 0.7660));
        points.add(new Point(55, 0.8192));
        points.add(new Point(60, 0.8660));

        // Predict y at x = 52
        x = 52.0;
        val = linalg.NewtonForwardInterpolation(points, x);
        assertEquals(0.788003, val, episilon);
    }
}
