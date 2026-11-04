/* @test    @summary Test decrementExact method in Math class    @run main MathWitupTest */

import java.lang.Math;

public class MathWitupTest {

    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactMinValue();
    }

    static void testDecrementExactNormal() {
        int input = 10;
        int expected = 9;
        int result = Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testDecrementExactMinValue() {
        int input = Integer.MIN_VALUE;
        try {
            Math.decrementExact(input);
            throw new AssertionError("Expected ArithmeticException for Integer.MIN_VALUE");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
