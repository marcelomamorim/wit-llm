/*
 * @test
 * @summary Deterministic tests for java.util.SplittableRandom.nextInt(int bound)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testNextIntWithValidBounds();
        testNextIntWithInvalidBounds();
    }

    private static void testNextIntWithValidBounds() {
        SplittableRandom sr = new SplittableRandom(12345L);

        // Test with bound = 1 (lowest positive bound)
        int result = sr.nextInt(1);
        if (result != 0) {
            throw new AssertionError("Expected 0 for bound=1 but got " + result);
        }

        // Test with a power of two bound
        int bound = 16;
        for (int i = 0; i < 1000; i++) {
            int val = sr.nextInt(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range: " + val);
            }
        }

        // Test with a non-power of two bound
        bound = 15;
        for (int i = 0; i < 1000; i++) {
            int val = sr.nextInt(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range: " + val);
            }
        }
    }

    private static void testNextIntWithInvalidBounds() {
        SplittableRandom sr = new SplittableRandom(67890L);

        // Test with bound = 0, expect IllegalArgumentException
        try {
            sr.nextInt(0);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test with bound < 0, expect IllegalArgumentException
        try {
            sr.nextInt(-1);
            throw new AssertionError("Expected IllegalArgumentException for bound=-1");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
