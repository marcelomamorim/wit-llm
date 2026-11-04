/* @test    @summary Test constructor MathContext(int setPrecision) for various precision values    @run main MathContextWitupTest */

import java.math.MathContext;
import java.math.RoundingMode;

public class MathContextWitupTest {

    public static void main(String[] args) {
        testConstructorWithZeroPrecision();
        testConstructorWithPositivePrecision();
        testConstructorWithLargePrecision();
    }

    private static void testConstructorWithZeroPrecision() {
        MathContext mc = new MathContext(0);
        assert mc.getPrecision() == 0 : "Expected precision 0";
        assert mc.getRoundingMode() == RoundingMode.HALF_UP : "Expected default rounding mode HALF_UP";
    }

    private static void testConstructorWithPositivePrecision() {
        int precision = 10;
        MathContext mc = new MathContext(precision);
        assert mc.getPrecision() == precision : "Expected precision " + precision;
        assert mc.getRoundingMode() == RoundingMode.HALF_UP : "Expected default rounding mode HALF_UP";
    }

    private static void testConstructorWithLargePrecision() {
        int largePrecision = Integer.MAX_VALUE;
        MathContext mc = new MathContext(largePrecision);
        assert mc.getPrecision() == largePrecision : "Expected precision " + largePrecision;
        assert mc.getRoundingMode() == RoundingMode.HALF_UP : "Expected default rounding mode HALF_UP";
    }
}
