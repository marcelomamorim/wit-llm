/* @test    @summary Test for Math.addExact to verify correct addition and overflow detection    @run main MathWitupTest */

import java.lang.Math;

public class MathWitupTest {

    public static void main(String[] args) {
        testAddExactNormalValues();
        testAddExactOverflow();
    }

    static void testAddExactNormalValues() {
        int a = 1000;
        int b = 2000;
        int result = Math.addExact(a, b);
        if (result != 3000) {
            throw new AssertionError("Expected 3000, but got " + result);
        }
        int c = -1000;
        int d = -2000;
        result = Math.addExact(c, d);
        if (result != -3000) {
            throw new AssertionError("Expected -3000, but got " + result);
        }
    }

    static void testAddExactOverflow() {
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;
        // Test overflow with positive overflow
        try {
            Math.addExact(max, 1);
            throw new AssertionError("Expected ArithmeticException for overflow");
        } catch (ArithmeticException e) {
            // Expected exception
        }
        // Test overflow with negative overflow
        try {
            Math.addExact(min, -1);
            throw new AssertionError("Expected ArithmeticException for overflow");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
