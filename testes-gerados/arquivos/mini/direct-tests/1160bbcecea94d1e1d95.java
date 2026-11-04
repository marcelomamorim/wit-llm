/*
 * @test
 * @summary Basic tests for ThreadLocalRandom.nextInt(int bound) method
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNextIntWithValidBounds();
        testNextIntWithPowerOfTwoBounds();
        testNextIntWithInvalidBounds();
    }

    private static void testNextIntWithValidBounds() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int bound = 10;
        for (int i = 0; i < 1000; i++) {
            int val = rnd.nextInt(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range: " + val);
            }
        }
    }

    private static void testNextIntWithPowerOfTwoBounds() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int bound = 16; // power of two
        for (int i = 0; i < 1000; i++) {
            int val = rnd.nextInt(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range for power of two bound: " + val);
            }
        }
    }

    private static void testNextIntWithInvalidBounds() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        try {
            rnd.nextInt(0);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            rnd.nextInt(-1);
            throw new AssertionError("Expected IllegalArgumentException for bound=-1");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
