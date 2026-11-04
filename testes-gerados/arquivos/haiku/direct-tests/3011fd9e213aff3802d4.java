/*
 * @test
 * @summary Verifies that the single-argument MathContext(int) constructor delegates to the default HALF_UP rounding mode and preserves the given precision
 * @run main MathContextWitupTest
 */

import java.math.MathContext;
import java.math.RoundingMode;

public class MathContextWitupTest {

    public static void main(String[] args) {
        testPrecisionIsPreserved();
        testDefaultRoundingModeIsHalfUp();
        testEquivalentToExplicitHalfUp();
        testZeroPrecisionMatchesUnlimited();
        testDistinctPrecisionsAreNotEqual();
    }

    static void testPrecisionIsPreserved() {
        MathContext mc = new MathContext(7);
        check(mc.getPrecision() == 7, "precision expected 7 but was " + mc.getPrecision());
    }

    static void testDefaultRoundingModeIsHalfUp() {
        MathContext mc = new MathContext(7);
        check(mc.getRoundingMode() == RoundingMode.HALF_UP,
              "rounding mode expected HALF_UP but was " + mc.getRoundingMode());
    }

    static void testEquivalentToExplicitHalfUp() {
        MathContext implicit = new MathContext(9);
        MathContext explicit = new MathContext(9, RoundingMode.HALF_UP);
        check(implicit.equals(explicit), "MathContext(9) should equal MathContext(9, HALF_UP)");
        check(implicit.hashCode() == explicit.hashCode(), "equal MathContext objects must have equal hash codes");
    }

    static void testZeroPrecisionMatchesUnlimited() {
        MathContext mc = new MathContext(0);
        check(mc.getPrecision() == 0, "precision expected 0 but was " + mc.getPrecision());
        check(mc.getRoundingMode() == RoundingMode.HALF_UP,
              "rounding mode expected HALF_UP but was " + mc.getRoundingMode());
        check(mc.equals(MathContext.UNLIMITED), "MathContext(0) should equal MathContext.UNLIMITED");
    }

    static void testDistinctPrecisionsAreNotEqual() {
        MathContext a = new MathContext(7);
        MathContext b = new MathContext(16);
        check(!a.equals(b), "MathContext(7) should not equal MathContext(16)");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
