/* @test    @summary Test decode method of Long class    @run main LongWitupTest_Pd65eb76f3c0f */

import java.lang.Long;

public class LongWitupTest_Pd65eb76f3c0f {

    public static void main(String[] args) {
        testDecodeHexadecimal();
        testDecodeOctal();
        testDecodeDecimal();
        testDecodeNegative();
        testDecodeWithSign();
        testDecodeInvalidSignPosition();
        testDecodeEmptyString();
        testDecodeInvalidNumber();
        System.out.println("All tests passed.");
    }

    static void testDecodeHexadecimal() {
        Long result = Long.decode("0x10");
        assert result.equals(Long.valueOf(16L)) : "Failed decode hexadecimal with 0x";
        result = Long.decode("0XFF");
        assert result.equals(Long.valueOf(255L)) : "Failed decode hexadecimal with 0X";
        result = Long.decode("#1A");
        assert result.equals(Long.valueOf(26L)) : "Failed decode hexadecimal with #";
        result = Long.decode("0xFFFFFFFFFFFFFFFF");
        assert result.equals(Long.valueOf(-1L)) : "Failed decode max unsigned long as signed";
    }

    static void testDecodeOctal() {
        Long result = Long.decode("077");
        assert result.equals(Long.valueOf(63L)) : "Failed decode octal";
        result = Long.decode("010");
        assert result.equals(Long.valueOf(8L)) : "Failed decode octal with leading zero";
    }

    static void testDecodeDecimal() {
        Long result = Long.decode("12345");
        assert result.equals(Long.valueOf(12345L)) : "Failed decode decimal";
    }

    static void testDecodeNegative() {
        Long result = Long.decode("-42");
        assert result.equals(Long.valueOf(-42L)) : "Failed decode negative decimal";
    }

    static void testDecodeWithSign() {
        Long positive = Long.decode("+100");
        assert positive.equals(Long.valueOf(100L)) : "Failed decode with plus sign";
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
            Long.decode("notANumber");
            assert false : "Expected NumberFormatException for invalid number";
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
