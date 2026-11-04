/* @test    @summary Test for ThreadLocalRandom.nextLong(long, long) method handling of invalid range    @run main ThreadLocalRandomWitupTest */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNextLongInvalidRange();
        testNextLongValidRange();
    }

    private static void testNextLongInvalidRange() {
        try {
            ThreadLocalRandom.current().nextLong(10L, 5L);
            System.err.println("Failed: Expected IllegalArgumentException for origin > bound");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testNextLongValidRange() {
        long origin = 5L;
        long bound = 10L;
        long result = ThreadLocalRandom.current().nextLong(origin, bound);
        if (result < origin || result >= bound) {
            System.err.println("Failed: Result out of range: " + result);
            System.exit(1);
        }
    }
}
