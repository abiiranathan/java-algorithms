package algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComplexTest {
    private static class Cplx {
        public static final Complex a = new Complex(5.0, 6.0);
        public static final Complex b = new Complex(-3.0, 4.0);
        public static final Complex c = new Complex(-1, 0);
        public static final Complex f = new Complex(5, 2);
    }

    @Test
    void testAbs() {
        assertEquals(5.385164807134504, Cplx.f.abs());
    }

    @Test
    void testArg() {
        assertEquals(0.3805063771123649, Cplx.f.arg());
        assertEquals(0.3805063771123649, Cplx.f.phase());
    }

    @Test
    void testCbrt() {

    }

    @Test
    void testConjugate() {

    }

    @Test
    void testCos() {

    }

    @Test
    void testCosh() {

    }

    @Test
    void testDiv() {

    }

    @Test
    void testEquals() {

    }

    @Test
    void testExp() {
        assertEquals(-61.761666662504986, Cplx.f.exp().real());
        assertEquals(134.9517036790434, Cplx.f.exp().imag());
    }

    @Test
    void testHashCode() {
        assertEquals(1041236929, Cplx.c.hashCode());
    }

    @Test
    void testImag() {
        assertEquals(5.0, Cplx.a.real());
        assertEquals(4.0, Cplx.b.imag());
    }

    @Test
    void testLog() {
        assertEquals(1.6836479149932368, Cplx.f.log().real());
        assertEquals(0.3805063771123649, Cplx.f.log().imag());
    }

    @Test
    void testMinus() {

    }

    @Test
    void testMod() {

    }

    @Test
    void testNegate() {
        assertEquals(-5.0, Cplx.a.negate().real());
        assertEquals(-6.0, Cplx.a.negate().imag());
    }

    @Test
    void testPhase() {

    }

    @Test
    void testPlus() {

    }

    @Test
    void testPolar() {

    }

    @Test
    void testPower() {

    }

    @Test
    void testPower2() {

    }

    @Test
    void testReal() {
        assertEquals(5.0, Cplx.a.real());
        assertEquals(-3.0, Cplx.b.real());
    }

    @Test
    void testReciprocal() {

    }

    @Test
    void testScale() {

    }

    @Test
    void testSin() {

    }

    @Test
    void testSinh() {

    }

    @Test
    void testSqrt() {

    }

    @Test
    void testTan() {

    }

    @Test
    void testTanh() {

    }

    @Test
    void testTimes() {

    }
}
