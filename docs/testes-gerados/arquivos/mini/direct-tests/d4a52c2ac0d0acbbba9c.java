/*
 * @test
 * @summary Test java.util.concurrent.ThreadLocalRandom.nextLong(long bound) behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNextLongWithValidBounds();
        testNextLongWithPowerOfTwoBounds();
        testNextLongWithInvalidBounds();
    }

    private static void testNextLongWithValidBounds() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        long bound = 100L;
        for (int i = 0; i < 1000; i++) {
            long val = rnd.nextLong(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range: " + val + " for bound " + bound);
            }
        }
    }

    private static void testNextLongWithPowerOfTwoBounds() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        long bound = 64L; // power of two
        for (int i = 0; i < 1000; i++) {
            long val = rnd.nextLong(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range: " + val + " for power-of-two bound " + bound);
            }
        }
    }

    private static void testNextLongWithInvalidBounds() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        try {
            rnd.nextLong(0L);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException expected) {
        }
        try {
            rnd.nextLong(-1L);
            throw new AssertionError("Expected IllegalArgumentException for bound=-1");
        } catch (IllegalArgumentException expected) {
        }
    }
}
