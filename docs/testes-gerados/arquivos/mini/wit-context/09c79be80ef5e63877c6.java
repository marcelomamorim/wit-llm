/*
 * @test
 * @summary Test BigDecimal(int val, MathContext mc) constructor behavior including rounding and scale
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalWitupTest {
    public static void main(String[] args) {
        testExactPrecisionZero();
        testRoundingWithPrecision();
        testRoundingWithPrecisionAndScaleDrop();
    }

    private static void testExactPrecisionZero() {
        MathContext mc = new MathContext(0, RoundingMode.HALF_UP); // unlimited precision
        BigDecimal bd = new BigDecimal(12345, mc);
        // With precision 0, no rounding, scale should be 0
        if (bd.scale() != 0) {
            throw new AssertionError("Expected scale 0 but got " + bd.scale());
        }
        if (bd.intValue() != 12345) {
            throw new AssertionError("Expected int value 12345 but got " + bd.intValue());
        }
    }

    private static void testRoundingWithPrecision() {
        // precision 3, value with more digits than precision, expect rounding
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        BigDecimal bd = new BigDecimal(12345, mc);
        // The precision should be <= 3
        if (bd.precision() > 3) {
            throw new AssertionError("Precision should be <= 3 but got " + bd.precision());
        }
        // The scale should be >= 0
        if (bd.scale() < 0) {
            throw new AssertionError("Scale should be >= 0 but got " + bd.scale());
        }
        // The intCompact field is internal, so check value via toString
        String s = bd.toString();
        if (!s.matches("\d+(\.\d+)?(E\d+)?")) {
            throw new AssertionError("Unexpected BigDecimal string representation: " + s);
        }
    }

    private static void testRoundingWithPrecisionAndScaleDrop() {
        // This test tries to trigger the rounding loop with drop > 0
        MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
        BigDecimal bd = new BigDecimal(12345, mc);
        // The precision should be <= 2
        if (bd.precision() > 2) {
            throw new AssertionError("Precision should be <= 2 but got " + bd.precision());
        }
        // The scale should be >= 0
        if (bd.scale() < 0) {
            throw new AssertionError("Scale should be >= 0 but got " + bd.scale());
        }
        // The string representation should be parseable as BigDecimal
        try {
            new BigDecimal(bd.toString());
        } catch (Exception e) {
            throw new AssertionError("BigDecimal string representation is invalid: " + bd.toString());
        }
    }
}
