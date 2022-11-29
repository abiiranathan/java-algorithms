package algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class MedicalCalcTest {
    private static Medical calc = new Medical();
    final double episilon = 0.000001d;

    @Test
    void testAUC() {
        List<Point> points = new ArrayList<>(5);
        points.add(new Point(0.25, 0.2474));
        points.add(new Point(0.26, 0.2571));
        points.add(new Point(0.27, 0.2667));
        points.add(new Point(0.28, 0.2764));
        points.add(new Point(0.29, 0.2860));

        var auc = calc.AUC(points);
        assertEquals(auc, 0.010669, episilon);
    }

    @Test
    void testBmi() {
        assertEquals(25, calc.bmi(64.0, 160), episilon);
    }

    @Test
    void testDripRate() {
        assertEquals(333, calc.dropsPerMinute(2000, 20, 2));
        assertEquals(6, calc.dropsPerSecond(2000, 20, 2));
    }

    @Test
    void testMap() {
        assertEquals(95, calc.MAP(125, 80));
    }

}
