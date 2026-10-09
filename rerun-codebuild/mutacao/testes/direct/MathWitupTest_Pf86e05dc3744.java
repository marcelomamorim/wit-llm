/* @test    @summary Test for Math.negateExact(long a) method to verify correct negation and overflow handling    @run main MathWitupTest_Pf86e05dc3744 */

import java.lang.Math;

public class MathWitupTest_Pf86e05dc3744 {

    public static void main(String[] args) {
        testNegateExactPositive();
        testNegateExactNegative();
        testNegateExactOverflow();
    }

    private static void testNegateExactPositive() {
        long value = 123456789L;
        long result = Math.negateExact(value);
        if (result != -value) {
            throw new AssertionError("Failed: negateExact positive, expected " + (-value) + " but got " + result);
        }
    }

    private static void testNegateExactNegative() {
        long value = -987654321L;
        long result = Math.negateExact(value);
        if (result != -value) {
            throw new AssertionError("Failed: negateExact negative, expected " + (-value) + " but got " + result);
        }
    }

    private static void testNegateExactOverflow() {
        long value = Long.MIN_VALUE;
        try {
            Math.negateExact(value);
            throw new AssertionError("Expected ArithmeticException for Long.MIN_VALUE") ;
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
