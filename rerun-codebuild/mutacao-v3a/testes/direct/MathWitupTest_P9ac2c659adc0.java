/* @test    @summary Test for Math.addExact method to verify overflow detection and correct addition behavior    @run main MathWitupTest_P9ac2c659adc0 */

import java.lang.Math;

public class MathWitupTest_P9ac2c659adc0 {

    public static void main(String[] args) {
        testAddExactNormalValues();
        testAddExactOverflow();
    }

    private static void testAddExactNormalValues() {
        long a = 100L;
        long b = 200L;
        long result = Math.addExact(a, b);
        if (result != 300L) {
            throw new AssertionError("Expected 300, but got " + result);
        }
        long c = -50L;
        long d = 25L;
        result = Math.addExact(c, d);
        if (result != -25L) {
            throw new AssertionError("Expected -25, but got " + result);
        }
    }

    private static void testAddExactOverflow() {
        long max = Long.MAX_VALUE;
        long min = Long.MIN_VALUE;
        boolean overflowCaught = false;
        try {
            Math.addExact(max, 1L);
        } catch (ArithmeticException e) {
            overflowCaught = true;
        }
        if (!overflowCaught) {
            throw new AssertionError("Overflow not detected for Long.MAX_VALUE + 1");
        }
        overflowCaught = false;
        try {
            Math.addExact(min, -1L);
        } catch (ArithmeticException e) {
            overflowCaught = true;
        }
        if (!overflowCaught) {
            throw new AssertionError("Overflow not detected for Long.MIN_VALUE + (-1)");
        }
    }
}
