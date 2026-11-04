/* @test    @summary Test SplittableRandom.nextInt(int) for various bounds    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNextIntWithPositiveBound();
        testNextIntWithPowerOfTwoBound();
        testNextIntWithInvalidBound();
    }

    static void testNextIntWithPositiveBound() {
        SplittableRandom sr = new SplittableRandom(12345L);
        int bound = 10;
        for (int i = 0; i < 100; i++) {
            int result = sr.nextInt(bound);
            if (result < 0 || result >= bound) {
                throw new AssertionError("Result out of bounds: " + result);
            }
        }
    }

    static void testNextIntWithPowerOfTwoBound() {
        SplittableRandom sr = new SplittableRandom(54321L);
        int bound = 8; // power of two
        for (int i = 0; i < 100; i++) {
            int result = sr.nextInt(bound);
            if (result < 0 || result >= bound) {
                throw new AssertionError("Result out of bounds: " + result);
            }
        }
    }

    static void testNextIntWithInvalidBound() {
        SplittableRandom sr = new SplittableRandom(67890L);
        boolean exceptionThrown = false;
        try {
            sr.nextInt(0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        }
        exceptionThrown = false;
        try {
            sr.nextInt(-5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        }
    }
}
