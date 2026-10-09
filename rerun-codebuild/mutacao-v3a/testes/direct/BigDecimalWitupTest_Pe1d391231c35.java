/* @test    @summary Test BigDecimal.divide with MathContext for division by zero and normal division    @run main BigDecimalWitupTest_Pe1d391231c35 */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest_Pe1d391231c35 {

    public static void main(String[] args) {
        testDivideWithMathContext();
    }

    static void testDivideWithMathContext() {
        MathContext mc = new MathContext(10);
        BigDecimal zero = new BigDecimal(0);
        BigDecimal one = new BigDecimal(1);
        BigDecimal two = new BigDecimal(2);
        BigDecimal ten = new BigDecimal(10);
        BigDecimal dividend = new BigDecimal("123.456");
        BigDecimal divisor = new BigDecimal("0.0");

        try {
            // Test division by zero should throw ArithmeticException
            dividend.divide(divisor, mc);
            fail("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            // expected
        }

        // Test normal division with MathContext
        BigDecimal result = dividend.divide(two, mc);
        // Verify result is as expected within precision
        BigDecimal expected = new BigDecimal("61.728");
        if (result.compareTo(expected) != 0) {
            throw new AssertionError("Unexpected division result: " + result);
        }

        // Test division with scale adjustment, should not throw
        BigDecimal result2 = dividend.divide(ten, mc);
        BigDecimal expected2 = new BigDecimal("12.3456");
        if (result2.compareTo(expected2) != 0) {
            throw new AssertionError("Unexpected division result: " + result2);
        }
    }

    static void fail(String message) {
        throw new AssertionError(message);
    }
}
