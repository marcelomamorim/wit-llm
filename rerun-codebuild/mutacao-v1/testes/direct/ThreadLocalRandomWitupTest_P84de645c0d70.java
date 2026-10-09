/* @test    @summary Test for ThreadLocalRandom.nextLong(long, long) method behavior and exception handling    @run main ThreadLocalRandomWitupTest_P84de645c0d70 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P84de645c0d70 {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
        testEdgeCases();
    }

    static void testValidRange() {
        long origin = 10L;
        long bound = 20L;
        long result = ThreadLocalRandom.current().nextLong(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result " + result + " not in range [" + origin + ", " + bound + ")");
        }
    }

    static void testInvalidRange() {
        long origin = 30L;
        long bound = 20L;
        boolean exceptionThrown = false;
        try {
            ThreadLocalRandom.current().nextLong(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid range") ;
        }
    }

    static void testEdgeCases() {
        long origin = Long.MIN_VALUE;
        long bound = Long.MIN_VALUE + 1;
        long result = ThreadLocalRandom.current().nextLong(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Edge case result " + result + " not in range [" + origin + ", " + bound + ")");
        }
        origin = Long.MAX_VALUE - 1;
        bound = Long.MAX_VALUE;
        result = ThreadLocalRandom.current().nextLong(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Edge case result " + result + " not in range [" + origin + ", " + bound + ")");
        }
    }
}
