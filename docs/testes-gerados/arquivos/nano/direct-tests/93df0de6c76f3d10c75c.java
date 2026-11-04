/* @test    @summary Test for ThreadLocalRandom.nextDouble(double, double) method    @run main ThreadLocalRandomWitupTest */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
        testEdgeCases();
    }

    static void testValidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double origin = 10.0;
        double bound = 20.0;
        for (int i = 0; i < 100; i++) {
            double result = rand.nextDouble(origin, bound);
            if (result < origin || result >= bound) {
                throw new AssertionError("Result out of range: " + result);
            }
        }
    }

    static void testInvalidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double origin = 20.0;
        double bound = 10.0;
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

    static void testEdgeCases() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double origin = 0.0;
        double bound = 0.0001;
        for (int i = 0; i < 50; i++) {
            double result = rand.nextDouble(origin, bound);
            if (result < origin || result >= bound) {
                throw new AssertionError("Edge case result out of range: " + result);
            }
        }
        origin = -100.0;
        bound = -50.0;
        for (int i = 0; i < 50; i++) {
            double result = rand.nextDouble(origin, bound);
            if (result < origin || result >= bound) {
                throw new AssertionError("Negative range result out of range: " + result);
            }
        }
    }
}
