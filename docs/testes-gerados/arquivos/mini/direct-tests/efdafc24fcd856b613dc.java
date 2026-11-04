/*
 * @test
 * @summary Test java.util.Random.nextInt(int bound) for correctness and exceptions
 * @run main java.util.RandomWitupTest
 */

import java.util.Random;

public class RandomWitupTest {
    public static void main(String[] args) {
        testNextIntWithPowerOfTwoBound();
        testNextIntWithNonPowerOfTwoBound();
        testNextIntWithBoundOne();
        testNextIntWithBoundMaxInt();
        testNextIntWithIllegalBounds();
    }

    private static void testNextIntWithPowerOfTwoBound() {
        Random rnd = new Random(12345L);
        int bound = 16; // power of two
        for (int i = 0; i < 1000; i++) {
            int val = rnd.nextInt(bound);
            if (val < 0 || val >= bound) {
                throw new RuntimeException("Value out of range for power-of-two bound: " + val);
            }
        }
    }

    private static void testNextIntWithNonPowerOfTwoBound() {
        Random rnd = new Random(12345L);
        int bound = 15; // not a power of two
        for (int i = 0; i < 1000; i++) {
            int val = rnd.nextInt(bound);
            if (val < 0 || val >= bound) {
                throw new RuntimeException("Value out of range for non-power-of-two bound: " + val);
            }
        }
    }

    private static void testNextIntWithBoundOne() {
        Random rnd = new Random(12345L);
        int bound = 1;
        for (int i = 0; i < 100; i++) {
            int val = rnd.nextInt(bound);
            if (val != 0) {
                throw new RuntimeException("Value must be zero when bound is 1, got: " + val);
            }
        }
    }

    private static void testNextIntWithBoundMaxInt() {
        Random rnd = new Random(12345L);
        int bound = Integer.MAX_VALUE;
        for (int i = 0; i < 1000; i++) {
            int val = rnd.nextInt(bound);
            if (val < 0 || val >= bound) {
                throw new RuntimeException("Value out of range for max int bound: " + val);
            }
        }
    }

    private static void testNextIntWithIllegalBounds() {
        Random rnd = new Random();
        try {
            rnd.nextInt(0);
            throw new RuntimeException("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            rnd.nextInt(-1);
            throw new RuntimeException("Expected IllegalArgumentException for bound=-1");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
