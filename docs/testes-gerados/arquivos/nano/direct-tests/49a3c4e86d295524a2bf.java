/* @test    @summary Test for ThreadLocalRandom.nextLong(long, long) method    @run main ThreadLocalRandomWitupTest */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNextLongWithinRange();
        testNextLongInvalidRange();
    }

    private static void testNextLongWithinRange() {
        long origin = 10L;
        long bound = 20L;
        for (int i = 0; i < 100; i++) {
            long result = ThreadLocalRandom.current().nextLong(origin, bound);
            if (result < origin || result >= bound) {
                throw new AssertionError("Result " + result + " out of range [" + origin + ", " + bound + ")");
            }
        }
    }

    private static void testNextLongInvalidRange() {
        long origin = 30L;
        long bound = 20L;
        boolean exceptionThrown = false;
        try {
            ThreadLocalRandom.current().nextLong(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid range")
        }
    }
}
