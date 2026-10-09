/* @test    @summary Test for Math.incrementExact method to verify exception on overflow    @run main MathWitupTest_P734804c51965 */

import java.lang.Math;

public class MathWitupTest_P734804c51965 {

    public static void main(String[] args) {
        testIncrementExactNormal();
        testIncrementExactOverflow();
    }

    static void testIncrementExactNormal() {
        long input = 42L;
        long expected = input + 1L;
        long result = Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("Failed: expected " + expected + " but got " + result);
        }
    }

    static void testIncrementExactOverflow() {
        long input = Long.MAX_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.incrementExact(input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Failed: expected ArithmeticException for input " + input);
        }
    }
}
