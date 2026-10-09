/* @test    @summary Test Short.decode(String) for valid and invalid inputs    @run main ShortWitupTest_Pbb306d8ee7a7 */

import java.lang.Short;

public class ShortWitupTest_Pbb306d8ee7a7 {
    public static void main(String[] args) {
        testDecodeValidInputs();
        testDecodeInvalidInputs();
    }

    private static void testDecodeValidInputs() {
        try {
            // Valid decimal string
            Short s1 = Short.decode("12345");
            if (s1 == null || s1.shortValue() != 12345) {
                throw new AssertionError("Failed to decode valid decimal string");
            }
            // Valid negative decimal string
            Short s2 = Short.decode("-12345");
            if (s2 == null || s2.shortValue() != -12345) {
                throw new AssertionError("Failed to decode valid negative decimal string");
            }
            // Valid hexadecimal string with 0x prefix
            Short s3 = Short.decode("0x7FFF");
            if (s3 == null || s3.shortValue() != 32767) {
                throw new AssertionError("Failed to decode valid hex string with 0x prefix");
            }
            // Valid hexadecimal string with 0X prefix
            Short s4 = Short.decode("0X8000");
            if (s4 == null || s4.shortValue() != -32768) {
                throw new AssertionError("Failed to decode valid hex string with 0X prefix");
            }
            // Valid octal string
            Short s5 = Short.decode("077777");
            if (s5 == null || s5.shortValue() != -1) {
                throw new AssertionError("Failed to decode valid octal string");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid inputs: " + e);
        }
    }

    private static void testDecodeInvalidInputs() {
        String[] invalidInputs = {"", "abc", "0xGHI", "0x", "-0x1", "07778", null};
        for (String input : invalidInputs) {
            try {
                Short.decode(input);
                throw new AssertionError("Expected NumberFormatException for input: " + input);
            } catch (NumberFormatException e) {
                // Expected exception
            } catch (NullPointerException e) {
                if (input != null) {
                    throw new AssertionError("Unexpected NullPointerException for input: " + input);
                }
                // Null input should throw NullPointerException or NumberFormatException
            }
        }
    }
}
