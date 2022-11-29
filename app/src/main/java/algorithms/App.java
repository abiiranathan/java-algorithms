package algorithms;

// Simple main class to test all the algorithms
public class App {
    private static Medical calc;

    public static void main(String[] args) {
        calc = new Medical();
        double map = calc.MAP(120, 80);

        System.out.printf("The MAP of this patient is %.3f mmHg\n", map);
    }
}
