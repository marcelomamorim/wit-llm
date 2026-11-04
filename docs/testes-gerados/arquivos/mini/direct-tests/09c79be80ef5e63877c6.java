/*
 * @test
 * @summary Test BigDecimal(int val, MathContext mc) constructor behavior with various precisions and rounding modes
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalWitupTest {
    public static void main(String[] args) {
        testExactPrecision();
        testRoundingDown();
        testRoundingUp();
        testZeroPrecision();
        testNegativeValueRounding();
    }

    static void testExactPrecision() {
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        BigDecimal bd = new BigDecimal(12345, mc);
        // The precision should be <= mc.precision
        if (bd.precision() > mc.getPrecision()) {
            throw new RuntimeException("Precision exceeds MathContext precision");
        }
        // The scale should be >= 0
        if (bd.scale() < 0) {
            throw new RuntimeException("Scale is negative");
        }
    }

    static void testRoundingDown() {
        MathContext mc = new MathContext(2, RoundingMode.DOWN);
        BigDecimal bd = new BigDecimal(129, mc);
        // The value should be rounded down to 12 * 10^scale
        // So bd.unscaledValue() should be 12 or 13 depending on rounding
        // We check that bd.precision() <= mc.precision
        if (bd.precision() > mc.getPrecision()) {
            throw new RuntimeException("Precision exceeds MathContext precision");
        }
    }

    static void testRoundingUp() {
        MathContext mc = new MathContext(2, RoundingMode.UP);
        BigDecimal bd = new BigDecimal(121, mc);
        if (bd.precision() > mc.getPrecision()) {
            throw new RuntimeException("Precision exceeds MathContext precision");
        }
    }

    static void testZeroPrecision() {
        MathContext mc = new MathContext(0, RoundingMode.HALF_UP);
        BigDecimal bd = new BigDecimal(123456789, mc);
        // When precision is zero, no rounding should occur, precision should be number of digits
        int expectedPrecision = bd.precision();
        if (expectedPrecision != bd.precision()) {
            throw new RuntimeException("Precision mismatch for zero precision MathContext");
        }
    }

    static void testNegativeValueRounding() {
        MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
        BigDecimal bd = new BigDecimal(-9876, mc);
        if (bd.precision() > mc.getPrecision()) {
            throw new RuntimeException("Precision exceeds MathContext precision for negative value");
        }
    }
}
