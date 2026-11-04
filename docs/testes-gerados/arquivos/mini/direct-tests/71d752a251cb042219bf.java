/*
 * @test
 * @summary Test java.util.SplittableRandom.nextDouble(double origin, double bound) method
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        SplittableRandom rng = new SplittableRandom(12345L);

        // Test valid range: origin < bound
        double origin = 1.0;
        double bound = 2.0;
        double val = rng.nextDouble(origin, bound);
        if (!(val >= origin && val < bound)) {
            throw new AssertionError("Value " + val + " not in range [" + origin + ", " + bound + ")");
        }

        // Test valid range with negative values
        origin = -5.0;
        bound = -3.0;
        val = rng.nextDouble(origin, bound);
        if (!(val >= origin && val < bound)) {
            throw new AssertionError("Value " + val + " not in range [" + origin + ", " + bound + ")");
        }

        // Test valid range with origin and bound close
        origin = 0.0;
        bound = 0.000001;
        val = rng.nextDouble(origin, bound);
        if (!(val >= origin && val < bound)) {
            throw new AssertionError("Value " + val + " not in range [" + origin + ", " + bound + ")");
        }

        // Test invalid range: origin == bound
        try {
            rng.nextDouble(1.0, 1.0);
            throw new AssertionError("Expected IllegalArgumentException for origin == bound");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test invalid range: origin > bound
        try {
            rng.nextDouble(2.0, 1.0);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        System.out.println("All tests passed.");
    }
}
