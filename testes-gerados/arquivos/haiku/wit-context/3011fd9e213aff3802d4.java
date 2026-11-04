/*
 * @test
 * @summary Tests the public MathContext(int) constructor: default rounding mode and precision validation
 * @run main MathContextWitupTest
 */

import java.math.MathContext;
import java.math.RoundingMode;

public class MathContextWitupTest {
    public static void main(String[] args) {
        MathContext mc = new MathContext(7);
        check(mc.getPrecision() == 7, "precision should be 7");
        check(mc.getRoundingMode() == RoundingMode.HALF_UP, "default rounding mode should be HALF_UP");
        check(new MathContext(0).getPrecision() == 0, "precision 0 should be accepted");
        check(new MathContext(7).equals(new MathContext(7, RoundingMode.HALF_UP)), "should equal the two-argument form with HALF_UP");
        expectIllegalArgument(-1);
        expectIllegalArgument(Integer.MIN_VALUE);
    }

    static void expectIllegalArgument(int precision) {
        try {
            new MathContext(precision);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for precision " + precision);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
