package algorithms;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nullable;

import cmp.Compare;

/**
 * Third degree polynomial.
 * Represents the polynomial in the form x³ + px + q = 0
 */
public class Poly3 {

    private final double a, b, c, d;

    public Poly3(double a, double b, double c, double d) {
        if (a == 0) {
            throw new IllegalArgumentException("coefficient a must be greater than zero");
        }

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     *      String representation of this polynomial.
     * @return String
     */
    @Override
    public String toString() {
        String numberFmt = generateNumberSigns(4);
        DecimalFormat fmt = new DecimalFormat(numberFmt);
        return genString(fmt);
    }

    /**
     * Helper method to generate the string representation of the polynomial.
     * 
     * @param fmt DecimalFormat
     * @return Formatted decimal string.
     */
    private String genString(DecimalFormat fmt) {
        String eq = String.format(
                "%sx³ + %sx² + %sx + %s",
                format(a, fmt),
                format(b, fmt),
                format(c, fmt),
                fmt.format(d));

        return cleanEquation(eq);
    }

    /**
     * String representation with the specified decimal precision
     * 
     * @param d Number of decimal places
     * @return Formatted string
     */
    public String toString(int d) {
        String numberFmt = generateNumberSigns(d);
        DecimalFormat fmt = new DecimalFormat(numberFmt);
        return genString(fmt);
    }

    private static String generateNumberSigns(int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += "#";
        }
        return s;

    }

    /**
     * Replace redundant 1 digit in value.
     * 
     * @param value double
     * @param fmt   DecimalFormat
     * 
     * @return The formatted string.
     */
    private static String format(double value, DecimalFormat fmt) {
        if (value == 1d)
            return "";
        return fmt.format(value);
    };

    /**
     * Replace redundant terms in the polynomial and fix multiple signs.
     * 
     * @param eq The equation to clean.
     * @return Properly formatted equation.
     */
    private static String cleanEquation(String eq) {
        return eq.replace("+ -", "- ")
                .replace(" + 0x²", "")
                .replace(" + 0x", "")
                .replace("-1x", "-x")
                .replace(" + 0", "");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(a);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(b);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(c);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(d);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Poly3 other = (Poly3) obj;
        if (Double.doubleToLongBits(a) != Double.doubleToLongBits(other.a))
            return false;
        if (Double.doubleToLongBits(b) != Double.doubleToLongBits(other.b))
            return false;
        if (Double.doubleToLongBits(c) != Double.doubleToLongBits(other.c))
            return false;
        if (Double.doubleToLongBits(d) != Double.doubleToLongBits(other.d))
            return false;
        return true;
    }

