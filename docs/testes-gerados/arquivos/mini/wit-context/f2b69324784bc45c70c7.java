/*
 * @test
 * @summary Deterministic tests for ThreadLocalRandom.nextInt(int origin, int bound)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNextIntValidRange();
        testNextIntInvalidRange();
    }

    private static void testNextIntValidRange() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int origin = 5;
        int bound = 10;
        for (int i = 0; i < 1000; i++) {
            int val = rnd.nextInt(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }
    }

    private static void testNextIntInvalidRange() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int origin = 10;
        int bound = 5;
        boolean threw = false;
        try {
            rnd.nextInt(origin, bound);
        } catch (IllegalArgumentException iae) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for bound <= origin");
        }
    }
}
