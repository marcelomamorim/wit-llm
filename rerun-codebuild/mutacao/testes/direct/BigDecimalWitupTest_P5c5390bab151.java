/* @test    @summary Test BigDecimal.divide with MathContext for normal division and division by zero    @run main BigDecimalWitupTest_P5c5390bab151 */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest_P5c5390bab151 {

    public static void main(String[] args) {
        testDivideWithMathContext();
        testDivideByZeroThrows();
    }

    static void testDivideWithMathContext() {
        BigDecimal num = new BigDecimal("100");
        BigDecimal denom = new BigDecimal("4");
        MathContext mc = new MathContext(5);
        BigDecimal result = num.divide(denom, mc);
        assert result != null : "Result should not be null";
        // Expected: 25 with 5 digits precision
        assert result.toString().startsWith("25") : "Unexpected division result: " + result;
    }

    static void testDivideByZeroThrows() {
        BigDecimal num = new BigDecimal("12345");
        BigDecimal zero = new BigDecimal("0");
        MathContext mc = new MathContext(10);
        try {
            num.divide(zero, mc);
            assert false : "Expected ArithmeticException for division by zero";
        } catch (ArithmeticException e) {
            assert e.getMessage().equals("Division by zero") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
