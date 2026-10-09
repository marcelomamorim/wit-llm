/* @test    @summary Test Integer.decode with various valid and invalid inputs    @run main IntegerWitupTest_P0148f88bc177 */

import java.lang.Integer;

public class IntegerWitupTest_P0148f88bc177 {
    public static void main(String[] args) {
        testDecodeValidDecimal();
        testDecodeValidHexLower();
        testDecodeValidHexUpper();
        testDecodeValidOctal();
        testDecodeWithSign();
        testDecodeInvalidEmpty();
        testDecodeInvalidSignPosition();
        testDecodeInvalidNumberFormat();
        System.out.println("All tests passed.");
    }

    static void testDecodeValidDecimal() {
        Integer result = Integer.decode("12345");
        if (result == null || result.intValue() != 12345) {
            throw new AssertionError("Failed: decode("12345")");
        }
    }

    static void testDecodeValidHexLower() {
        Integer result = Integer.decode("0x1a");
        if (result == null || result.intValue() != 26) {
            throw new AssertionError("Failed: decode("0x1a")");
        }
    }

    static void testDecodeValidHexUpper() {
        Integer result = Integer.decode("0XFF");
        if (result == null || result.intValue() != 255) {
            throw new AssertionError("Failed: decode("0XFF")");
        }
    }

    static void testDecodeValidOctal() {
        Integer result = Integer.decode("077");
        if (result == null || result.intValue() != 63) {
            throw new AssertionError("Failed: decode("077")");
        }
    }

    static void testDecodeWithSign() {
        Integer resultPos = Integer.decode("+123");
        Integer resultNeg = Integer.decode("-123");
        if (resultPos == null || resultPos.intValue() != 123) {
            throw new AssertionError("Failed: decode("+123")");
        }
        if (resultNeg == null || resultNeg.intValue() != -123) {
            throw new AssertionError("Failed: decode("-123")");
        }
    }

    static void testDecodeInvalidEmpty() {
        try {
            Integer.decode("");
            throw new AssertionError("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeInvalidSignPosition() {
        try {
            Integer.decode("-+123");
            throw new AssertionError("Expected NumberFormatException for sign in wrong position");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeInvalidNumberFormat() {
        try {
            Integer.decode("0xGHI");
            throw new AssertionError("Expected NumberFormatException for invalid hex");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
