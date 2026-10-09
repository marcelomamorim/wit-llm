/* @test
   @summary Tests BigDecimal.pow(int, MathContext) for various cases.
   @run main BigDecimalWitupTest_Pcd7d3665a6af
 */
import java.math.BigDecimal;
import java.math.MathContext;
public class BigDecimalWitupTest_Pcd7d3665a6af {
    public static void main(String[] args) {
        testPowerZeroExponent();
        testPowerPositiveExponent();
        testPowerNegativeExponent();
        testPowerLargeExponent();
        testPowerWithPrecision();
        testPowerInvalidExponent();
    }

    static void testPowerZeroExponent() {
        BigDecimal bd = new BigDecimal("123.456");
        BigDecimal result = bd.pow(0, new MathContext(10));
        assert result.compareTo(BigDecimal.ONE) == 0 : "Power 0 should be 1";
    }

    static void testPowerPositiveExponent() {
        BigDecimal bd = new BigDecimal("2");
        BigDecimal result = bd.pow(3, new MathContext(10));
        assert result.compareTo(new BigDecimal("8")) == 0 : "2^3 should be 8";
    }

    static void testPowerNegativeExponent() {
        BigDecimal bd = new BigDecimal("2");
        BigDecimal result = bd.pow(-2, new MathContext(10));
        BigDecimal expected = BigDecimal.ONE.divide(new BigDecimal("4"), new MathContext(10));
        assert result.compareTo(expected) == 0 : "2^-2 should be 0.25";
    }

    static void testPowerLargeExponent() {
        BigDecimal bd = new BigDecimal("2");
        try {
            bd.pow(1000000000, new MathContext(10));
            assert false : "Expected ArithmeticException for large exponent";
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testPowerWithPrecision() {
        BigDecimal bd = new BigDecimal("1.5");
        MathContext mc = new MathContext(5);
        BigDecimal result = bd.pow(3, mc);
        BigDecimal expected = new BigDecimal("3.375");
        assert result.round(mc).compareTo(expected) == 0 : "1.5^3 with precision 5";
    }

    static void testPowerInvalidExponent() {
        BigDecimal bd = new BigDecimal("2");
        try {
            bd.pow(1000000000, new MathContext(10));
            assert false : "Expected ArithmeticException for invalid operation";
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
