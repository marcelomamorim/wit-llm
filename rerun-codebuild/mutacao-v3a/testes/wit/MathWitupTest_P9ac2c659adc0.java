/* @test    @summary Test for Math.addExact handling overflow scenarios    @run main MathWitupTest_P9ac2c659adc0 */

import java.lang.ArithmeticException;
import java.lang.Math;

public class MathWitupTest_P9ac2c659adc0 {

    public static void main(String[] args) {
        testAddExactNoOverflow();
        testAddExactPositiveOverflow();
        testAddExactNegativeOverflow();
    }

    static void testAddExactNoOverflow() {
        long x = 123456789L;
        long y = 987654321L;
        long result = Math.addExact(x, y);
        long expected = x + y;
        if (result != expected) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    static void testAddExactPositiveOverflow() {
        long maxLong = Long.MAX_VALUE;
        long x = maxLong - 1;
        long y = 2;
        try {
            Math.addExact(x, y);
            throw new AssertionError("Expected ArithmeticException not thrown");
        } catch (ArithmeticException e) {
            // Expected exception
            if (!e.getMessage().contains("long overflow")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testAddExactNegativeOverflow() {
        long minLong = Long.MIN_VALUE;
        long x = minLong + 1;
        long y = -2;
        try {
            Math.addExact(x, y);
            throw new AssertionError("Expected ArithmeticException not thrown");
        } catch (ArithmeticException e) {
            // Expected exception
            if (!e.getMessage().contains("long overflow")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
