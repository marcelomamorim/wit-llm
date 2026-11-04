/* @test    @summary Test for Math.negateExact(int a) to verify behavior, including exception case    @run main MathWitupTest */

import java.lang.Math;

public class MathWitupTest {

    public static void main(String[] args) {
        testNegateExactNormal();
        testNegateExactOverflow();
    }

    private static void testNegateExactNormal() {
        int input = 12345;
        int expected = -12345;
        int result = Math.negateExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testNegateExactOverflow() {
        int input = Integer.MIN_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.negateExact(input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for input Integer.MIN_VALUE");
        }
    }
}
