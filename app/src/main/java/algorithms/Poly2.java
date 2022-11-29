package algorithms;

import java.text.DecimalFormat;
import java.util.Vector;

/**
 * Second degree polynomial data structure.
 */
public class Poly2 {
    private double a, b, c;

    public Poly2(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
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

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(double c) {
        this.c = c;
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

        Poly2 other = (Poly2) obj;
        System.out.println("other " + other);
        if (Double.doubleToLongBits(a) != Double.doubleToLongBits(other.a))
            return false;
        if (Double.doubleToLongBits(b) != Double.doubleToLongBits(other.b))
            return false;
        if (Double.doubleToLongBits(c) != Double.doubleToLongBits(other.c))
            return false;
        return true;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     * 
     *      String representation of this polynomial up to 4 decimal places.
     *      To adjust the decimal precision, use {@link #toString(int)}
     * 
     * @return String
     */
    @Override
    public String toString() {
        String numberFmt = generateNumberSigns(4);
        DecimalFormat fmt = new DecimalFormat(numberFmt);

        String eq = String.format("%sx² + %sx + %s", format(a, fmt), format(b, fmt), fmt.format(c));
        return cleanEquation(eq);
    }

    /**
     * Generates a dynamic string placeholder for n decimal places.
     * 
     * @param n Number of decimal places
     * @return String
     */
    private static String generateNumberSigns(int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += "#";
        }
        return s;

    }

    /**
     * Formats value with fmt id value is not an empty string.
     * 
     * @param value The double value to format
     * @param fmt   The Decimal formatter to format the value with.
     * @return The modified string.
     */
    private static String format(double value, DecimalFormat fmt) {
        if (value == 1d)
            return "";
        return fmt.format(value);
    };

    private static String cleanEquation(String eq) {
        return eq.replace("+ -", "- ")
                .replace(" + 0x", "")
                .replace("-1x", "-x")
                .replace(" + 0", "");
    }

    /**
     * Format polynomial.
     * 
     * @param d Number of decimal places
     * @return String representation of the polynomial
     */
    public String toString(int d) {
        String numberFmt = generateNumberSigns(d);
        DecimalFormat fmt = new DecimalFormat(numberFmt);

        String eq = String.format("%sx² + %sx + %s", format(a, fmt), format(b, fmt), fmt.format(c));
        return cleanEquation(eq);
    }

    /**
     * Solve the polymonial and return a vector real and/or complex roots.
     * 
     * If you are only interested in real roots, use {@link #roots()}
     * 
     * @return a vector of roots as complex numbers.
     */
    public Vector<Complex> croots() {
        double descriminant = (b * b) - (4 * a * c);
        var r = new Vector<Complex>();

        // Complex roots
        if (descriminant < 0.0) {
            double real = -b / 2 * a;
            double imag = Math.sqrt(-descriminant) / 2 * a;

            var c1 = new Complex(real, imag);
            var c2 = new Complex(real, -imag);
            r.add(c1);
            r.add(c2);
            return r;
        }

        // Real roots
        double x1 = (-b + Math.sqrt(descriminant)) / (2 * a);
        double x2 = (-b - Math.sqrt(descriminant)) / (2 * a);

        r.add(new Complex(x1, 0));
        r.add(new Complex(x2, 0));

        return r;
    }

    /**
     * Computes only the real roots of this polynomial.
     * If this polynomial has no real roots, the returned vector.size() ==0.
     * 
     * If you want real roots and complex roots use {@link #croots()}
     * 
     * @return a vector of doubles
     */
    public Vector<Double> roots() {
        double descr = (b * b) - (4 * a * c);
        var r = new Vector<Double>();
        if (descr > 0.0) {
            double x1 = (-b + Math.sqrt(descr)) / (2 * a);
            double x2 = (-b - Math.sqrt(descr)) / (2 * a);

            r.add(x1);
            r.add(x2);
        }
        return r;
    }

    /**
     * Returns true if the polynomial has real roots. ie discreminant &ge; 0
     * 
     * @return boolean
     */
    public boolean hasRealRoots() {
        double descr = (b * b) - (4 * a * c);
        return descr >= 0d;
    }
}
