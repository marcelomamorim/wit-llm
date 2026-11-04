/*
 * @test
 * @summary Deterministic tests for java.util.SplittableRandom.nextLong(long bound)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testNextLongWithValidBounds();
        testNextLongWithInvalidBounds();
    }

    private static void testNextLongWithValidBounds() {
        SplittableRandom sr = new SplittableRandom(12345L);

        // Test with bound = 1, should always return 0
        long result = sr.nextLong(1L);
        if (result != 0L) {
            throw new AssertionError("Expected 0 for bound=1, got " + result);
        }

        // Test with a power of two bound
        long bound = 16L;
        for (int i = 0; i < 1000; i++) {
            long val = sr.nextLong(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range for bound=16: " + val);
            }
        }

        // Test with a non-power of two bound
        bound = 15L;
        for (int i = 0; i < 1000; i++) {
            long val = sr.nextLong(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range for bound=15: " + val);
            }
        }
    }

    private static void testNextLongWithInvalidBounds() {
        SplittableRandom sr = new SplittableRandom(67890L);

        // Test with bound = 0, expect IllegalArgumentException
        try {
            sr.nextLong(0L);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test with bound < 0, expect IllegalArgumentException
        try {
            sr.nextLong(-1L);
            throw new AssertionError("Expected IllegalArgumentException for bound=-1");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
