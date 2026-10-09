/*
 * @test
 * @summary Test ThreadLocalRandom.nextDouble(double origin, double bound) for valid and invalid ranges
 * @run main ThreadLocalRandomWitupTest_P420ce1d71e5d
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P420ce1d71e5d {
    public static void main(String[] args) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        // Test valid range: origin < bound
        double origin = 1.0;
        double bound = 2.0;
        double val = rnd.nextDouble(origin, bound);
        if (!(val >= origin && val < bound)) {
            throw new AssertionError("Returned value " + val + " not in range [" + origin + ", " + bound + ")");
        }

        // Test invalid range: origin == bound, expect IllegalArgumentException
        try {
            rnd.nextDouble(1.0, 1.0);
            throw new AssertionError("Expected IllegalArgumentException for origin == bound");
        } catch (IllegalArgumentException expected) {
        }

        // Test invalid range: origin > bound, expect IllegalArgumentException
        try {
            rnd.nextDouble(2.0, 1.0);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException expected) {
        }

        // Test with negative bounds
        origin = -5.0;
        bound = -1.0;
        val = rnd.nextDouble(origin, bound);
        if (!(val >= origin && val < bound)) {
            throw new AssertionError("Returned value " + val + " not in range [" + origin + ", " + bound + ")");
        }

        // Test with large values
        origin = 1e10;
        bound = 1e10 + 1.0;
        val = rnd.nextDouble(origin, bound);
        if (!(val >= origin && val < bound)) {
            throw new AssertionError("Returned value " + val + " not in range [" + origin + ", " + bound + ")");
        }
    }
}
