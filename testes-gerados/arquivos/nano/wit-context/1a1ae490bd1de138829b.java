/*
 * @test
 * @summary Test for SplittableRandom.nextInt(int) method.
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNextIntWithPositiveBound();
        testNextIntWithZeroBound();
        testNextIntWithNegativeBound();
        testNextIntWithLargeBound();
    }

    private static void testNextIntWithPositiveBound() {
        SplittableRandom sr = new SplittableRandom();
        int bound = 10;
        int result = sr.nextInt(bound);
        if (result < 0 || result >= bound) {
            throw new AssertionError("Result out of bounds: " + result);
        }
    }

    private static void testNextIntWithZeroBound() {
        SplittableRandom sr = new SplittableRandom();
        boolean exceptionThrown = false;
        try {
            sr.nextInt(0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for bound=0") ;
        }
    }

    private static void testNextIntWithNegativeBound() {
        SplittableRandom sr = new SplittableRandom();
        boolean exceptionThrown = false;
        try {
            sr.nextInt(-5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative bound") ;
        }
    }

    private static void testNextIntWithLargeBound() {
        SplittableRandom sr = new SplittableRandom();
        int bound = Integer.MAX_VALUE;
        int result = sr.nextInt(bound);
        if (result < 0 || result >= bound) {
            throw new AssertionError("Result out of bounds for large bound: " + result);
        }
    }
}
