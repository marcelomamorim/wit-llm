/* @test    @summary Test for Math.decrementExact method to verify correct behavior and exception handling    @run main MathWitupTest_Pf108e32cd491 */

import java.lang.Math;

public class MathWitupTest_Pf108e32cd491 {

    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactOverflow();
    }

    private static void testDecrementExactNormal() {
        long input = 100L;
        long expected = 99L;
        long result = Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testDecrementExactOverflow() {
        long input = Long.MIN_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.decrementExact(input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for input: " + input);
        }
    }
}
