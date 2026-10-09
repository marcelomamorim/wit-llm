/*
 * @test
 * @summary Test java.lang.Short.decode(String) method behavior
 * @run main ShortWitupTest_P500b1d6ad1b3
 */

public class ShortWitupTest_P500b1d6ad1b3 {
    public static void main(String[] args) {
        testDecodeValidDecimal();
        testDecodeValidHex();
        testDecodeValidOctal();
        testDecodeValueOutOfRange();
        testDecodeNullInput();
        testDecodeEmptyString();
        testDecodeInvalidFormat();
    }

    private static void testDecodeValidDecimal() {
        Short s = Short.decode("123");
        if (s.shortValue() != 123) {
            throw new RuntimeException("Failed: decode decimal 123");
        }
        Short sNeg = Short.decode("-32768");
        if (sNeg.shortValue() != -32768) {
            throw new RuntimeException("Failed: decode decimal -32768");
        }
    }

    private static void testDecodeValidHex() {
        Short s = Short.decode("0x7FFF");
        if (s.shortValue() != 32767) {
            throw new RuntimeException("Failed: decode hex 0x7FFF");
        }
        Short sUpper = Short.decode("0X7FFF");
        if (sUpper.shortValue() != 32767) {
            throw new RuntimeException("Failed: decode hex 0X7FFF");
        }
        Short sHash = Short.decode("#7FFF");
        if (sHash.shortValue() != 32767) {
            throw new RuntimeException("Failed: decode hex #7FFF");
        }
    }

    private static void testDecodeValidOctal() {
        Short s = Short.decode("0777");
        if (s.shortValue() != 511) {
            throw new RuntimeException("Failed: decode octal 0777");
        }
    }

    private static void testDecodeValueOutOfRange() {
        try {
            Short.decode("32768");
            throw new RuntimeException("Failed: decode should throw NumberFormatException for 32768");
        } catch (NumberFormatException e) {
            // expected
        }
        try {
            Short.decode("-32769");
            throw new RuntimeException("Failed: decode should throw NumberFormatException for -32769");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testDecodeNullInput() {
        try {
            Short.decode(null);
            throw new RuntimeException("Failed: decode should throw NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testDecodeEmptyString() {
        try {
            Short.decode("");
            throw new RuntimeException("Failed: decode should throw NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testDecodeInvalidFormat() {
        try {
            Short.decode("12AB34");
            throw new RuntimeException("Failed: decode should throw NumberFormatException for invalid format");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
