/* @test    @summary Tests for java.lang.Short.decode(String) method behavior and exception paths    @run main ShortWitupTest */

import java.lang.Short;

public class ShortWitupTest {

    public static void main(String[] args) {
        testDecodeValidValues();
        testDecodeOutOfRangeValues();
        testDecodeInvalidFormat();
    }

    private static void testDecodeValidValues() {
        try {
            Short s1 = Short.decode("32767");
            assert s1 != null && s1.shortValue() == 32767 : "Failed to decode max short value";
            Short s2 = Short.decode("-32768");
            assert s2 != null && s2.shortValue() == -32768 : "Failed to decode min short value";
            Short s3 = Short.decode("0");
            assert s3 != null && s3.shortValue() == 0 : "Failed to decode zero";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid values test", e);
        }
    }

    private static void testDecodeOutOfRangeValues() {
        try {
            // Value greater than MAX_VALUE (32767)
            Short.decode("32768");
            throw new AssertionError("Expected NumberFormatException for value > MAX_VALUE");
        } catch (NumberFormatException e) {
            // Expected exception
        }
        try {
            // Value less than MIN_VALUE (-32768)
            Short.decode("-32769");
            throw new AssertionError("Expected NumberFormatException for value < MIN_VALUE");
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private static void testDecodeInvalidFormat() {
        String[] invalidInputs = {"abc", "12.34", "", null};
        for (String input : invalidInputs) {
            try {
                Short.decode(input);
                if (input == null || input.isEmpty()) {
                    throw new AssertionError("Expected NullPointerException or NumberFormatException for input: " + input);
                } else {
                    throw new AssertionError("Expected NumberFormatException for input: " + input);
                }
            } catch (NumberFormatException | NullPointerException e) {
                // Expected exception
            }
        }
    }
}
