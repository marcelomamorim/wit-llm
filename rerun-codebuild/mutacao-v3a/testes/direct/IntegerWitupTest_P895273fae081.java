/* @test    @summary Test Integer.decode method behavior    @run main IntegerWitupTest_P895273fae081 */

import java.lang.Integer;

public class IntegerWitupTest_P895273fae081 {

    public static void main(String[] args) {
        testDecodeBasicDecimal();
        testDecodeNegativeDecimal();
        testDecodeHexadecimalLowercase();
        testDecodeHexadecimalUppercase();
        testDecodeOctal();
        testDecodeWithPlusSign();
        testDecodeInvalidSignPosition();
        testDecodeEmptyString();
        testDecodeInvalidRadixPrefix();
        testDecodeInvalidNumberFormat();
    }

    static void testDecodeBasicDecimal() {
        Integer result = Integer.decode("12345");
        assert result != null : "Result should not be null";
        assert result.equals(Integer.valueOf(12345)) : "Expected 12345, got " + result;
    }

    static void testDecodeNegativeDecimal() {
        Integer result = Integer.decode("-6789");
        assert result != null : "Result should not be null";
        assert result.equals(Integer.valueOf(-6789)) : "Expected -6789, got " + result;
    }

    static void testDecodeHexadecimalLowercase() {
        Integer result = Integer.decode("0x1A3F");
        assert result != null : "Result should not be null";
        assert result.equals(Integer.valueOf(0x1A3F)) : "Expected 0x1A3F, got " + result;
    }

    static void testDecodeHexadecimalUppercase() {
        Integer result = Integer.decode("0X4B2");
        assert result != null : "Result should not be null";
        assert result.equals(Integer.valueOf(0x4B2)) : "Expected 0x4B2, got " + result;
    }

    static void testDecodeOctal() {
        Integer result = Integer.decode("0123");
        assert result != null : "Result should not be null";
        assert result.equals(Integer.valueOf(0123)) : "Expected 0123, got " + result;
    }

    static void testDecodeWithPlusSign() {
        Integer result = Integer.decode("+456");
        assert result != null : "Result should not be null";
        assert result.equals(Integer.valueOf(456)) : "Expected 456, got " + result;
    }

    static void testDecodeInvalidSignPosition() {
        try {
            Integer.decode("45-6");
            assert false : "Expected NumberFormatException";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeEmptyString() {
        try {
            Integer.decode("");
            assert false : "Expected NumberFormatException";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeInvalidRadixPrefix() {
        try {
            Integer.decode("0b1010");
            assert false : "Expected NumberFormatException";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeInvalidNumberFormat() {
        try {
            Integer.decode("notANumber");
            assert false : "Expected NumberFormatException";
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
