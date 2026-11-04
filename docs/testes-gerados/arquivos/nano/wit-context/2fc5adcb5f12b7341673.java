/*
 * @test
 * @summary Test for Math.addExact method to verify overflow exception behavior.
 * @run main MathWitupTest
 */

import java.lang.ArithmeticException;
import java.lang.Math;

public class MathWitupTest {

    public static void main(String[] args) {
        testAddExactNoOverflow();
        testAddExactPositiveOverflow();
        testAddExactNegativeOverflow();
    }

    private static void testAddExactNoOverflow() {
        int x = 1000;
        int y = 2000;
        int result = Math.addExact(x, y);
        if (result != (x + y)) {
            throw new AssertionError("Expected " + (x + y) + " but got " + result);
        }
    }

    private static void testAddExactPositiveOverflow() {
        int x = Integer.MAX_VALUE;
        int y = 1;
        try {
            Math.addExact(x, y);
            throw new AssertionError("Expected ArithmeticException for positive overflow");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }

    private static void testAddExactNegativeOverflow() {
        int x = Integer.MIN_VALUE;
        int y = -1;
        try {
            Math.addExact(x, y);
            throw new AssertionError("Expected ArithmeticException for negative overflow");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
