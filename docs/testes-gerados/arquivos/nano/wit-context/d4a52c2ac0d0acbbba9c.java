/*
 * @test
 * @summary Tests for java.util.concurrent.ThreadLocalRandom.nextLong(long)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNextLongWithPositiveBound();
        testNextLongWithZeroBound();
        testNextLongWithNegativeBound();
        testNextLongWithPowerOfTwoBound();
        testNextLongWithNonPowerOfTwoBound();
    }

    static void testNextLongWithPositiveBound() {
        long bound = 100L;
        long result = ThreadLocalRandom.current().nextLong(bound);
        if (result < 0 || result >= bound) {
            throw new AssertionError("Result out of bounds: " + result);
        }
    }

    static void testNextLongWithZeroBound() {
        boolean exceptionThrown = false;
        try {
            ThreadLocalRandom.current().nextLong(0L);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for zero bound");
        }
    }

    static void testNextLongWithNegativeBound() {
        boolean exceptionThrown = false;
        try {
            ThreadLocalRandom.current().nextLong(-10L);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        }
    }

    static void testNextLongWithPowerOfTwoBound() {
        long bound = 16L; // power of two
        long result = ThreadLocalRandom.current().nextLong(bound);
        if (result < 0 || result >= bound) {
            throw new AssertionError("Result out of bounds for power of two: " + result);
        }
    }

    static void testNextLongWithNonPowerOfTwoBound() {
        long bound = 20L; // non power of two
        long result = ThreadLocalRandom.current().nextLong(bound);
        if (result < 0 || result >= bound) {
            throw new AssertionError("Result out of bounds for non power of two: " + result);
        }
    }
}
