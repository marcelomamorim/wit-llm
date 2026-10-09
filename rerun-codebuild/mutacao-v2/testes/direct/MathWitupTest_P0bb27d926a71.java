/*
 * @test
 * @summary Test Math.subtractExact(long, long) for correctness and overflow
 * @run main MathWitupTest_P0bb27d926a71
 */

public class MathWitupTest_P0bb27d926a71 {
    public static void main(String[] args) {
        testSubtractExactNormal();
        testSubtractExactOverflow();
    }

    private static void testSubtractExactNormal() {
        // Test normal subtraction without overflow
        long result1 = Math.subtractExact(10L, 5L);
        if (result1 != 5L) {
            throw new Error("Failed: 10 - 5 should be 5 but was " + result1);
        }

        long result2 = Math.subtractExact(-10L, -5L);
        if (result2 != -5L) {
            throw new Error("Failed: -10 - (-5) should be -5 but was " + result2);
        }

        long result3 = Math.subtractExact(Long.MAX_VALUE, 0L);
        if (result3 != Long.MAX_VALUE) {
            throw new Error("Failed: MAX_VALUE - 0 should be MAX_VALUE but was " + result3);
        }

        long result4 = Math.subtractExact(Long.MIN_VALUE, 0L);
        if (result4 != Long.MIN_VALUE) {
            throw new Error("Failed: MIN_VALUE - 0 should be MIN_VALUE but was " + result4);
        }
    }

    private static void testSubtractExactOverflow() {
        // Test overflow cases
        try {
            Math.subtractExact(Long.MIN_VALUE, 1L);
            throw new Error("Failed: Expected ArithmeticException for Long.MIN_VALUE - 1");
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new Error("Failed: Exception message mismatch: " + e.getMessage());
            }
        }

        try {
            Math.subtractExact(Long.MAX_VALUE, -1L);
            // This does not overflow: MAX_VALUE - (-1) == MAX_VALUE + 1 == overflow?
            // Actually MAX_VALUE + 1 overflows long, so subtractExact should throw
            throw new Error("Failed: Expected ArithmeticException for Long.MAX_VALUE - (-1)");
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new Error("Failed: Exception message mismatch: " + e.getMessage());
            }
        }

        try {
            Math.subtractExact(-1L, Long.MAX_VALUE);
            // -1 - MAX_VALUE = negative number, no overflow expected
        } catch (ArithmeticException e) {
            throw new Error("Failed: Unexpected ArithmeticException for -1 - MAX_VALUE");
        }

        try {
            Math.subtractExact(Long.MIN_VALUE, -1L);
            // MIN_VALUE - (-1) = MIN_VALUE + 1 no overflow
        } catch (ArithmeticException e) {
            throw new Error("Failed: Unexpected ArithmeticException for MIN_VALUE - (-1)");
        }
    }
}
