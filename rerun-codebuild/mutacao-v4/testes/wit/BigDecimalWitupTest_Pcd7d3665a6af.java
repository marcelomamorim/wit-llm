/* @test
   @summary Testa o método BigDecimal.pow(int, MathContext) para comportamentos observáveis.
   @run main BigDecimalWitupTest_Pcd7d3665a6af
 */
import java.math.BigDecimal;
import java.math.MathContext;
public class BigDecimalWitupTest_Pcd7d3665a6af {
    public static void main(String[] args) {
        testPowerWithZeroPrecision();
        testPowerWithNegativeExponent();
        testPowerWithLargeExponent();
        testPowerWithSmallExponent();
        testPowerWithNegativeExponentAndReciprocal();
    }

    static void testPowerWithZeroPrecision() {
        BigDecimal bd = new BigDecimal("2");
        MathContext mc = new MathContext(0);
        try {
            BigDecimal result = bd.pow(3, mc);
            if (result == null) {
                throw new AssertionError("Result should not be null");
            }
        } catch (ArithmeticException e) {
            throw new AssertionError("Unexpected exception for zero precision", e);
        }
    }

    static void testPowerWithNegativeExponent() {
        BigDecimal bd = new BigDecimal("2");
        MathContext mc = new MathContext(10);
        try {
            BigDecimal result = bd.pow(-2, mc);
            if (result == null) {
                throw new AssertionError("Result should not be null");
            }
        } catch (ArithmeticException e) {
            // Expecting exception due to negative exponent
        }
    }

    static void testPowerWithLargeExponent() {
        BigDecimal bd = new BigDecimal("2");
        MathContext mc = new MathContext(10);
        try {
            BigDecimal result = bd.pow(1000000000, mc);
            throw new AssertionError("Expected ArithmeticException for large exponent");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }

    static void testPowerWithSmallExponent() {
        BigDecimal bd = new BigDecimal("2");
        MathContext mc = new MathContext(10);
        BigDecimal result = bd.pow(0, mc);
        if (!result.equals(BigDecimal.ONE)) {
            throw new AssertionError("2^0 should be 1");
        }
    }

    static void testPowerWithNegativeExponentAndReciprocal() {
        BigDecimal bd = new BigDecimal("2");
        MathContext mc = new MathContext(10);
        BigDecimal result = bd.pow(-2, mc);
        BigDecimal expected = BigDecimal.ONE.divide(bd.pow(2, mc), mc);
        if (result.compareTo(expected) != 0) {
            throw new AssertionError("Reciprocal power mismatch");
        }
    }
}
