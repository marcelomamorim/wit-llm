/*
 * @test
 * @summary Checks nextDouble(origin, bound) range validation and bounded output
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        SplittableRandom r = new SplittableRandom(42L);

        expectIllegalArgument(r, 1.0, 1.0);
        expectIllegalArgument(r, 2.0, 1.0);
        expectIllegalArgument(r, Double.NaN, 1.0);
        expectIllegalArgument(r, 0.0, Double.NaN);

        SplittableRandom a = new SplittableRandom(7L);
        SplittableRandom b = new SplittableRandom(7L);
        for (int i = 0; i < 1000; i++) {
            double va = a.nextDouble(-3.5, 7.25);
            double vb = b.nextDouble(-3.5, 7.25);
            if (Double.compare(va, vb) != 0) {
                throw new RuntimeException("Same seed produced different values at " + i);
            }
        }

        SplittableRandom g = new SplittableRandom(12345L);
        for (int i = 0; i < 10000; i++) {
            double v = g.nextDouble(-3.5, 7.25);
            if (Double.isNaN(v) || v < -3.5 || v >= 7.25) {
                throw new RuntimeException("Value out of range: " + v);
            }
        }

        SplittableRandom h = new SplittableRandom(99L);
        for (int i = 0; i < 10000; i++) {
            double v = h.nextDouble(0.0, Double.MIN_VALUE * 4);
            if (Double.isNaN(v) || v < 0.0 || v >= Double.MIN_VALUE * 4) {
                throw new RuntimeException("Tiny range value out of range: " + v);
            }
        }
    }

    private static void expectIllegalArgument(SplittableRandom r, double origin, double bound) {
        try {
            r.nextDouble(origin, bound);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for origin="
                + origin + ", bound=" + bound);
    }
}
