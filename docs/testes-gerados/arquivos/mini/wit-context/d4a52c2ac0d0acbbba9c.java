/*
 * @test
 * @summary Deterministic tests for ThreadLocalRandom.nextLong(long bound) method
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNextLongWithValidBounds();
        testNextLongWithInvalidBounds();
    }

    private static void testNextLongWithValidBounds() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        // Test with bound = 1, should always return 0
        long result = rnd.nextLong(1L);
        if (result != 0L) {
            throw new AssertionError("Expected 0 for bound=1 but got " + result);
        }

        // Test with a power of two bound, e.g., 8
        long bound = 8L;
        for (int i = 0; i < 1000; i++) {
            long val = rnd.nextLong(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range for bound=8: " + val);
            }
        }

        // Test with a non-power of two bound, e.g., 10
        bound = 10L;
        for (int i = 0; i < 1000; i++) {
            long val = rnd.nextLong(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range for bound=10: " + val);
            }
        }
    }

    private static void testNextLongWithInvalidBounds() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        // Test with bound = 0, expect IllegalArgumentException
        try {
            rnd.nextLong(0L);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test with bound = -1, expect IllegalArgumentException
        try {
            rnd.nextLong(-1L);
            throw new AssertionError("Expected IllegalArgumentException for bound=-1");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test with bound = Long.MIN_VALUE, expect IllegalArgumentException
        try {
            rnd.nextLong(Long.MIN_VALUE);
            throw new AssertionError("Expected IllegalArgumentException for bound=Long.MIN_VALUE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
