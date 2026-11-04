/* @test    @summary Test decrementExact method of Math class    @run main MathWitupTest */

import java.lang.Math;

public class MathWitupTest {

    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactMinValue();
    }

    static void testDecrementExactNormal() {
        long input = 10L;
        long expected = 9L;
        long result = Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testDecrementExactMinValue() {
        long input = Long.MIN_VALUE;
        try {
            Math.decrementExact(input);
            throw new AssertionError("Expected ArithmeticException for Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
