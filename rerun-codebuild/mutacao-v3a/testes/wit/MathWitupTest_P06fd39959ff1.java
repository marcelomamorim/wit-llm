/* @test    @summary Tests for java.lang.Math.incrementExact(long a)    @run main MathWitupTest_P06fd39959ff1 */

import java.lang.Math;

public class MathWitupTest_P06fd39959ff1 {

    public static void main(String[] args) {
        testIncrementExactNormalValue();
        testIncrementExactOverflow();
    }

    private static void testIncrementExactNormalValue() {
        long value = 42L;
        long result = Math.incrementExact(value);
        if (result != value + 1L) {
            throw new AssertionError("Expected " + (value + 1L) + ", but got " + result);
        }
    }

    private static void testIncrementExactOverflow() {
        long maxLong = Long.MAX_VALUE;
        try {
            Math.incrementExact(maxLong);
            throw new AssertionError("Expected ArithmeticException was not thrown");
        } catch (ArithmeticException e) {
            // Expected exception
            if (!"long overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
