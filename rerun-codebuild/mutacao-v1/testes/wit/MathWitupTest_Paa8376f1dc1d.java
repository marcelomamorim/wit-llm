/* @test    @summary Test for Math.negateExact(long a) method to verify exception on Long.MIN_VALUE    @run main MathWitupTest_Paa8376f1dc1d */

import java.lang.Math;

public class MathWitupTest_Paa8376f1dc1d {
    public static void main(String[] args) {
        testNegateExactWithMinValue();
    }

    private static void testNegateExactWithMinValue() {
        long input = Long.MIN_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.negateExact(input);
            throw new AssertionError("Expected ArithmeticException was not thrown for input: " + input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
            if (!e.getMessage().contains("long overflow")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("ArithmeticException was not thrown for input: " + input);
        }
    }
}
