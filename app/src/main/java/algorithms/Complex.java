/******************************************************************************
 *
 * Data type for complex numbers.
 *
 * The data type is "immutable" so once you create and initialize
 * a Complex object, you cannot change it. The "final" keyword
 * when declaring re and im enforces this rule, making it a
 * compile-time error to change the .re or .im instance variables after
 * they've been initialized.
 * 
 * 
 * Last Updated November 24, 2022
 * Copyright 2022-2003
 * 
 * @version 1.0
 * @author Dr. A. Nathan Kyarugahi
 *
 ******************************************************************************/

package algorithms;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Vector;

/**
 * Data type for complex numbers.
 * The data type is "immutable" so once you create and initialize
 * a Complex object, you cannot change it.
 */
public class Complex {
    private final double re; // the real part
    private final double im; // the imaginary part

    public Complex(double real, double imag) {
        this.re = real;
        this.im = imag;
    }

    /**
     * Polar form of a complex number.
     */
    public class Polar {
        double R; // R is the modulus of the complex number
        double theta; // theta is the phase angle in radians

        public Polar(double r, double theta) {
            this.R = r;
            this.theta = theta;
        }

        @Override
        public String toString() {
            return String.format("%.4f cos(%.4f) + isin(%.4f)", R, theta, theta);
        }

    }

    /**
     * String representation of this Complex number.
     * 
     * @return x+i*y, x-i*y, x, or i*y as appropriate.
     */
    public String toString() {
        // No imaginary part, return real part only
        if (im == 0)
            return re + "";

        // No real part
        if (re == 0)
            // Ignore prefix 1i and return only i
            return im == 1.0 ? "i" : (im == -1.0 ? "-" : im) + "i";

        if (im < 0)
            // Ignore prefix -1i and return only -i
            return re + " - " + (im == -1.0000 ? "" : (-im)) + "i";

        // Both real and imag parts should be represented
        return re + " + " + (im == 1.0 ? "" : (im)) + "i";
    }

    /**
     * Abs/Modulus/Magnitude of this Complex number
     * (the distance from the origin in polar coordinates).
     * r = Math.sqrt(a^2 + b^2)
     * 
     * @return |z| where z is this Complex number.
     */
    public double abs() {
        return Math.hypot(re, im);
    }

    /**
     * Modulus of a complex number. Similar to {@link #abs()}
     * 
     * @return |z|
     */
    public double mod() {
        return abs();
    }

    /**
     * return angle (in radians)/phase/argument, normalized to be between -pi and
     * pi. (The angle in radians with the x-axis in polar coordinates).
     * 
     * @return arg(z) where z is this Complex number.
     */
    public double arg() {
        return Math.atan2(im, re);
    }

    /**
     * Similar to {@link #arg()}
     * 
     * @return arg(z)
     */
    public double phase() {
        return arg();
    }

    /**
     * return a new Complex object whose value is (this + b)
     * 
     * @param b
     * @return A new complex number
     */
    public Complex plus(Complex b) {
        Complex a = this;
        var real = BigDecimal.valueOf(a.re).add(BigDecimal.valueOf(b.re));
        var imag = BigDecimal.valueOf(a.im).add(BigDecimal.valueOf(b.im));
        return new Complex(real.doubleValue(), imag.doubleValue());
    }

    /**
     * return a new Complex object whose value is (this - b)
     * 
     * @param b
     * @return A new complex number
     * 
     */
    public Complex minus(Complex b) {
        Complex a = this;
        var real = BigDecimal.valueOf(a.re).subtract(BigDecimal.valueOf(b.re));
        var imag = BigDecimal.valueOf(a.im).subtract(BigDecimal.valueOf(b.im));
        return new Complex(real.doubleValue(), imag.doubleValue());
    }

    /**
     * return a new Complex object whose value is (this * b)
     * 
     * @param b
     * @return A new complex number
     * 
     */
    public Complex times(Complex b) {
        Complex a = this;
        var real = BigDecimal.valueOf(a.re * b.re).subtract(BigDecimal.valueOf(a.im * b.im));
        var imag = BigDecimal.valueOf(a.re * b.im).add(BigDecimal.valueOf(a.im * b.re));
        return new Complex(roundDouble(real.doubleValue()), roundDouble(imag.doubleValue()));
    }

    /**
     * A static version of plus
     * 
     * @param a
     * @param b
     * @return A new complex number
     * 
     */
    public static Complex plus(Complex a, Complex b) {
        var real = BigDecimal.valueOf(a.re).add(BigDecimal.valueOf(b.re));
        var imag = BigDecimal.valueOf(a.im).add(BigDecimal.valueOf(b.im));
        return new Complex(real.doubleValue(), imag.doubleValue());
    }

