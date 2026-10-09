/* @test    @summary Test for ThreadLocalRandom.nextLong(long, long) with boundary conditions    @run main ThreadLocalRandomWitupTest_P84de645c0d70 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P84de645c0d70 {

    public static void main(String[] args) {
        testNextLongWithInvalidRange();
        testNextLongWithValidRange();
    }

    private static void testNextLongWithInvalidRange() {
        long origin = 10L;
        long bound = 5L;
        try {
            ThreadLocalRandom.current().nextLong(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for bound <= origin");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }

    private static void testNextLongWithValidRange() {
        long origin = 5L;
        long bound = 15L;
        for (int i = 0; i < 10; i++) {
            long result = ThreadLocalRandom.current().nextLong(origin, bound);
            if (result < origin || result >= bound) {
                throw new AssertionError("Result out of range: " + result);
            }
        }
    }
}
