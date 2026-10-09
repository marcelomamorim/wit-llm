/*
 * @test    @summary Test for ThreadLocalRandom.nextDouble(double, double)
 * @run main ThreadLocalRandomWitupTest_P973c2eb3d1d2
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P973c2eb3d1d2 {

    public static void main(String[] args) {
        testNextDoubleValidRange();
        testNextDoubleInvalidRange();
    }

    public static void testNextDoubleValidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double origin = 0.0;
        double bound = 10.0;
        double value = rand.nextDouble(origin, bound);
        if (value < origin || value >= bound) {
            throw new AssertionError("Value out of range: " + value);
        }
    }

    public static void testNextDoubleInvalidRange() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double origin = 5.0;
        double bound = 5.0;
        try {
            rand.nextDouble(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }

        origin = 10.0;
        bound = 5.0;
        try {
            rand.nextDouble(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
