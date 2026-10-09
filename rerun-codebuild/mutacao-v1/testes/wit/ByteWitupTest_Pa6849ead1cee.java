/* @test    @summary Test for Byte.decode(String) method handling valid and invalid inputs    @run main ByteWitupTest_Pa6849ead1cee */

import java.lang.Byte;

public class ByteWitupTest_Pa6849ead1cee {

    public static void main(String[] args) {
        testDecodeValidInputs();
        testDecodeInvalidInputs();
    }

    private static void testDecodeValidInputs() {
        try {
            // Valid decimal string within byte range
            Byte b1 = Byte.decode("127");
            if (b1 == null || b1.byteValue() != 127) {
                throw new AssertionError("Failed to decode '127'");
            }
            // Valid decimal string within byte range
            Byte b2 = Byte.decode("-128");
            if (b2 == null || b2.byteValue() != -128) {
                throw new AssertionError("Failed to decode '-128'");
            }
            // Valid hexadecimal string
            Byte b3 = Byte.decode("0x7F");
            if (b3 == null || b3.byteValue() != 127) {
                throw new AssertionError("Failed to decode '0x7F'");
            }
            // Valid hexadecimal string with uppercase
            Byte b4 = Byte.decode("0X80");
            if (b4 == null || b4.byteValue() != -128) {
                throw new AssertionError("Failed to decode '0X80'");
            }
            // Valid octal string
            Byte b5 = Byte.decode("0177");
            if (b5 == null || b5.byteValue() != 127) {
                throw new AssertionError("Failed to decode '0177'");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid inputs: " + e);
        }
    }

    private static void testDecodeInvalidInputs() {
        String[] invalidInputs = {"abc", "0xG", "999", "-999", "0xZZ", "0777", ""};
        for (String input : invalidInputs) {
            try {
                Byte.decode(input);
                throw new AssertionError("Expected NumberFormatException for input: " + input);
            } catch (NumberFormatException e) {
                // Expected exception, test passes for this input
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception type for input: " + input + ": " + e);
            }
        }
    }
}
