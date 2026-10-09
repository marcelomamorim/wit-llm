/* @test    @summary Test Integer.decode with various string inputs    @run main IntegerWitupTest_Pf900b8e05da7 */

import java.lang.Integer;

public class IntegerWitupTest_Pf900b8e05da7 {

    public static void main(String[] args) {
        testDecodeDecimal();
        testDecodeHexadecimal();
        testDecodeOctal();
        testDecodeWithSign();
        testDecodeInvalidFormat();
        testDecodeEmptyString();
    }

    static void testDecodeDecimal() {
        Integer result = Integer.decode("12345");
        assert result != null : "Result should not be null";
        assert result.equals(Integer.valueOf(12345)) : "Expected 12345, got " + result;
    }

    static void testDecodeHexadecimal() {
        Integer resultLower = Integer.decode("0x1a");
        Integer resultUpper = Integer.decode("0X1A");
        Integer resultHash = Integer.decode("#1a");
        assert resultLower.equals(Integer.valueOf(26)) : "Expected 26, got " + resultLower;
        assert resultUpper.equals(Integer.valueOf(26)) : "Expected 26, got " + resultUpper;
        assert resultHash.equals(Integer.valueOf(26)) : "Expected 26, got " + resultHash;
    }

    static void testDecodeOctal() {
        Integer result = Integer.decode("077");
        assert result.equals(Integer.valueOf(63)) : "Expected 63, got " + result;
    }

    static void testDecodeWithSign() {
        Integer positive = Integer.decode("+123");
        Integer negative = Integer.decode("-123");
        assert positive.equals(Integer.valueOf(123)) : "Expected 123, got " + positive;
        assert negative.equals(Integer.valueOf(-123)) : "Expected -123, got " + negative;
    }

    static void testDecodeInvalidFormat() {
        try {
            Integer.decode("--123");
            assert false : "Expected NumberFormatException for '--123'";
        } catch (NumberFormatException e) {
            // expected
        }
        try {
            Integer.decode("0xGHI");
            assert false : "Expected NumberFormatException for '0xGHI'";
        } catch (NumberFormatException e) {
            // expected
        }
        try {
            Integer.decode("");
            assert false : "Expected NumberFormatException for empty string";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeEmptyString() {
        try {
            Integer.decode("");
            assert false : "Expected NumberFormatException for empty string";
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
