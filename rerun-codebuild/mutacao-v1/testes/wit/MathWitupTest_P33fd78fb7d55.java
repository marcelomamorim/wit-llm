/* @test    @summary Test for Math.decrementExact(long a) to verify behavior and exception handling    @run main MathWitupTest_P33fd78fb7d55 */

import java.lang.Math;

public class MathWitupTest_P33fd78fb7d55 {
    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactOverflow();
    }

    static void testDecrementExactNormal() {
        long input = 100L;
        long expected = 99L;
        long result = Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("Failed: decrementExact(" + input + ") expected " + expected + " but got " + result);
        }
    }

    static void testDecrementExactOverflow() {
        long input = Long.MIN_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.decrementExact(input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
            if (!e.getMessage().contains("long overflow")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException was not thrown for input: " + input);
        }
    }
}
