/* @test    @summary Test subtractExact for overflow and normal cases    @run main MathWitupTest_P6bba5e3ebd0e */

import java.lang.ArithmeticException;
import java.lang.Math;

public class MathWitupTest_P6bba5e3ebd0e {

    public static void main(String[] args) {
        testSubtractExactNormal();
        testSubtractExactOverflow();
    }

    private static void testSubtractExactNormal() {
        long x = 100L;
        long y = 50L;
        long expected = 50L;
        long result = Math.subtractExact(x, y);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testSubtractExactOverflow() {
        long x = Long.MAX_VALUE;
        long y = -1L;
        try {
            Math.subtractExact(x, y);
            throw new AssertionError("Expected ArithmeticException for overflow")
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }

}
