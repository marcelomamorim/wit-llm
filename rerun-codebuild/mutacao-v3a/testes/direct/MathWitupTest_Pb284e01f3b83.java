/* @test    @summary Test decrementExact method for boundary and normal cases    @run main MathWitupTest_Pb284e01f3b83 */

import java.lang.Math;

public class MathWitupTest_Pb284e01f3b83 {

    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactBoundary();
    }

    private static void testDecrementExactNormal() {
        long input = 100L;
        long expected = 99L;
        long result = Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("Failed for input: " + input + ", expected: " + expected + ", but got: " + result);
        }
    }

    private static void testDecrementExactBoundary() {
        long input = Long.MIN_VALUE;
        try {
            Math.decrementExact(input);
            throw new AssertionError("Expected ArithmeticException for input: " + input);
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