    /**
     * return a / b
     * 
     * @param b
     * @return
     */
    public Complex div(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }

    /**
     * return a new object whose value is (this * alpha)
     * 
     * @param alpha
     * @return
     */
    public Complex scale(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    /**
     * return a new Complex object whose value is the conjugate of this
     * 
     * @return
     */
    public Complex conjugate() {
        return new Complex(re, -im);
    }

    /**
     * return a new Complex object whose value is the reciprocal of this
     * 
     * @return
     */
    public Complex reciprocal() {
        BigDecimal re = asDecimal(this.re);
        BigDecimal im = asDecimal(this.im);

        BigDecimal scale = re.multiply(re).add(im.multiply(im));
        return new Complex(
                re.divide(scale, 5, RoundingMode.HALF_UP).doubleValue(),
                im.negate().divide(scale, 5, RoundingMode.HALF_UP).doubleValue());
    }

    /**
     * Real part of this Complex number
     * (the x-coordinate in rectangular coordinates).
     * 
     * @return Re[z] where z is this Complex number.
     */
    public double real() {
        // fixes wierd comparison errors (0 != -0.0 ???, sorry doubles and floats)
        if (re == -0.0d) {
            return 0;
        }
        return re;
    }

    /**
     * Imaginary part of this Complex number
     * (the y-coordinate in rectangular coordinates).
     * 
     * @return Im[z] where z is this Complex number.
     */
    public double imag() {
        // fixes wierd comparison errors
        if (im == -0.0d) {
            return 0;
        }
        return im;
    }

    /**
     * return a new Complex object whose value is the complex exponential of this
     * 
     * @return
     */
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    /**
     * Computes power of a complex number for non-integer n
     * Using De Moivre's theorem.
     * 
     * @param n Any real non-integer number.
     * @return
     */
    public Complex power(double n) {
        // Express the complex in Polar form.
        Polar p = polar();

        // De Moivre's theorem
        // Z^n = r^n (cos(nθ) + i sin(nθ)).
        return new Complex(
                roundDouble(Math.pow(p.R, n) * Math.cos(n * p.theta)),
                roundDouble(Math.pow(p.R, n) * Math.sin(n * p.theta)));
    }

    /**
     * Returns a complex raised to power of integer n
     * 
     * @param n
     * @return
     */
    public Complex power(long n) {
        Complex ans = new Complex(0, 0);

        // Every thing should be zero
        if (n == 0) {
            return ans;
        }

        // return this complex unchanged
        if (n == 1)
            return this;

        // Recursive call for n/2
        Complex sq = this.power(n / 2);
        if (n % 2 == 0)
            return sq.times(sq);

        return this.times(sq.times(sq));
    }

    /**
     * 
     * @return A new Complex object whose value is the complex sine of this
     */
    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    /**
     * 
     * @return A new Complex object whose value is the complex cosine of this
     */
    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    /**
     * 
     * @return A new Complex object whose value is the complex tangent of this
     */
    public Complex tan() {
        return sin().div(cos());
    }

    /**
     * Real cosh function (used to compute complex trig functions)
     * 
     * @return
     */
    private double cosh(double theta) {
        return (Math.exp(theta) + Math.exp(-theta)) / 2.0;
    }

    /**
     * 
     * @return Real sinh function (used to compute complex trig functions)
     */
    private double sinh(double theta) {
        return (Math.exp(theta) - Math.exp(-theta)) / 2.0;
    }

    /**
     * Hyperbolic sine of this Complex number
     * (doesn't change this Complex number).
     * <br>
     * sinh(z) = (exp(z)-exp(-z))/2.
     * 
     * @return sinh(z) where z is this Complex number.
     */
    public Complex sinh() {
        return new Complex(sinh(re) * Math.cos(im), cosh(re) * Math.sin(im));
    }

    /**
     * Hyperbolic cosine of this Complex number.
     * 
     * @return cosh(z) = (exp(z) + exp(-z)) / 2 where z is complex.
     */
    public Complex cosh() {
        return new Complex(cosh(re) * Math.cos(im), sinh(re) * Math.sin(im));
    }

    /**
     * 
     * @return Hyperbolic tangent of this complex number.
     */
    public Complex tanh() {
        return sinh().div(cosh());
    }

    /**
     * Compare two complex numbers for equality.
     * 
     * @return boolean
     */
    public boolean equals(Object x) {
        if (x == null)
            return false;
        if (this.getClass() != x.getClass())
            return false;

        Complex that = (Complex) x;
        return (this.re == that.re) && (this.im == that.im);
    }

    public int hashCode() {
        return Objects.hash(re, im);
    }

    /**
     * Principal branch of the Complex logarithm of this Complex number.
     * The principal branch is the branch with -pi &lt; arg &ge; pi.
     * 
     * @return log(z) where z is this Complex number.
     */
    public Complex log() {
        return new Complex(Math.log(this.abs()), this.arg());
    }

    /**
     * Complex square root.
     * See https://www.geeksforgeeks.org/square-root-of-two-complex-numbers/
     * and
     * https://www.tabnine.com/code/java/methods/org.apache.commons.math3.complex.Complex/sqrt
     * 
     * @return a Vector of complex roots as there may be more than one root.
     */
    public Vector<Complex> sqrt() {
        var roots = new Vector<Complex>();

        Complex c1 = power(1.0 / 2.0);
        roots.add(c1);
        roots.add(c1.negate());
        return roots;
    }

    /**
     * Round value up to 5 decimal places.
     * 
     * @param value
     * @return
     */
    public double roundDouble(double value) {
        return BigDecimal.valueOf(value).setScale(5, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Wraps value in BigDecimal using BigDecimal.valueOf() function.
     * 
     * @param value
     * @return
     */
    public BigDecimal asDecimal(double value) {
        return BigDecimal.valueOf(value);
    }

    /**
     * Computes the cube root of this complex number.
     * 
     * @return
     */
    public Complex cbrt() {
        return power(1.0d / 3.0d);
    }

    /**
     * Negative of this complex number.
     * This produces a new Complex number.
     * <br>
     * -(x+i*y) = -x-i*y.
     * 
     * @return -z where z is this Complex number.
     */
    public Complex negate() {
        return new Complex(-re, -im);
    }

    /**
     * Express the complex number in polar form.
     * Z = r(cosθ + isinθ)
     * 
     * @return
     */
    public Polar polar() {
        double theta = 0d, a, b, r;
        a = re;
        b = im;

        // Calculate the modulus
        r = Math.sqrt(a * a + b * b);

        if (a > 0) {
            theta = Math.atan(b / a);
        } else if (a < 0) {
            theta = Math.atan(b / a) + Math.PI;
        }

        return new Polar(r, theta);
    }

    /**
     * Sample main function for testing
     * Run it your self to confirm.
     */
    public static void main(String[] args) {
        Complex a = new Complex(5.0, 6.0);
        Complex b = new Complex(-3.0, 4.0);
        Complex c = new Complex(-1, 0);
        Complex d = new Complex(4, 0);
        Complex e = new Complex(2, -4);
        Complex f = new Complex(5, 2);

        System.out.println("Re(a)        = " + a.real());
        System.out.println("Im(a)        = " + a.imag());
        System.out.println("b + a        = " + b.plus(a));
        System.out.println("a - b        = " + a.minus(b));
        System.out.println("a * b        = " + a.times(b));
        System.out.println("b * a        = " + b.times(a));
        System.out.println("a / b        = " + a.div(b));
        System.out.println("(a / b) * b  = " + a.div(b).times(b));
        System.out.println("conj(a)      = " + a.conjugate());
        System.out.println("|a|          = " + a.abs());
        System.out.println("arg(a)       = " + a.arg());
        System.out.println("sin(a)       = " + a.sin());
        System.out.println("cos(a)       = " + a.cos());
        System.out.println("tan(a)       = " + a.tan());
        System.out.println("sinh(a)      = " + a.sinh());
        System.out.println("cosh(a)      = " + a.cosh());
        System.out.println("log(a)       = " + a.log());
        System.out.println("e.pow(3)     = " + e.power(3));

        System.out.println("e.sqrt     = " + e.sqrt());
        System.out.println("e.pow(1/2)     = " + e.power(1.0 / 2.0)); // same as sqrt
        System.out.println("e.pow(1/3)     = " + e.power(1.0 / 3.0)); // same as cbrt

        System.out.println("f.polar()     = " + f.polar());
        System.out.println("(1+root(3)i)^4     = " + new Complex(1, Math.sqrt(3)).power(4));

        for (var cmpl : c.sqrt()) {
            System.out.println("sqrt(c)      = " + cmpl);
        }

        for (var cmpl : d.sqrt()) {
            System.out.println("sqrt(d)      = " + cmpl);
        }

        Complex z = new Complex(1, -1);
        System.out.println(z.real() + " " + z.imag());
        System.out.println("cbrt(1-i)     = " + z.cbrt());
        System.out.println("tanh(1-i)     = " + z.tanh());

        // More square roots
        Complex z1, z2, z3;
        z1 = new Complex(-21, -20);
        z2 = new Complex(7, -24);
        z3 = new Complex(0, -1);

        System.out.println("sqrt(-21 -20i)     = " + z1.sqrt());
        System.out.println("sqrt(7-24i)     = " + z2.sqrt());
        System.out.println("sqrt(-1)     = " + z3.sqrt());
    }
}