    /**
     * <pre>
     * Let A,B,C,D be real constants
     * such that A != 0, then Ax^3 + Bx^2+Cx+D=0;
     * 
     * Re-write the equation as X^3 + ax^2 + bx+c = 0 where
     * a=B/A, b=C/A, c=D/A, it follows from Cardano's formula that
     * 
     * p = b - a^2/3 and q=2a^3/27 - ab/3 +c, then
     * 
     * DELTA = q^2/4 + p^3/27.
     * </pre>
     * 
     * @return List of all real roots.
     */
    public List<Double> RealRoots() {
        double p, q, delta, x1, x2, x3;
        List<Double> roots = new ArrayList<>();

        // Divide by coeficient a throught the polynomial
        // Shadow the member variables.
        var a = this.b / this.a;
        var b = this.c / this.a;
        var c = this.d / this.a;

        p = b - (a * a / 3);
        q = (2 * a * a * a / 27) - (a * b / 3) + c;

        // Calculate the discreminant
        delta = (q * q / 4) + (p * p * p / 27);
        boolean equal = Compare.equal(delta, 0);

        if (equal) {
            // There are repeated roots
            x1 = -2 * cbrt(q / 2) - (a / 3);
            x2 = x3 = cbrt(q / 2) - (a / 3);

            roots.add(BigDecimal.valueOf(x1).setScale(4, RoundingMode.HALF_UP).doubleValue());
            roots.add(BigDecimal.valueOf(x2).setScale(4, RoundingMode.HALF_UP).doubleValue());
            roots.add(BigDecimal.valueOf(x3).setScale(4, RoundingMode.HALF_UP).doubleValue());
        } else if (delta > 0) {
            // There is only one real solution
            x1 = cbrt((-q / 2) + Math.sqrt(delta)) + cbrt((-q / 2) - Math.sqrt(delta)) - (a / 3);
            roots.add(BigDecimal.valueOf(x1).setScale(4, RoundingMode.HALF_UP).doubleValue());
        } else if (delta < 0) {
            // There are three distinct real roots
            // calculated using trigonometry to avoid complex numbers
            double Root3 = Math.sqrt(3);
            double RootMinusP = Math.sqrt(-p);
            double RootMinusPCubed = cubed(Math.sqrt(-p));

            x1 = (2 / Root3) * RootMinusP * Math.sin(1.0 / 3.0 * Math.asin((3 * Root3 * q) / (2 * RootMinusPCubed)))
                    - (a / 3);

            x2 = (-2 / Root3) * RootMinusP
                    * Math.sin(1.0 / 3.0 * Math.asin(((3 * Root3 * q) / (2 * RootMinusPCubed))) + Math.PI / 3)
                    - (a / 3);

            x3 = (2 / Root3) * RootMinusP
                    * Math.cos(1.0 / 3.0 * Math.asin(((3 * Root3 * q) / (2 * RootMinusPCubed))) + Math.PI / 6)
                    - (a / 3);

            roots.add(BigDecimal.valueOf(x1).setScale(4, RoundingMode.HALF_UP).doubleValue());
            roots.add(BigDecimal.valueOf(x2).setScale(4, RoundingMode.HALF_UP).doubleValue());
            roots.add(BigDecimal.valueOf(x3).setScale(4, RoundingMode.HALF_UP).doubleValue());
        }
        return roots;

    }

    /**
     * Compute the real and/or complex roots of this polynomial.
     * If you only care about the real roots, use {@link #RealRoots()} method.
     * 
     * @return List of all roots.
     */
    public List<Complex> Roots() {
        double p, q, delta, x1, x2, x3;
        List<Complex> roots = new ArrayList<>();

        // Divide by coeficient a throught the polynomial
        // Shadow the member variables.
        var a = this.b / this.a;
        var b = this.c / this.a;
        var c = this.d / this.a;

        p = b - (a * a / 3);
        q = (2 * a * a * a / 27) - (a * b / 3) + c;

        // Calculate the discreminant
        delta = (q * q / 4) + (p * p * p / 27);
        boolean equal = Compare.equal(delta, 0);

        if (equal) {
            // There are repeated roots
            x1 = -2 * cbrt(q / 2) - (a / 3);
            x2 = cbrt(q / 2) - (a / 3);

            x1 = BigDecimal.valueOf(x1).setScale(4, RoundingMode.HALF_UP).doubleValue();
            x2 = BigDecimal.valueOf(x2).setScale(4, RoundingMode.HALF_UP).doubleValue();
            x3 = x2;

            roots.add(new Complex(x1, 0));
            roots.add(new Complex(x2, 0));
            roots.add(new Complex(x3, 0));
            return roots;
        } else if (delta > 0) {
            // There is only one real solution
            x1 = cbrt((-q / 2) + Math.sqrt(delta)) + cbrt((-q / 2) - Math.sqrt(delta)) - (a / 3);
            Poly2 poly2 = syntheticDivision(x1);

            x1 = BigDecimal.valueOf(x1).setScale(4, RoundingMode.HALF_UP).doubleValue();
            roots.add(new Complex(x1, 0));

            // Perform long division to get a 2nd degree polynomial
            // Evaluate it to get the complex roots.
            if (poly2 != null) {
                var croots = poly2.croots();
                roots.addAll(croots);
            }
            return roots;
        } else if (delta < 0) {
            // There are three distinct real roots
            // calculated using trigonometry to avoid complex numbers
            double Root3 = Math.sqrt(3);
            double RootMinusP = Math.sqrt(-p);
            double RootMinusPCubed = cubed(Math.sqrt(-p));

            x1 = (2 / Root3) * RootMinusP * Math.sin(1.0 / 3.0 * Math.asin((3 * Root3 * q) / (2 * RootMinusPCubed)))
                    - (a / 3);

            x2 = (-2 / Root3) * RootMinusP
                    * Math.sin(1.0 / 3.0 * Math.asin(((3 * Root3 * q) / (2 * RootMinusPCubed))) + Math.PI / 3)
                    - (a / 3);

            x3 = (2 / Root3) * RootMinusP
                    * Math.cos(1.0 / 3.0 * Math.asin(((3 * Root3 * q) / (2 * RootMinusPCubed))) + Math.PI / 6)
                    - (a / 3);

            x1 = BigDecimal.valueOf(x1).setScale(4, RoundingMode.HALF_UP).doubleValue();
            x2 = BigDecimal.valueOf(x2).setScale(4, RoundingMode.HALF_UP).doubleValue();
            x2 = BigDecimal.valueOf(x3).setScale(4, RoundingMode.HALF_UP).doubleValue();

            roots.add(new Complex(x1, 0));
            roots.add(new Complex(x2, 0));
            roots.add(new Complex(x3, 0));
        }

        return roots;
    }

