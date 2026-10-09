/* @test    @summary Tests for java.lang.Byte.decode(String) to verify exception paths and behavior.    @run main ByteWitupTest_P0d7728e13f2e */

import java.lang.Byte;

public class ByteWitupTest_P0d7728e13f2e {

    public static void main(String[] args) {
        testDecodeValidRange();
        testDecodeBelowRange();
        testDecodeAboveRange();
        testDecodeInvalidFormat();
    }

    private static void testDecodeValidRange() {
        try {
            Byte b1 = Byte.decode("-128");
            Byte b2 = Byte.decode("127");
            assert b1 != null : "Decoding -128 should not be null";
            assert b2 != null : "Decoding 127 should not be null";
            assert b1.byteValue() == -128 : "Decoding -128 failed";
            assert b2.byteValue() == 127 : "Decoding 127 failed";
        } catch (NumberFormatException e) {
            assert false : "NumberFormatException should not be thrown for valid range";
        }
    }

    private static void testDecodeBelowRange() {
        try {
            Byte.decode("-129");
            assert false : "Expected NumberFormatException for value below MIN_VALUE";
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private static void testDecodeAboveRange() {
        try {
            Byte.decode("128");
            assert false : "Expected NumberFormatException for value above MAX_VALUE";
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private static void testDecodeInvalidFormat() {
        String[] invalidInputs = {"abc", "12.34", "", " "};
        for (String input : invalidInputs) {
            try {
                Byte.decode(input);
                assert false : "Expected NumberFormatException for invalid input: " + input;
            } catch (NumberFormatException e) {
                // Expected exception
            }
        }
    }
}
