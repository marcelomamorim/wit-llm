/* @test    @summary Test for Short.decode(String) method to ensure correct parsing and range checking.    @run main ShortWitupTest_Paffc25b163a3 */

import java.lang.Short;

public class ShortWitupTest_Paffc25b163a3 {

    public static void main(String[] args) {
        testValidDecodes();
        testInvalidDecodes();
        testRangeChecks();
    }

    static void testValidDecodes() {
        // Valid decimal string
        Short s1 = Short.decode("12345");
        assert s1 != null : "Failed to decode valid decimal string";
        assert s1.shortValue() == 12345 : "Decoded value mismatch";

        // Valid negative decimal string
        Short s2 = Short.decode("-12345");
        assert s2 != null : "Failed to decode valid negative decimal string";
        assert s2.shortValue() == -12345 : "Decoded negative value mismatch";

        // Valid hexadecimal string with 0x prefix
        Short s3 = Short.decode("0x7FFF");
        assert s3 != null : "Failed to decode valid hex string";
        assert s3.shortValue() == 32767 : "Hex decoded value mismatch";

        // Valid hexadecimal string with 0X prefix
        Short s4 = Short.decode("0X8000");
        assert s4 != null : "Failed to decode valid hex string with 0X";
        assert s4.shortValue() == -32768 : "Hex decoded value mismatch";

        // Valid octal string
        Short s5 = Short.decode("077");
        assert s5 != null : "Failed to decode valid octal string";
        assert s5.shortValue() == 63 : "Octal decoded value mismatch";
    }

    static void testInvalidDecodes() {
        // Null string
        try {
            Short.decode(null);
            assert false : "Expected NullPointerException for null input";
        } catch (NullPointerException e) {
            // expected
        }

        // Empty string
        try {
            Short.decode("");
            assert false : "Expected NumberFormatException for empty string";
        } catch (NumberFormatException e) {
            // expected
        }

        // Invalid prefix
        try {
            Short.decode("xyz");
            assert false : "Expected NumberFormatException for invalid prefix";
        } catch (NumberFormatException e) {
            // expected
        }

        // Out of range value
        try {
            Short.decode("32768"); // one above MAX_VALUE
            assert false : "Expected NumberFormatException for out of range value";
        } catch (NumberFormatException e) {
            // expected
        }

        try {
            Short.decode("-32769"); // one below MIN_VALUE
            assert false : "Expected NumberFormatException for out of range negative value";
        } catch (NumberFormatException e) {
            // expected
        }

        // Invalid characters
        try {
            Short.decode("12a45");
            assert false : "Expected NumberFormatException for invalid characters";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testRangeChecks() {
        // Valid boundary values
        Short sMin = Short.decode("-32768");
        assert sMin != null && sMin.shortValue() == -32768 : "Failed at MIN_VALUE";
        Short sMax = Short.decode("32767");
        assert sMax != null && sMax.shortValue() == 32767 : "Failed at MAX_VALUE";
    }
}
