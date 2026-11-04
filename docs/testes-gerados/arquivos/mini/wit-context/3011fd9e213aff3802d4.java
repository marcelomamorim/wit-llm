/*
 * @test
 * @summary Test MathContext(int) constructor for valid and invalid precision values
 * @run main MathContextWitupTest
 */

import java.math.MathContext;
import java.math.RoundingMode;

public class MathContextWitupTest {
    public static void main(String[] args) {
        testValidPrecision();
        testInvalidPrecision();
        System.out.println("MathContextWitupTest passed.");
    }

    private static void testValidPrecision() {
        // Test with zero precision (unlimited precision)
        MathContext mcZero = new MathContext(0);
        if (mcZero.getPrecision() != 0) {
            throw new AssertionError("Expected precision 0, got " + mcZero.getPrecision());
        }
        if (mcZero.getRoundingMode() != RoundingMode.HALF_UP) {
            throw new AssertionError("Expected rounding mode HALF_UP, got " + mcZero.getRoundingMode());
        }

        // Test with positive precision
        MathContext mcPositive = new MathContext(10);
        if (mcPositive.getPrecision() != 10) {
            throw new AssertionError("Expected precision 10, got " + mcPositive.getPrecision());
        }
        if (mcPositive.getRoundingMode() != RoundingMode.HALF_UP) {
            throw new AssertionError("Expected rounding mode HALF_UP, got " + mcPositive.getRoundingMode());
        }
    }

    private static void testInvalidPrecision() {
        // According to source and WIT, constructor throws IllegalArgumentException if precision < 0
        try {
            new MathContext(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative precision");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }

        try {
            new MathContext(Integer.MIN_VALUE);
            throw new AssertionError("Expected IllegalArgumentException for negative precision");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
