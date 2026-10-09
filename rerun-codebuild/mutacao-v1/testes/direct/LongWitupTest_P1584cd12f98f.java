/* @test    @summary Test Long.decode with various valid and invalid inputs    @run main LongWitupTest_P1584cd12f98f */

import java.lang.Long;

public class LongWitupTest_P1584cd12f98f {
    public static void main(String[] args) {
        testDecodeValidHex();
        testDecodeValidOctal();
        testDecodeValidDecimal();
        testDecodeWithSign();
        testDecodeInvalidSignPosition();
        testDecodeEmptyString();
        testDecodeInvalidNumberFormat();
        System.out.println("All tests passed.");
    }

    static void testDecodeValidHex() {
        Long result = Long.decode("0x10");
        if (result == null || result.longValue() != 16L) {
            throw new AssertionError("Failed: decode '0x10'");
        }
        result = Long.decode("0XFF");
        if (result == null || result.longValue() != 255L) {
            throw new AssertionError("Failed: decode '0XFF'");
        }
        result = Long.decode("#1A");
        if (result == null || result.longValue() != 26L) {
            throw new AssertionError("Failed: decode '#1A'");
        }
    }

    static void testDecodeValidOctal() {
        Long result = Long.decode("077");
        if (result == null || result.longValue() != 63L) {
            throw new AssertionError("Failed: decode '077'");
        }
    }

    static void testDecodeValidDecimal() {
        Long result = Long.decode("12345");
        if (result == null || result.longValue() != 12345L) {
            throw new AssertionError("Failed: decode '12345'");
        }
    }

    static void testDecodeWithSign() {
        Long result = Long.decode("-42");
        if (result == null || result.longValue() != -42L) {
            throw new AssertionError("Failed: decode '-42'");
        }
        result = Long.decode("+42");
        if (result == null || result.longValue() != 42L) {
            throw new AssertionError("Failed: decode '+42'");
        }
    }

    static void testDecodeInvalidSignPosition() {
        try {
            Long.decode("--1");
            throw new AssertionError("Expected NumberFormatException for '--1'");
        } catch (NumberFormatException e) {
            // expected
        }
        try {
            Long.decode("+ -1");
            throw new AssertionError("Expected NumberFormatException for '+ -1'");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeEmptyString() {
        try {
            Long.decode("");
            throw new AssertionError("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeInvalidNumberFormat() {
        try {
            Long.decode("abc");
            throw new AssertionError("Expected NumberFormatException for 'abc'");
        } catch (NumberFormatException e) {
            // expected
        }
        try {
            Long.decode("0xG");
            throw new AssertionError("Expected NumberFormatException for '0xG'");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
