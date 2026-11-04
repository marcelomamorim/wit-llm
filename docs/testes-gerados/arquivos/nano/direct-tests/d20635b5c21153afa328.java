/* @test    @summary Test subtractExact method for overflow and normal cases    @run main MathWitupTest */

import java.lang.Math;

public class MathWitupTest {
    public static void main(String[] args) {
        testSubtractExactNormal();
        testSubtractExactOverflow();
    }

    static void testSubtractExactNormal() {
        int x = 10;
        int y = 5;
        int result = Math.subtractExact(x, y);
        if (result != 5) {
            throw new AssertionError("Expected 5, got " + result);
        }
    }

    static void testSubtractExactOverflow() {
        int x = Integer.MAX_VALUE;
        int y = -1;
        try {
            Math.subtractExact(x, y);
            throw new AssertionError("Expected ArithmeticException for overflow");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
