/* @test    @summary Test for ThreadLocalRandom.nextDouble(double, double) method    @run main ThreadLocalRandomWitupTest_P973c2eb3d1d2 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P973c2eb3d1d2 {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
        testEdgeCases();
    }

    private static void testValidRange() {
        double origin = 5.0;
        double bound = 10.0;
        double result = ThreadLocalRandom.current().nextDouble(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result " + result + " not in range [" + origin + ", " + bound + ")");
        }
    }

    private static void testInvalidRange() {
        double origin = 10.0;
        double bound = 5.0;
        boolean exceptionThrown = false;
        try {
            ThreadLocalRandom.current().nextDouble(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid range") ;
        }
    }

    private static void testEdgeCases() {
        double origin = 0.0;
        double bound = 0.0001;
        double result = ThreadLocalRandom.current().nextDouble(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Edge case result " + result + " not in range [" + origin + ", " + bound + ")");
        }
        origin = -5.0;
        bound = -1.0;
        result = ThreadLocalRandom.current().nextDouble(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Negative range result " + result + " not in range [" + origin + ", " + bound + ")");
        }
    }
}
