/*
 * @test
 * @summary Witup-derived checks for SplittableRandom.doubles(double, double) range validation and deterministic output
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testInvalidRangeThrows(0.0, 0.0);
        testInvalidRangeThrows(1.0, 0.0);
        testInvalidRangeThrows(-1.0, -2.0);
        testInvalidRangeThrows(Double.NaN, 1.0);
        testInvalidRangeThrows(0.0, Double.NaN);
        testValidRangeBounds();
        testDeterministicForSameSeed();
    }

    static void testInvalidRangeThrows(double origin, double bound) {
        SplittableRandom r = new SplittableRandom(42L);
        boolean thrown = false;
        try {
            r.doubles(origin, bound);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("invalid range accepted: origin=" + origin + " bound=" + bound);
        }
    }

    static void testValidRangeBounds() {
        SplittableRandom r = new SplittableRandom(42L);
        double[] vals = r.doubles(-2.5, 7.5).limit(1000).toArray();
        if (vals.length != 1000) {
            throw new RuntimeException("unexpected length: " + vals.length);
        }
        for (double v : vals) {
            if (v < -2.5 || v >= 7.5) {
                throw new RuntimeException("value out of range: " + v);
            }
        }
    }

    static void testDeterministicForSameSeed() {
        SplittableRandom a = new SplittableRandom(7L);
        SplittableRandom b = new SplittableRandom(7L);
        double[] x = a.doubles(0.0, 1.0).limit(50).toArray();
        double[] y = b.doubles(0.0, 1.0).limit(50).toArray();
        if (!Arrays.equals(x, y)) {
            throw new RuntimeException("same seed produced different sequences");
        }
    }
}
