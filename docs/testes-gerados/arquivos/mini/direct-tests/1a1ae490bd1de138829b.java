/*
 * @test
 * @summary Test java.util.SplittableRandom.nextInt(int bound) for correctness and exceptions
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testNextIntWithValidBounds();
        testNextIntWithPowerOfTwoBounds();
        testNextIntWithInvalidBounds();
    }

    private static void testNextIntWithValidBounds() {
        SplittableRandom sr = new SplittableRandom(12345L);
        int bound = 10;
        for (int i = 0; i < 1000; i++) {
            int val = sr.nextInt(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range: " + val);
            }
        }
    }

    private static void testNextIntWithPowerOfTwoBounds() {
        SplittableRandom sr = new SplittableRandom(67890L);
        int bound = 16; // power of two
        for (int i = 0; i < 1000; i++) {
            int val = sr.nextInt(bound);
            if (val < 0 || val >= bound) {
                throw new AssertionError("Value out of range for power of two bound: " + val);
            }
        }
    }

    private static void testNextIntWithInvalidBounds() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.nextInt(0);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            sr.nextInt(-1);
            throw new AssertionError("Expected IllegalArgumentException for bound=-1");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
