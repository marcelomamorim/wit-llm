/*
 * @test
 * @summary Test SplittableRandom.nextLong(long origin, long bound) for boundary and exception cases
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testNextLongValidRange();
        testNextLongInvalidRange();
    }

    private static void testNextLongValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long origin = 10L;
        long bound = 20L;
        for (int i = 0; i < 100; i++) {
            long val = sr.nextLong(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }
    }

    private static void testNextLongInvalidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long origin = 20L;
        long bound = 10L;
        boolean threw = false;
        try {
            sr.nextLong(origin, bound);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException when bound <= origin");
        }
    }
}
