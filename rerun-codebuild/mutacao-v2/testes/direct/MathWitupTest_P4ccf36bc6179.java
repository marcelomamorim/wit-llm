/*
 * @test
 * @summary Test java.lang.Math.addExact(long x, long y) for correctness and overflow
 * @run main MathWitupTest_P4ccf36bc6179
 */

public class MathWitupTest_P4ccf36bc6179 {
    public static void main(String[] args) {
        testAddExactNormal();
        testAddExactOverflowPositive();
        testAddExactOverflowNegative();
    }

    static void testAddExactNormal() {
        // Test normal addition without overflow
        long a = 100L;
        long b = 200L;
        long result = java.lang.Math.addExact(a, b);
        if (result != 300L) {
            throw new AssertionError("Expected 300 but got " + result);
        }

        // Test addition with negative numbers without overflow
        a = -100L;
        b = -200L;
        result = java.lang.Math.addExact(a, b);
        if (result != -300L) {
            throw new AssertionError("Expected -300 but got " + result);
        }

        // Test addition with mixed sign numbers without overflow
        a = 100L;
        b = -50L;
        result = java.lang.Math.addExact(a, b);
        if (result != 50L) {
            throw new AssertionError("Expected 50 but got " + result);
        }
    }

    static void testAddExactOverflowPositive() {
        // Test positive overflow: Long.MAX_VALUE + 1 should throw ArithmeticException
        long a = Long.MAX_VALUE;
        long b = 1L;
        try {
            java.lang.Math.addExact(a, b);
            throw new AssertionError("Expected ArithmeticException for positive overflow");
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testAddExactOverflowNegative() {
        // Test negative overflow: Long.MIN_VALUE + (-1) should throw ArithmeticException
        long a = Long.MIN_VALUE;
        long b = -1L;
        try {
            java.lang.Math.addExact(a, b);
            throw new AssertionError("Expected ArithmeticException for negative overflow");
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
