/* @test    @summary Test for Math.addExact to verify correct behavior and exception handling    @run main MathWitupTest */

import java.lang.ArithmeticException;
import java.lang.Math;

public class MathWitupTest {

    public static void main(String[] args) {
        testAddExactNormalValues();
        testAddExactOverflow();
    }

    private static void testAddExactNormalValues() {
        long x = 100L;
        long y = 200L;
        long expected = 300L;
        long result = Math.addExact(x, y);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testAddExactOverflow() {
        long max = Long.MAX_VALUE;
        long min = Long.MIN_VALUE;
        // Test overflow case: adding 1 to Long.MAX_VALUE should throw ArithmeticException
        try {
            Math.addExact(max, 1L);
            throw new AssertionError("Expected ArithmeticException for overflow not thrown");
        } catch (ArithmeticException e) {
            // Expected exception
        }
        // Test overflow case: adding -1 to Long.MIN_VALUE should throw ArithmeticException
        try {
            Math.addExact(min, -1L);
            throw new AssertionError("Expected ArithmeticException for overflow not thrown");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
