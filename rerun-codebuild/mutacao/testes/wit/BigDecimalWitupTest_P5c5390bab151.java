/* @test    @summary Test for BigDecimal.divide(BigDecimal, MathContext) method behavior.    @run main BigDecimalWitupTest_P5c5390bab151 */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest_P5c5390bab151 {

    public static void main(String[] args) {
        testDivideNormal();
        testDivideDivisionByZero();
        testDivideZeroDividend();
        testDivideWithPrecision();
    }

    static void testDivideNormal() {
        BigDecimal bd1 = new BigDecimal("10.5");
        BigDecimal bd2 = new BigDecimal("2.0");
        MathContext mc = new MathContext(10);
        BigDecimal result = bd1.divide(bd2, mc);
        System.out.println("testDivideNormal: " + result.toString());
    }

    static void testDivideDivisionByZero() {
        BigDecimal bd1 = new BigDecimal("10.5");
        BigDecimal zero = BigDecimal.ZERO;
        MathContext mc = new MathContext(10);
        try {
            bd1.divide(zero, mc);
            System.out.println("testDivideDivisionByZero: Failed to throw ArithmeticException");
        } catch (ArithmeticException e) {
            System.out.println("testDivideDivisionByZero: Caught expected exception: " + e.getMessage());
        }
    }

    static void testDivideZeroDividend() {
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal bd2 = new BigDecimal("123.456");
        MathContext mc = new MathContext(10);
        BigDecimal result = zero.divide(bd2, mc);
        System.out.println("testDivideZeroDividend: " + result.toString());
    }

    static void testDivideWithPrecision() {
        BigDecimal bd1 = new BigDecimal("12345.6789");
        BigDecimal bd2 = new BigDecimal("0.0001");
        MathContext mc = new MathContext(5);
        BigDecimal result = bd1.divide(bd2, mc);
        System.out.println("testDivideWithPrecision: " + result.toString());
    }
}
