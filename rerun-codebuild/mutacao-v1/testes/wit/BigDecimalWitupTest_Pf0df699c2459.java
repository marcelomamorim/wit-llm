/* @test    @summary Test BigDecimal.pow(int, MathContext) for exception paths and behavior    @run main BigDecimalWitupTest_Pf0df699c2459 */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest_Pf0df699c2459 {

    public static void main(String[] args) {
        testPowWithInvalidPrecision();
        testPowWithExcessiveMagnitude();
        testPowZeroExponent();
        testPowNegativeExponent();
    }

    private static void testPowWithInvalidPrecision() {
        try {
            BigDecimal bd = new BigDecimal("2");
            MathContext mc = new MathContext(5);
            // Simulate precision == 0 and n outside valid range
            MathContext mcInvalid = new MathContext(0);
            // n within range
            bd.pow(3, mcInvalid);
            // n outside range should throw
            boolean exceptionThrown = false;
            try {
                bd.pow(1000000000, mcInvalid);
            } catch (ArithmeticException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected ArithmeticException for large n with precision=0");
            }
        } catch (Exception e) {
            throw new RuntimeException("Test failed: testPowWithInvalidPrecision", e);
        }
    }

    private static void testPowWithExcessiveMagnitude() {
        try {
            BigDecimal bd = new BigDecimal("2");
            MathContext mc = new MathContext(10);
            // n within range
            bd.pow(10, mc);
            // n outside range should throw
            boolean exceptionThrown = false;
            try {
                bd.pow(1000000000, mc);
            } catch (ArithmeticException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected ArithmeticException for excessive n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Test failed: testPowWithExcessiveMagnitude", e);
        }
    }

    private static void testPowZeroExponent() {
        try {
            BigDecimal bd = new BigDecimal("123.456");
            MathContext mc = new MathContext(10);
            BigDecimal result = bd.pow(0, mc);
            if (!result.equals(BigDecimal.ONE)) {
                throw new AssertionError("pow(0) should return 1, got: " + result);
            }
        } catch (Exception e) {
            throw new RuntimeException("Test failed: testPowZeroExponent", e);
        }
    }

    private static void testPowNegativeExponent() {
        try {
            BigDecimal bd = new BigDecimal("2");
            MathContext mc = new MathContext(10);
            BigDecimal result = bd.pow(-2, mc);
            BigDecimal expected = BigDecimal.ONE.divide(new BigDecimal("4"), mc);
            if (result.compareTo(expected) != 0) {
                throw new AssertionError("pow(-2) result mismatch, expected: " + expected + ", got: " + result);
            }
        } catch (ArithmeticException e) {
            throw new AssertionError("Unexpected ArithmeticException in testPowNegativeExponent", e);
        } catch (Exception e) {
            throw new RuntimeException("Test failed: testPowNegativeExponent", e);
        }
    }
}
