/* @test    @summary Test incrementExact method in Math class    @run main MathWitupTest_P06fd39959ff1 */

import java.lang.Math;

public class MathWitupTest_P06fd39959ff1 {

    public static void main(String[] args) {
        testIncrementExactNormalValue();
        testIncrementExactMaxValue();
    }

    static void testIncrementExactNormalValue() {
        long input = 42L;
        long expected = 43L;
        long result = Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testIncrementExactMaxValue() {
        long input = Long.MAX_VALUE;
        try {
            Math.incrementExact(input);
            throw new AssertionError("Expected ArithmeticException not thrown");
        } catch (ArithmeticException e) {
            // Expected exception
            if (!"long overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
