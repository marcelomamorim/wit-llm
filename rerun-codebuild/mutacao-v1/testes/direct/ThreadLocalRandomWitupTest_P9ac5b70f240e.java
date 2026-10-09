/* @test    @summary Test for ThreadLocalRandom.nextDouble(double, double) method    @run main ThreadLocalRandomWitupTest_P9ac5b70f240e */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P9ac5b70f240e {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
        testEdgeCases();
    }

    static void testValidRange() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        double origin = 10.0;
        double bound = 20.0;
        for (int i = 0; i < 100; i++) {
            double result = r.nextDouble(origin, bound);
            if (result < origin || result >= bound) {
                throw new AssertionError("Result out of range: " + result);
            }
        }
    }

    static void testInvalidRange() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        double origin = 20.0;
        double bound = 10.0;
        boolean exceptionThrown = false;
        try {
            r.nextDouble(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid range") ;
        }
    }

    static void testEdgeCases() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        double origin = 0.0;
        double bound = 0.0;
        boolean exceptionThrown = false;
        try {
            r.nextDouble(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for zero range") ;
        }
        origin = Double.NEGATIVE_INFINITY;
        bound = Double.POSITIVE_INFINITY;
        double result = r.nextDouble(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result out of range for infinite bounds: " + result);
        }
    }
}
