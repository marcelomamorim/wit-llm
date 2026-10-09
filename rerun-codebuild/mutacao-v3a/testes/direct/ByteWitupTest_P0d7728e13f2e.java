/* @test    @summary Test Byte.decode with valid and invalid inputs    @run main ByteWitupTest_P0d7728e13f2e */

import java.lang.Byte;

public class ByteWitupTest_P0d7728e13f2e {
    public static void main(String[] args) {
        testDecodeValid();
        testDecodeInvalid();
    }

    private static void testDecodeValid() {
        // Test decoding decimal string
        Byte b1 = Byte.decode("127");
        assert b1.byteValue() == 127 : "Expected 127, got " + b1;

        // Test decoding negative decimal string
        Byte b2 = Byte.decode("-128");
        assert b2.byteValue() == -128 : "Expected -128, got " + b2;

        // Test decoding with plus sign
        Byte b3 = Byte.decode("+0");
        assert b3.byteValue() == 0 : "Expected 0, got " + b3;

        // Test decoding with radix prefix (hexadecimal)
        Byte b4 = Byte.decode("0x7F");
        assert b4.byteValue() == 127 : "Expected 127, got " + b4;

        // Test decoding with radix prefix (octal)
        Byte b5 = Byte.decode("077");
        assert b5.byteValue() == 63 : "Expected 63, got " + b5;
    }

    private static void testDecodeInvalid() {
        // Expect NumberFormatException for out of range value
        try {
            Byte.decode("128");
            assert false : "Expected NumberFormatException for 128";
        } catch (NumberFormatException e) {
            // Expected
        }
        try {
            Byte.decode("-129");
            assert false : "Expected NumberFormatException for -129";
        } catch (NumberFormatException e) {
            // Expected
        }
        // Invalid format string
        try {
            Byte.decode("abc");
            assert false : "Expected NumberFormatException for 'abc'";
        } catch (NumberFormatException e) {
            // Expected
        }
        // Invalid prefix
        try {
            Byte.decode("0xGHI");
            assert false : "Expected NumberFormatException for '0xGHI'";
        } catch (NumberFormatException e) {
            // Expected
        }
    }
}
