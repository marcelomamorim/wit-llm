/* @test    @summary Test BigDecimal.divide(BigDecimal, MathContext) for division with precision.    @run main BigDecimalWitupTest_P3dcc5418be8c */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest_P3dcc5418be8c {
    public static void main(String[] args) {
        try {
            // Test division with non-zero divisor and positive precision
            BigDecimal bd1 = new BigDecimal("123.456");
            BigDecimal bd2 = new BigDecimal("2.0");
            MathContext mc = new MathContext(5);
            BigDecimal result = bd1.divide(bd2, mc);
            if (result == null) {
                throw new AssertionError("Result should not be null");
            }
            // Test division with zero divisor should throw ArithmeticException
            BigDecimal zero = BigDecimal.ZERO;
            try {
                bd1.divide(zero, mc);
                throw new AssertionError("Expected ArithmeticException for division by zero");
            } catch (ArithmeticException e) {
                // Expected exception
            }
            // Test division with zero dividend
            BigDecimal zeroDividend = BigDecimal.ZERO;
            BigDecimal resultZeroDividend = zeroDividend.divide(bd2, mc);
            if (resultZeroDividend.compareTo(BigDecimal.ZERO) != 0) {
                throw new AssertionError("Zero dividend division result should be zero");
            }
            // Test division with high precision
            MathContext highPrec = new MathContext(20);
            BigDecimal bd3 = new BigDecimal("1.00000000000000000001");
            BigDecimal bd4 = new BigDecimal("3");
            BigDecimal resHighPrec = bd3.divide(bd4, highPrec);
            if (resHighPrec == null) {
                throw new AssertionError("Result should not be null");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
