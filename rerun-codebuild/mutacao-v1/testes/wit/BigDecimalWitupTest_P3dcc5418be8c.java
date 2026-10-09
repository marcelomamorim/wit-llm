/* @test    @summary Test BigDecimal.divide(BigDecimal, MathContext) for exception paths    @run main BigDecimalWitupTest_P3dcc5418be8c */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest_P3dcc5418be8c {

    public static void main(String[] args) {
        testDivideDivisionByZero();
        testDivideZeroDividend();
        testDivideNormal();
    }

    private static void testDivideDivisionByZero() {
        try {
            BigDecimal bd1 = new BigDecimal("123.45");
            BigDecimal zero = new BigDecimal("0");
            MathContext mc = new MathContext(10);
            bd1.divide(zero, mc);
            throw new AssertionError("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }

    private static void testDivideZeroDividend() {
        try {
            BigDecimal zero = new BigDecimal("0");
            BigDecimal bd2 = new BigDecimal("987.65");
            MathContext mc = new MathContext(10);
            BigDecimal result = zero.divide(bd2, mc);
            if (result == null) {
                throw new AssertionError("Result should not be null");
            }
        } catch (ArithmeticException e) {
            throw new AssertionError("Unexpected ArithmeticException for zero dividend", e);
        }
    }

    private static void testDivideNormal() {
        try {
            BigDecimal bd1 = new BigDecimal("1000");
            BigDecimal bd2 = new BigDecimal("3");
            MathContext mc = new MathContext(10);
            BigDecimal result = bd1.divide(bd2, mc);
            if (result == null) {
                throw new AssertionError("Result should not be null");
            }
        } catch (ArithmeticException e) {
            throw new AssertionError("Unexpected ArithmeticException in normal division", e);
        }
    }
}
