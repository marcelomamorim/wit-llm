/* @test    @summary Test SplittableRandom.nextLong(long bound) for correct behavior and exception handling    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testValidBounds();
        testZeroBoundThrows();
        testNegativeBoundThrows();
        testPowerOfTwoBound();
        testNonPowerOfTwoBound();
    }

    static void testValidBounds() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long bound = 10L;
        for (int i = 0; i < 100; i++) {
            long result = sr.nextLong(bound);
            if (result < 0 || result >= bound) {
                throw new AssertionError("Result out of bounds: " + result);
            }
        }
    }

    static void testZeroBoundThrows() {
        SplittableRandom sr = new SplittableRandom(54321L);
        try {
            sr.nextLong(0L);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNegativeBoundThrows() {
        SplittableRandom sr = new SplittableRandom(67890L);
        try {
            sr.nextLong(-5L);
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testPowerOfTwoBound() {
        SplittableRandom sr = new SplittableRandom(13579L);
        long bound = 8L; // power of two
        for (int i = 0; i < 100; i++) {
            long result = sr.nextLong(bound);
            if (result < 0 || result >= bound) {
                throw new AssertionError("Result out of bounds: " + result);
            }
        }
    }

    static void testNonPowerOfTwoBound() {
        SplittableRandom sr = new SplittableRandom(24680L);
        long bound = 10L; // not power of two
        for (int i = 0; i < 100; i++) {
            long result = sr.nextLong(bound);
            if (result < 0 || result >= bound) {
                throw new AssertionError("Result out of bounds: " + result);
            }
        }
    }
}
