/* @test    @summary Test for Math.decrementExact(int a) method to verify correct behavior and exception handling.    @run main MathWitupTest */

import java.lang.Math;

public class MathWitupTest {

    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactOverflow();
    }

    private static void testDecrementExactNormal() {
        int input = 10;
        int expected = 9;
        int result = Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testDecrementExactOverflow() {
        int input = Integer.MIN_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.decrementExact(input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
            if (!"integer overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException was not thrown for input: " + input);
        }
    }
}
