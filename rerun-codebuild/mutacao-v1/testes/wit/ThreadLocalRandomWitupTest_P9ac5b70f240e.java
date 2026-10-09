/* @test    @summary Test for ThreadLocalRandom.nextDouble(double, double) with invalid bounds    @run main ThreadLocalRandomWitupTest_P9ac5b70f240e */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P9ac5b70f240e {
    public static void main(String[] args) {
        testNextDoubleInvalidBounds();
    }

    private static void testNextDoubleInvalidBounds() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double origin = 5.0;
        double bound = 3.0;
        try {
            rand.nextDouble(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for bound <= origin");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
