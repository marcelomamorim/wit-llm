/* @test    @summary Test Long.decode with various string inputs    @run main LongWitupTest_P0cd75859bf43 */

import java.lang.Long;

public class LongWitupTest_P0cd75859bf43 {

    public static void main(String[] args) {
        testDecodeDecimal();
        testDecodeHexLowercase();
        testDecodeHexUppercase();
        testDecodeOctal();
        testDecodeWithSign();
        testDecodeInvalidSignPosition();
        testDecodeEmptyString();
        testDecodeInvalidNumber();
        testDecodeWithPlusSign();
        testDecodeWithHashPrefix();
        testDecodeWithZeroPrefix();
    }

    static void testDecodeDecimal() {
        Long result = Long.decode("12345");
        assert result.equals(Long.valueOf(12345)) : "Failed decimal decode";
    }

    static void testDecodeHexLowercase() {
        Long result = Long.decode("0x1a2b");
        assert result.equals(Long.valueOf(0x1a2b)) : "Failed lowercase hex decode";
    }

    static void testDecodeHexUppercase() {
        Long result = Long.decode("0X1A2B");
        assert result.equals(Long.valueOf(0x1a2b)) : "Failed uppercase hex decode";
    }

    static void testDecodeOctal() {
        Long result = Long.decode("077");
        assert result.equals(Long.valueOf(077)) : "Failed octal decode";
    }

    static void testDecodeWithSign() {
        Long resultPos = Long.decode("+9876");
        Long resultNeg = Long.decode("-9876");
        assert resultPos.equals(Long.valueOf(9876)) : "Failed plus sign decode";
        assert resultNeg.equals(Long.valueOf(-9876)) : "Failed minus sign decode";
    }

    static void testDecodeInvalidSignPosition() {
        try {
            Long.decode("-+123");
            assert false : "Expected NumberFormatException for invalid sign position";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeEmptyString() {
        try {
            Long.decode("");
            assert false : "Expected NumberFormatException for empty string";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeInvalidNumber() {
        try {
            Long.decode("notanumber");
            assert false : "Expected NumberFormatException for invalid number";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeWithPlusSign() {
        try {
            Long.decode("+-123");
            assert false : "Expected NumberFormatException for sign in wrong position";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeWithHashPrefix() {
        Long result = Long.decode("#1a2b");
        assert result.equals(Long.valueOf(0x1a2b)) : "Failed hash prefix decode";
    }

    static void testDecodeWithZeroPrefix() {
        Long result = Long.decode("077");
        assert result.equals(Long.valueOf(077)) : "Failed zero prefix decode";
    }
}
