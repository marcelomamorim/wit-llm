/* @test    @summary Test ThreadLocalRandom.nextLong(long bound) for correct behavior and exception handling    @run main ThreadLocalRandomWitupTest */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNextLongWithValidBounds();
        testNextLongWithZeroBound();
        testNextLongWithNegativeBound();
    }

    static void testNextLongWithValidBounds() {
        long[] bounds = {1L, 10L, Long.MAX_VALUE, 1000L};
        for (long bound : bounds) {
            long result = ThreadLocalRandom.current().nextLong(bound);
            if (result < 0 || result >= bound) {
                throw new AssertionError("Result " + result + " out of bounds [0, " + bound + ") for bound " + bound);
            }
        }
    }

    static void testNextLongWithZeroBound() {
        boolean exceptionThrown = false;
        try {
            ThreadLocalRandom.current().nextLong(0L);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for zero bound") ;
        }
    }

    static void testNextLongWithNegativeBound() {
        boolean exceptionThrown = false;
        try {
            ThreadLocalRandom.current().nextLong(-5L);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative bound") ;
        }
    }
}
