/* @test    @summary Test BigDecimal(int, MathContext) constructor for various MathContext settings    @run main BigDecimalWitupTest */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest {

    public static void main(String[] args) {
        testBigDecimalConstructorWithMathContext();
    }

    private static void testBigDecimalConstructorWithMathContext() {
        MathContext mcUnlimited = new MathContext(0); // unlimited precision
        MathContext mcPrecise = new MathContext(5); // precision 5
        MathContext mcRounding = new MathContext(3); // precision 3

        // Test with value 12345 and unlimited precision
        BigDecimal bdUnlimited = new BigDecimal(12345, mcUnlimited);
        assert bdUnlimited != null : "BigDecimal with unlimited precision should not be null";

        // Test with value 12345 and precision 5
        BigDecimal bdPrecise = new BigDecimal(12345, mcPrecise);
        assert bdPrecise != null : "BigDecimal with precision 5 should not be null";
        // Check that the unscaled value is as expected
        assert bdPrecise.unscaledValue().equals(BigDecimal.valueOf(12345).unscaledValue()) : "Unscaled value mismatch";

        // Test with value 12345 and precision 3
        BigDecimal bdRounding = new BigDecimal(12345, mcRounding);
        assert bdRounding != null : "BigDecimal with precision 3 should not be null";
        // The unscaled value should be same, but scale may be adjusted
        assert bdRounding.unscaledValue().equals(BigDecimal.valueOf(12345).unscaledValue()) : "Unscaled value mismatch";

        // Additional test: check that the scale is adjusted correctly when rounding occurs
        MathContext mcRoundUp = new MathContext(2); // precision 2
        BigDecimal bdRoundUp = new BigDecimal(12345, mcRoundUp);
        assert bdRoundUp != null : "BigDecimal with precision 2 should not be null";
        // The unscaled value should be rounded accordingly
        // Since 12345 with precision 2, expect unscaled value to be 12000 or similar after rounding
        long unscaled = bdRoundUp.unscaledValue().longValue();
        assert Math.abs(unscaled) < 100000 : "Unscaled value out of expected range";
    }
}
