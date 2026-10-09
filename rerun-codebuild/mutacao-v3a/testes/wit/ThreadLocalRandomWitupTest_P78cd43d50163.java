/*
 * @test
 * @summary Tests for java.util.concurrent.ThreadLocalRandom.nextLong(long, long)
 * @run main ThreadLocalRandomWitupTest_P78cd43d50163
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P78cd43d50163 {

    public static void main(String[] args) {
        testNextLongWithValidRange();
        testNextLongWithInvalidRange();
    }

    private static void testNextLongWithValidRange() {
        long origin = 10L;
        long bound = 20L;
        long result = ThreadLocalRandom.current().nextLong(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result " + result + " not in range [" + origin + ", " + bound + ")");
        }
    }

    private static void testNextLongWithInvalidRange() {
        long origin = 20L;
        long bound = 10L;
        try {
            ThreadLocalRandom.current().nextLong(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin=" + origin + " and bound=" + bound);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
