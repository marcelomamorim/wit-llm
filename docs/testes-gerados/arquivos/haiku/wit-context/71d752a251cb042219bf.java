/*
 * @test
 * @summary Witup-generated tests for SplittableRandom.nextDouble(double, double) range validation
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        SplittableRandom r = new SplittableRandom(42L);

        expectIllegalArgument(r, 1.0, 1.0);
        expectIllegalArgument(r, 2.0, 1.0);
        expectIllegalArgument(r, -1.0, -2.0);
        expectIllegalArgument(r, Double.NaN, 1.0);
        expectIllegalArgument(r, 0.0, Double.NaN);

        for (int i = 0; i < 1000; i++) {
            double origin = -3.5;
            double bound = 7.25;
            double v = r.nextDouble(origin, bound);
            if (v < origin || v >= bound) {
                throw new RuntimeException("value out of range: " + v);
            }
        }

        SplittableRandom a = new SplittableRandom(7L);
        SplittableRandom b = new SplittableRandom(7L);
        for (int i = 0; i < 100; i++) {
            double x = a.nextDouble(0.0, 10.0);
            double y = b.nextDouble(0.0, 10.0);
            if (Double.compare(x, y) != 0) {
                throw new RuntimeException("non-deterministic sequence at " + i);
            }
        }
    }

    private static void expectIllegalArgument(SplittableRandom r, double origin, double bound) {
        boolean thrown = false;
        try {
            r.nextDouble(origin, bound);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for origin=" + origin + " bound=" + bound);
        }
    }
}
