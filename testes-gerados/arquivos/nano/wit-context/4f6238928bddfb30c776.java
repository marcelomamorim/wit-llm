/* @test    @summary Test for MathContext(String) constructor with valid and invalid inputs    @run main MathContextWitupTest */

import java.math.*;

public class MathContextWitupTest {

    public static void main(String[] args) {
        testValidString();
        testNullString();
        testInvalidFormatString();
        testNegativeDigits();
    }

    private static void testValidString() {
        String validVal = "precision=10 roundingMode=HALF_UP";
        try {
            MathContext mc = new MathContext(validVal);
            if (mc == null || mc.getClass() != MathContext.class) {
                throw new AssertionError("Failed to create MathContext with valid string");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid string: " + e);
        }
    }

    private static void testNullString() {
        try {
            new MathContext(null);
            throw new AssertionError("Expected NullPointerException for null string");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testInvalidFormatString() {
        String invalidVal = "invalid format";
        try {
            new MathContext(invalidVal);
            throw new AssertionError("Expected IllegalArgumentException for invalid format string");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testNegativeDigits() {
        String negativeDigitsVal = "precision=-5 roundingMode=HALF_UP";
        try {
            new MathContext(negativeDigitsVal);
            throw new AssertionError("Expected IllegalArgumentException for negative precision");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
