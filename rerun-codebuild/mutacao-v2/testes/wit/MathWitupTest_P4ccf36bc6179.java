/*
 * @test
 * @summary Test java.lang.Math.addExact(long, long) for overflow and normal cases
 * @run main MathWitupTest_P4ccf36bc6179
 */

public class MathWitupTest_P4ccf36bc6179 {
    public static void main(String[] args) {
        testAddExactNoOverflow();
        testAddExactOverflow();
        System.out.println("MathWitupTest_P4ccf36bc6179 passed.");
    }

    private static void testAddExactNoOverflow() {
        // Normal addition without overflow
        long a = 1000L;
        long b = 2000L;
        long result = java.lang.Math.addExact(a, b);
        if (result != (a + b)) {
            throw new AssertionError("addExact failed for normal addition");
        }

        // Test with zero
        if (java.lang.Math.addExact(0L, 0L) != 0L) {
            throw new AssertionError("addExact failed for zero addition");
        }

        // Test with negative and positive that do not overflow
        long c = -500L;
        long d = 400L;
        long res2 = java.lang.Math.addExact(c, d);
        if (res2 != (c + d)) {
            throw new AssertionError("addExact failed for mixed sign addition without overflow");
        }
    }

    private static void testAddExactOverflow() {
        // Test overflow: large positive + large positive -> overflow
        long max = Long.MAX_VALUE;
        long one = 1L;
        try {
            java.lang.Math.addExact(max, one);
            throw new AssertionError("addExact did not throw ArithmeticException on positive overflow");
        } catch (ArithmeticException e) {
            // expected
        }

        // Test overflow: large negative + large negative -> overflow
        long min = Long.MIN_VALUE;
        long minusOne = -1L;
        try {
            java.lang.Math.addExact(min, minusOne);
            throw new AssertionError("addExact did not throw ArithmeticException on negative overflow");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
