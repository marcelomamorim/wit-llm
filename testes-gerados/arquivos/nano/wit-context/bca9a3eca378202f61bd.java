/* @test    @summary Test for Math.incrementExact method to verify normal and overflow behavior    @run main MathWitupTest */

import java.lang.Math;

public class MathWitupTest {

    public static void main(String[] args) {
        testIncrementExactNormal();
        testIncrementExactOverflow();
    }

    private static void testIncrementExactNormal() {
        long input = 42L;
        long expected = input + 1L;
        long result = Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testIncrementExactOverflow() {
        long input = Long.MAX_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.incrementExact(input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for input: " + input);
        }
    }
}
