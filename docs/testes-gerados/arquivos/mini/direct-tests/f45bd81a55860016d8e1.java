/*
 * @test
 * @summary Test java.util.SplittableRandom.nextLong(long bound) method
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testNextLongWithValidBounds();
        testNextLongWithPowerOfTwoBounds();
        testNextLongWithInvalidBounds();
    }

    private static void testNextLongWithValidBounds() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long bound = 100L;
        for (int i = 0; i < 1000; i++) {
            long val = sr.nextLong(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range: " + val + " for bound " + bound);
            }
        }
    }

    private static void testNextLongWithPowerOfTwoBounds() {
        SplittableRandom sr = new SplittableRandom(67890L);
        long bound = 64L; // power of two
        for (int i = 0; i < 1000; i++) {
            long val = sr.nextLong(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range: " + val + " for power-of-two bound " + bound);
            }
        }
    }

    private static void testNextLongWithInvalidBounds() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.nextLong(0L);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            sr.nextLong(-1L);
            throw new AssertionError("Expected IllegalArgumentException for bound=-1");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
