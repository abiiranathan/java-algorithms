package algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Poly3Test {
    @Test
    void testRealRoots() {
        // Test distinct real roots
        // x^3-2x-x+2=0
        Poly3 poly3 = new Poly3(1, -2, -1, 2);
        var roots = poly3.RealRoots();

        assertEquals(3, roots.size());

        double r1, r2, r3;
        r1 = roots.get(0);
        r2 = roots.get(1);
        r3 = roots.get(2);

        assertEquals(1.0, r1);
        assertEquals(-1.0, r2);
        assertEquals(2.0, r3);

        // Test repeated roots (x-1)^3=0
        poly3 = new Poly3(1, 0, 0, -1);
        roots = poly3.RealRoots();

        assertEquals(1, roots.size());

        r1 = roots.get(0);
        assertEquals(1, r1);

        // Test a single root x^3-7x^2+41x-87=0
        poly3 = new Poly3(1, -7, 41, -87);
        roots = poly3.RealRoots();

        assertTrue(1 == roots.size());
        r1 = roots.get(0);
        assertEquals(3.0, r1);
    }

    @Test
    void testRoots() {
        // Tests that we get real and complex roots
        Poly3 poly3 = new Poly3(1, -7, 41, -87);
        var roots = poly3.Roots();

        assertTrue(roots.size() == 3);

        Complex r1, r2, r3;
        r1 = roots.get(0);
        r2 = roots.get(1);
        r3 = roots.get(2);

        assertEquals(3.0, r1.real());
        assertEquals(0.0, r1.imag());
        assertEquals(2.0, r2.real());
        assertEquals(5.0, r2.imag());
        assertEquals(2.0, r3.real());
        assertEquals(-5.0, r3.imag());
    }

    @Test
    void testHornerEvaluate() {
        Poly3 poly3 = new Poly3(1, -7, 41, -87);
        double remainder = poly3.hornerEvaluate(3.0);
        assertEquals(0.0, remainder);
    }

    @Test
    void testProbableRoots() {

    }

    @Test
    void testSyntheticDivision() {
        Poly3 poly3 = new Poly3(1, -7, 41, -87);
        Poly2 poly2 = poly3.syntheticDivision(3.0);

        // Expect poly2 not to be null
        // x^2 -4x +29
        // Must be divisible with zero remainder
        assertNotNull(poly2);

        // Test coefficients
        assertEquals(1.0, poly2.getA());
        assertEquals(-4, poly2.getB());
        assertEquals(29.0, poly2.getC());
    }
}
