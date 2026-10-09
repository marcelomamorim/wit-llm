/*
 * @test
 * @summary Test java.util.concurrent.ThreadLocalRandom.nextLong(long origin, long bound) method
 * @run main ThreadLocalRandomWitupTest_Pd06ee2c0ded7
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_Pd06ee2c0ded7 {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
        testSingleValueRange();
    }

    private static void testValidRange() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        long origin = 10L;
        long bound = 20L;
        for (int i = 0; i < 1000; i++) {
            long val = rnd.nextLong(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }
    }

    private static void testInvalidRange() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        long origin = 5L;
        long bound = 5L;
        try {
            rnd.nextLong(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin >= bound");
        } catch (IllegalArgumentException e) {
            // expected
        }

        origin = 10L;
        bound = 5L;
        try {
            rnd.nextLong(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSingleValueRange() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        long origin = Long.MIN_VALUE;
        long bound = Long.MIN_VALUE + 1;
        for (int i = 0; i < 100; i++) {
            long val = rnd.nextLong(origin, bound);
            if (val != origin) {
                throw new AssertionError("Value " + val + " not equal to origin " + origin + " in single-value range");
            }
        }
    }
}
