/*
 * @test
 * @summary Deterministic tests for ThreadLocalRandom.nextLong(long, long) method
 * @run main ThreadLocalRandomWitupTest_Pd06ee2c0ded7
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_Pd06ee2c0ded7 {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeThrows();
    }

    private static void testValidRange() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        long origin = 10L;
        long bound = 20L;
        for (int i = 0; i < 1000; i++) {
            long val = rnd.nextLong(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of expected range [" + origin + ", " + bound + ")");
            }
        }
    }

    private static void testInvalidRangeThrows() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        long origin = 20L;
        long bound = 10L;
        boolean thrown = false;
        try {
            rnd.nextLong(origin, bound);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException when bound <= origin");
        }
    }
}
