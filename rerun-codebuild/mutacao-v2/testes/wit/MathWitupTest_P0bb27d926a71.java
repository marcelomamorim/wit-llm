/*
 * @test
 * @summary Test Math.subtractExact(long, long) for overflow and normal cases
 * @run main MathWitupTest_P0bb27d926a71
 */

public class MathWitupTest_P0bb27d926a71 {
    public static void main(String[] args) {
        testSubtractExactNoOverflow();
        testSubtractExactOverflow();
        System.out.println("MathWitupTest_P0bb27d926a71 passed.");
    }

    private static void testSubtractExactNoOverflow() {
        // Normal cases without overflow
        long result1 = java.lang.Math.subtractExact(10L, 5L);
        if (result1 != 5L) {
            throw new AssertionError("Expected 5 but got " + result1);
        }

        long result2 = java.lang.Math.subtractExact(-10L, -5L);
        if (result2 != -5L) {
            throw new AssertionError("Expected -5 but got " + result2);
        }

        long result3 = java.lang.Math.subtractExact(Long.MAX_VALUE, 0L);
        if (result3 != Long.MAX_VALUE) {
            throw new AssertionError("Expected Long.MAX_VALUE but got " + result3);
        }

        long result4 = java.lang.Math.subtractExact(Long.MIN_VALUE + 1, 1L);
        if (result4 != Long.MIN_VALUE) {
            throw new AssertionError("Expected Long.MIN_VALUE but got " + result4);
        }
    }

    private static void testSubtractExactOverflow() {
        // Overflow cases
        // According to the method, overflow occurs if ((x ^ y) & (x ^ r)) < 0
        // Test case: x = Long.MIN_VALUE, y = 1 -> x - y = Long.MIN_VALUE - 1 overflows
        try {
            java.lang.Math.subtractExact(Long.MIN_VALUE, 1L);
            throw new AssertionError("Expected ArithmeticException for overflow not thrown");
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }

        // Another overflow case: x = Long.MAX_VALUE, y = -1 -> x - y = Long.MAX_VALUE - (-1) = Long.MAX_VALUE + 1 overflows
        try {
            java.lang.Math.subtractExact(Long.MAX_VALUE, -1L);
            throw new AssertionError("Expected ArithmeticException for overflow not thrown");
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
