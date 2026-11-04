/*
 * @test
 * @summary Test MathContext(int) constructor with various precisions
 * @run main MathContextWitupTest
 */

import java.math.MathContext;
import java.math.RoundingMode;

public class MathContextWitupTest {
    public static void main(String[] args) {
        testDefaultRoundingMode();
        testZeroPrecision();
        testPositivePrecision();
        testLargePrecision();
    }

    private static void testDefaultRoundingMode() {
        MathContext mc = new MathContext(5);
        if (mc.getPrecision() != 5) {
            throw new RuntimeException("Expected precision 5 but got " + mc.getPrecision());
        }
        if (mc.getRoundingMode() != RoundingMode.HALF_UP) {
            throw new RuntimeException("Expected rounding mode HALF_UP but got " + mc.getRoundingMode());
        }
    }

    private static void testZeroPrecision() {
        MathContext mc = new MathContext(0);
        if (mc.getPrecision() != 0) {
            throw new RuntimeException("Expected precision 0 but got " + mc.getPrecision());
        }
        if (mc.getRoundingMode() != RoundingMode.HALF_UP) {
            throw new RuntimeException("Expected rounding mode HALF_UP but got " + mc.getRoundingMode());
        }
    }

    private static void testPositivePrecision() {
        MathContext mc = new MathContext(10);
        if (mc.getPrecision() != 10) {
            throw new RuntimeException("Expected precision 10 but got " + mc.getPrecision());
        }
        if (mc.getRoundingMode() != RoundingMode.HALF_UP) {
            throw new RuntimeException("Expected rounding mode HALF_UP but got " + mc.getRoundingMode());
        }
    }

    private static void testLargePrecision() {
        int largePrecision = 1000000;
        MathContext mc = new MathContext(largePrecision);
        if (mc.getPrecision() != largePrecision) {
            throw new RuntimeException("Expected precision " + largePrecision + " but got " + mc.getPrecision());
        }
        if (mc.getRoundingMode() != RoundingMode.HALF_UP) {
            throw new RuntimeException("Expected rounding mode HALF_UP but got " + mc.getRoundingMode());
        }
    }
}
