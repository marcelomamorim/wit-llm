/*
 * @test
 * @summary Test SplittableRandom.nextInt(int origin, int bound) for valid and invalid ranges
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        SplittableRandom rng = new SplittableRandom(12345L);

        // Test valid range: origin < bound
        int origin = 10;
        int bound = 20;
        for (int i = 0; i < 100; i++) {
            int val = rng.nextInt(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }

        // Test boundary condition: origin == bound should throw IllegalArgumentException
        try {
            rng.nextInt(5, 5);
            throw new AssertionError("Expected IllegalArgumentException for origin == bound");
        } catch (IllegalArgumentException expected) {
        }

        // Test invalid range: bound < origin should throw IllegalArgumentException
        try {
            rng.nextInt(10, 5);
            throw new AssertionError("Expected IllegalArgumentException for bound < origin");
        } catch (IllegalArgumentException expected) {
        }

        // Test negative ranges
        origin = -20;
        bound = -10;
        for (int i = 0; i < 100; i++) {
            int val = rng.nextInt(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }

        // Test full int range except upper bound
        origin = Integer.MIN_VALUE;
        bound = Integer.MIN_VALUE + 1000;
        for (int i = 0; i < 100; i++) {
            int val = rng.nextInt(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }
    }
}
