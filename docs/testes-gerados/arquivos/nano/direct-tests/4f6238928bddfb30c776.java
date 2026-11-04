/* @test    @summary Test constructor MathContext(String val) with valid and invalid inputs    @run main MathContextWitupTest */

import java.math.*;

public class MathContextWitupTest {

    public static void main(String[] args) {
        testValidString();
        testNullString();
        testInvalidFormatString();
        testNegativeDigits();
    }

    static void testValidString() {
        String validStr = "precision=10 roundingMode=HALF_UP";
        MathContext mc = new MathContext(validStr);
        assert mc != null : "MathContext should not be null for valid string";
        assert mc.getPrecision() == 10 : "Expected precision 10";
        assert mc.getRoundingMode() == RoundingMode.HALF_UP : "Expected RoundingMode.HALF_UP";
    }

    static void testNullString() {
        try {
            new MathContext(null);
            assert false : "NullPointerException expected";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("null String") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testInvalidFormatString() {
        String invalidStr = "invalid format";
        try {
            new MathContext(invalidStr);
            assert false : "IllegalArgumentException expected";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("bad string format") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testNegativeDigits() {
        String negativeDigitsStr = "precision=-5 roundingMode=HALF_UP";
        try {
            new MathContext(negativeDigitsStr);
            assert false : "IllegalArgumentException expected for negative digits";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("Digits < 0") : "Unexpected message: " + e.getMessage();
        }
    }
}
