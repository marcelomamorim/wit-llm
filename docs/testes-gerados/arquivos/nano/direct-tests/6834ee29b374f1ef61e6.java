/* @test    @summary Test for Math.incrementExact method to verify correct increment and overflow exception handling    @run main MathWitupTest */

import java.lang.Math;

public class MathWitupTest {

    public static void main(String[] args) {
        testIncrementExactNormalValue();
        testIncrementExactMaxValue();
    }

    private static void testIncrementExactNormalValue() {
        int input = 42;
        int expected = 43;
        int result = Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testIncrementExactMaxValue() {
        int max = Integer.MAX_VALUE;
        try {
            Math.incrementExact(max);
            throw new AssertionError("Expected ArithmeticException for overflow not thrown");
        } catch (ArithmeticException e) {
            // Expected exception
            if (!"integer overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
