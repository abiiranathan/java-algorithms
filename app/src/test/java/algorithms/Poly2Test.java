package algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Poly2Test {
    /**
     * Test complex roots of the second degree polynomial.
     */
    @Test
    void testCroots() {
        // x^2 + 3x + 16 = 0
        var p3 = new Poly2(1, 3, 16);
        var roots = p3.croots();
        // -1.5 + 3.7080992435478315i
        assertEquals(2, roots.size());

        assertEquals(-1.5, roots.get(0).real());
        assertEquals(3.7080992435478315, roots.get(0).imag());

        assertEquals(-1.5, roots.get(1).real());
        assertEquals(-3.7080992435478315, roots.get(1).imag());

        // x^2 + 1 = 0 ( +/- i)
        roots = new Poly2(1d, 0d, 1d).croots();
        assertEquals(2.0, roots.size());

        assertEquals(0.0, roots.get(0).real());
        assertEquals(1.0, roots.get(0).imag());

        assertEquals(0.0, roots.get(1).real());
        assertEquals(-1.0, roots.get(1).imag());
    }

    @Test
    void testRoots() {
        // x^2+x-6=0 (-3, 2)
        var roots = new Poly2(1, 1, -6).croots();
        assertEquals(2, roots.size());

        assertEquals(2, roots.get(0).real());
        assertEquals(0, roots.get(0).imag());

        assertEquals(-3.0, roots.get(1).real());
        assertEquals(0.0, roots.get(1).imag());
    }

    @Test
    void testEquals() {
        var p1 = new Poly2(1, 1, -6);
        var p2 = new Poly2(1, 1, -6);

        assertTrue(p1.equals(p2));
    }

    @Test
    void testHasRealRoots() {
        var p1 = new Poly2(1, 1, -6);
        var p2 = new Poly2(1, 0, 1);

        assertTrue(p1.hasRealRoots());
        assertFalse(p2.hasRealRoots());
    }

    @Test
    void testToString() {
        var p1 = new Poly2(1, 1, -6);
        var p2 = new Poly2(1, 0, 1);

        assertEquals("x² + x - 6", p1.toString(0));
        assertEquals("x² + 1", p2.toString(0));
    }
}
