/*
 * @test
 * @summary Test for ThreadLocalRandom.nextDouble(double, double) method.
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNextDoubleValidRange();
        testNextDoubleInvalidRange();
    }

    private static void testNextDoubleValidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double origin = 0.0;
        double bound = 1.0;
        double result = rand.nextDouble(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result out of range: " + result);
        }
    }

    private static void testNextDoubleInvalidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double origin = 2.0;
        double bound = 1.0;
        boolean exceptionThrown = false;
        try {
            rand.nextDouble(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid range") ;
        }
    }
}
