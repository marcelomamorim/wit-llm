/* @test    @summary Tests for Math.subtractExact method to verify overflow detection and correct subtraction behavior    @run main MathWitupTest_P6bba5e3ebd0e */

import java.lang.ArithmeticException;
import java.lang.Math;

public class MathWitupTest_P6bba5e3ebd0e {

    public static void main(String[] args) {
        testSubtractExactNormalValues();
        testSubtractExactOverflow();
        testSubtractExactNegativeValues();
        testSubtractExactMixedValues();
    }

    static void testSubtractExactNormalValues() {
        long x = 100L;
        long y = 50L;
        long result = Math.subtractExact(x, y);
        if (result != 50L) {
            throw new AssertionError("Expected 50, but got " + result);
        }
    }

    static void testSubtractExactOverflow() {
        long max = Long.MAX_VALUE;
        long min = Long.MIN_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.subtractExact(max, -1L);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for overflow not thrown.");
        }
        exceptionThrown = false;
        try {
            Math.subtractExact(min, 1L);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for overflow not thrown.");
        }
    }

    static void testSubtractExactNegativeValues() {
        long x = -100L;
        long y = -50L;
        long result = Math.subtractExact(x, y);
        if (result != -50L) {
            throw new AssertionError("Expected -50, but got " + result);
        }
    }

    static void testSubtractExactMixedValues() {
        long x = 100L;
        long y = -50L;
        long result = Math.subtractExact(x, y);
        if (result != 150L) {
            throw new AssertionError("Expected 150, but got " + result);
        }
        x = -100L;
        y = 50L;
        result = Math.subtractExact(x, y);
        if (result != -150L) {
            throw new AssertionError("Expected -150, but got " + result);
        }
    }
}
