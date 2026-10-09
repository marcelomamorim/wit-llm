/*
 * @test
 * @summary Test for ThreadLocalRandom.nextInt(int, int)
 * @run main ThreadLocalRandomWitupTest_P199b36fda2bb
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P199b36fda2bb {

    public static void main(String[] args) {
        testNextIntValidRange();
        testNextIntInvalidRange();
    }

    private static void testNextIntValidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int origin = 10;
        int bound = 20;
        int result = rand.nextInt(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result out of range: " + result);
        }
    }

    private static void testNextIntInvalidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int origin = 20;
        int bound = 10;
        try {
            rand.nextInt(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
