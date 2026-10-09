/* @test    @summary Test BigDecimal.pow(int, MathContext) for exception paths and behavior    @run main BigDecimalWitupTest_P77114316cefc */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest_P77114316cefc {

    public static void main(String[] args) {
        testPowWithInvalidPrecision();
        testPowWithExcessiveExponent();
        testPowWithZeroExponent();
        testPowWithNegativeExponent();
    }

    private static void testPowWithInvalidPrecision() {
        BigDecimal bd = new BigDecimal("1.23");
        MathContext mc = new MathContext(5);
        int n = 10;
        try {
            MathContext invalidMc = new MathContext(6); // elength > mc.precision
            bd.pow(n, invalidMc);
            System.out.println("FAIL: Expected ArithmeticException for invalid precision");
        } catch (ArithmeticException e) {
            System.out.println("PASS: Caught expected ArithmeticException for invalid precision");
        }
    }

    private static void testPowWithExcessiveExponent() {
        BigDecimal bd = new BigDecimal("2");
        MathContext mc = new MathContext(10);
        int n = 1000000000; // > 999999999
        try {
            bd.pow(n, mc);
            System.out.println("FAIL: Expected ArithmeticException for excessive exponent");
        } catch (ArithmeticException e) {
            System.out.println("PASS: Caught expected ArithmeticException for excessive exponent");
        }
    }

    private static void testPowWithZeroExponent() {
        BigDecimal bd = new BigDecimal("5");
        MathContext mc = new MathContext(10);
        try {
            BigDecimal result = bd.pow(0, mc);
            if (result.compareTo(BigDecimal.ONE) == 0) {
                System.out.println("PASS: pow with zero exponent returns 1");
            } else {
                System.out.println("FAIL: pow with zero exponent did not return 1");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for zero exponent: " + e);
        }
    }

    private static void testPowWithNegativeExponent() {
        BigDecimal bd = new BigDecimal("2");
        MathContext mc = new MathContext(10);
        try {
            BigDecimal result = bd.pow(-2, mc);
            BigDecimal expected = BigDecimal.ONE.divide(bd.pow(2), mc);
            if (result.compareTo(expected) == 0) {
                System.out.println("PASS: pow with negative exponent produces reciprocal");
            } else {
                System.out.println("FAIL: pow with negative exponent does not produce reciprocal");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for negative exponent: " + e);
        }
    }
}
