/* @test    @summary Test for Math.incrementExact to verify normal and overflow behavior    @run main MathWitupTest */

import java.lang.Math;

public class MathWitupTest {

    public static void main(String[] args) {
        testIncrementExactNormal();
        testIncrementExactOverflow();
    }

    private static void testIncrementExactNormal() {
        int input = 42;
        int expected = input + 1;
        int result = Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testIncrementExactOverflow() {
        int input = Integer.MAX_VALUE;
        try {
            Math.incrementExact(input);
            throw new AssertionError("Expected ArithmeticException for input: " + input);
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
