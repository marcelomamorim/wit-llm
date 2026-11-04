/*
 * @test
 * @summary Test SplittableRandom.nextDouble(double origin, double bound) for valid and invalid ranges
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

        // Test invalid range: origin == bound, expect IllegalArgumentException
        try {
            rng.nextDouble(5.0, 5.0);
            throw new AssertionError("Expected IllegalArgumentException for origin == bound");
        } catch (IllegalArgumentException expected) {
        }

        // Test invalid range: origin > bound, expect IllegalArgumentException
        try {
            rng.nextDouble(10.0, 5.0);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException expected) {
        }

        // Test with negative bounds
        origin = -10.0;
        bound = -5.0;
        val = rng.nextDouble(origin, bound);
        if (!(val >= origin && val < bound)) {
            throw new AssertionError("Value " + val + " not in range [" + origin + ", " + bound + ")");
        }

        // Test with large range
        origin = 1e10;
        bound = 1e10 + 1000.0;
        val = rng.nextDouble(origin, bound);
        if (!(val >= origin && val < bound)) {
            throw new AssertionError("Value " + val + " not in range [" + origin + ", " + bound + ")");
        }
    }
}
