/* @test    @summary Test for Math.addExact to verify correct addition and overflow detection    @run main MathWitupTest */

import java.lang.Math;

public class MathWitupTest {

    public static void main(String[] args) {
        testAddExactNormalCase();
        testAddExactOverflow();
    }

    static void testAddExactNormalCase() {
        long a = 123456789L;
        long b = 987654321L;
        long result = Math.addExact(a, b);
        long expected = a + b;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testAddExactOverflow() {
        long max = Long.MAX_VALUE;
        long min = Long.MIN_VALUE;
        try {
            Math.addExact(max, 1L);
            throw new AssertionError("Expected ArithmeticException for overflow not thrown");
        } catch (ArithmeticException e) {
            // expected
        }
        try {
            Math.addExact(min, -1L);
            throw new AssertionError("Expected ArithmeticException for overflow not thrown");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
