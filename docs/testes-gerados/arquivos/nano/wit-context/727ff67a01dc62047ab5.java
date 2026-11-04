/*
 * @test
 * @summary Test for ThreadLocalRandom.doubles(double, double)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testDoublesValidRange();
        testDoublesInvalidRange();
    }

    private static void testDoublesValidRange() {
        double origin = 1.0;
        double bound = 2.0;
        DoubleStream stream = ThreadLocalRandom.current().doubles(origin, bound);
        boolean valid = stream.allMatch(d -> d >= origin && d < bound);
        if (!valid) {
            throw new AssertionError("Doubles outside valid range") ;
        }
    }

    private static void testDoublesInvalidRange() {
        double origin = 5.0;
        double bound = 3.0;
        try {
            ThreadLocalRandom.current().doubles(origin, bound).count();
            throw new AssertionError("Expected IllegalArgumentException not thrown") ;
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
