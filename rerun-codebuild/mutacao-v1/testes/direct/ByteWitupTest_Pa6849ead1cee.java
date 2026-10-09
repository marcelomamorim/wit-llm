/* @test    @summary Test Byte.decode(String) for valid and invalid inputs    @run main ByteWitupTest_Pa6849ead1cee */

import java.lang.Byte;

public class ByteWitupTest_Pa6849ead1cee {
    public static void main(String[] args) {
        testDecodeValidInputs();
        testDecodeInvalidInputs();
    }

    static void testDecodeValidInputs() {
        try {
            Byte b1 = Byte.decode("127");
            if (b1 == null || b1.byteValue() != 127) {
                throw new AssertionError("Failed to decode '127'");
            }
            Byte b2 = Byte.decode("-128");
            if (b2 == null || b2.byteValue() != -128) {
                throw new AssertionError("Failed to decode '-128'");
            }
            Byte b3 = Byte.decode("0");
            if (b3 == null || b3.byteValue() != 0) {
                throw new AssertionError("Failed to decode '0'");
            }
            Byte b4 = Byte.decode("+42");
            if (b4 == null || b4.byteValue() != 42) {
                throw new AssertionError("Failed to decode '+42'");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid inputs", e);
        }
    }

    static void testDecodeInvalidInputs() {
        String[] invalidInputs = {"128", "-129", "abc", "", "+128", "-129"};
        for (String input : invalidInputs) {
            try {
                Byte.decode(input);
                throw new AssertionError("Expected NumberFormatException for input: " + input);
            } catch (NumberFormatException e) {
                // Expected exception, test passes for this input
            } catch (Exception e) {
                throw new RuntimeException("Unexpected exception type for input: " + input, e);
            }
        }
    }
}
