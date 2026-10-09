/* @test    @summary Test for Math.decrementExact(long) method to verify correct behavior and exception handling.    @run main MathWitupTest_Pb284e01f3b83 */

import java.lang.Math;

public class MathWitupTest_Pb284e01f3b83 {

    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactOverflow();
    }

    private static void testDecrementExactNormal() {
        long input = 10L;
        long expected = 9L;
        long result = Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testDecrementExactOverflow() {
        long input = Long.MIN_VALUE;
        boolean threwException = false;
        try {
            Math.decrementExact(input);
        } catch (ArithmeticException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected ArithmeticException for input: " + input);
        }
    }
}
