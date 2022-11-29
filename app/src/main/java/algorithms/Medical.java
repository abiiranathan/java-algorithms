package algorithms;

import java.util.List;

/**
 * Contains algorithms to compute common medical, physiology, pharmacokinetic
 * and other formulae.
 */
public class Medical {
    /**
     * Calculates the Mean arterial pressure
     * 
     * @param sbp Systolic blood pressure
     * @param dbp Diastolic blood pressure
     * @return The Mean Arterial Pressure
     */
    public double MAP(final double sbp, final double dbp) {
        return (dbp + (1.0 / 3.0 * (sbp - dbp)));
    }

    /**
     * Calculate the body mass index
     * 
     * @param wt Weight in kilograms
     * @param ht Height in centimeters
     * @return BMI
     */
    public double bmi(final double wt, final double ht) {
        return wt / Math.pow(ht / 100, 2);
    }

    /**
     * Calculates the area under a curve by Trapezoid rule.
     * points define the curve. Important in pharmacology
     * to estimate the bio-availability on a concentration-time
     * curve.
     * 
     * @param points A list of (x, y) points.
     * @return Area under the curve.
     */
    double AUC(List<Point> points) {
        if (points.size() < 2) {
            System.out.println("you must provide atleast 2 points");
            return 0;
        }

        // Calculate the size of each trapezoid
        double h, sum, innersum = 0;
        int n = points.size();

        h = points.get(1).x - points.get(0).x;

        // Apply the trapezoid rule
        // I= h/2[(y0+yn) + 2(y1+y2+y3+.....+yn-1)]

        // h/2(y0 + yn) yn is that last point so it's points[n-1]
        sum = (h / 2) * (points.get(0).y + points.get(n - 1).y);
        // h(y1+ y2+y3 +... + yn-1)

        for (int i = 1; i < n - 1; i++) {
            innersum += points.get(i).y;
        }

        return sum + h * innersum;
    }

    /**
     * Calculates the drip rate(drops/min) required to infuse volume,
     * v in mL over a time, t(hour) given the drip factor, df(gtt/mL).
     * 
     * @param volume     Total volume to be infused in mls
     * @param dropFactor drop factor (gtt/min)
     * @param duration   duration in hours
     * @return drops/min rounded down to the nearest integer.
     */
    int dropsPerMinute(double volume, double dropFactor, double duration) {
        return (int) ((volume / (duration * 60)) * dropFactor);
    }

    /**
     * Calculates the drip rate(drops/sec) required to infuse volume,
     * v in mL over a time, t(hour) given the drip factor, df(gtt/mL).
     * 
     * @param volume     Total volume to be infused in mls
     * @param dropFactor drop factor (gtt/min)
     * @param duration   duration in hours
     * @return drops/sec rounded up to the nearest integer.
     */
    int dropsPerSecond(double volume, double dropFactor, double duration) {
        return (int) Math.ceil((volume / (duration * 3600)) * dropFactor);
    }
}
