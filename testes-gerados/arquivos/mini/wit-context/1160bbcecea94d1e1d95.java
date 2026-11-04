/*
 * @test
 * @summary Deterministic tests for ThreadLocalRandom.nextInt(int bound) method
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNextIntWithValidBounds();
        testNextIntWithInvalidBounds();
    }

    private static void testNextIntWithValidBounds() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        // Test with bound = 1, should always return 0
        int result = rnd.nextInt(1);
        if (result != 0) {
            throw new AssertionError("Expected 0 for bound=1 but got " + result);
        }

        // Test with a power of two bound, e.g., 8
        for (int i = 0; i < 1000; i++) {
            int val = rnd.nextInt(8);
            if (val < 0 || val >= 8) {
                throw new AssertionError("Value out of range for bound=8: " + val);
            }
        }

        // Test with a non-power of two bound, e.g., 7
        for (int i = 0; i < 1000; i++) {
            int val = rnd.nextInt(7);
            if (val < 0 || val >= 7) {
                throw new AssertionError("Value out of range for bound=7: " + val);
            }
        }
    }

    private static void testNextIntWithInvalidBounds() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        // Test with bound = 0, expect IllegalArgumentException
        try {
            rnd.nextInt(0);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test with bound = -1, expect IllegalArgumentException
        try {
            rnd.nextInt(-1);
            throw new AssertionError("Expected IllegalArgumentException for bound=-1");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test with bound = Integer.MIN_VALUE, expect IllegalArgumentException
        try {
            rnd.nextInt(Integer.MIN_VALUE);
            throw new AssertionError("Expected IllegalArgumentException for bound=Integer.MIN_VALUE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