    /**
     * 
     * @param x Any real double.
     * @return The cube root of x.
     */
    private double cbrt(double x) {
        if (x < 0) {
            return -1 * Math.pow(-x, 1.0 / 3.0);
        }
        return Math.pow(x, 1.0 / 3.0);
    }

    /**
     * Raise x to power 3.
     * 
     * @param x Any real double.
     * @return
     */
    private double cubed(double x) {
        return Math.pow(x, 3.0);
    }

    /**
     * Evaluate this polynomial f(x) at x using horner's method.
     * Time complexity is O(n)
     * 
     * @param n The value to substitute into the polynomial.
     * 
     * @return The remainder after division. If the remainder is zero, then
     *         (x-n) is a root.
     */
    public double hornerEvaluate(double n) {
        final int polynomialDegree = 3;
        double poly[] = { a, b, c, d };
        double result = poly[0];

        for (int i = 1; i <= polynomialDegree; i++) {
            result = result * n + poly[i];
        }
        return result;
    }

    /**
     * Perform synthetic division on this polynomial
     * given that x is a root of this polynomial.
     * 
     * @param x The root of the polynomial
     * @return Returns a second degree polynomial of the form ax^2+bx+c or
     *         <b>null</b> if
     *         the remainder after division is not zero.
     */
    @Nullable
    public Poly2 syntheticDivision(double x) {
        double remainder;
        // Initialise the polynomial
        Poly2 poly = new Poly2(0, 0, 0);

        poly.setA(a);
        poly.setB(a * x + b);
        poly.setC(poly.getB() * x + c);

        remainder = Math.abs(poly.getC() * x + d);
        if (!Compare.equal(remainder, 0)) {
            return null;
        }
        return poly;
    }

    /**
     * Rational roots test, to return all possible roots of this polynomial.
     * a and d must be integers.
     * 
     * @return A set of all probable roots
     */
    public HashSet<Double> probableRoots() {
        var pr = new HashSet<Double>();
        for (int i = 1; i <= Math.abs(d); i++) {
            if (d % i == 0) {
                pr.add(i / a);
                pr.add(-i / a);
            }
        }
        return pr;
    }

    public static void main(String[] args) {
        Poly3 poly3 = new Poly3(1, -7, 41, -87);
        var roots = poly3.Roots();
        System.out.println(roots);
    }
}
