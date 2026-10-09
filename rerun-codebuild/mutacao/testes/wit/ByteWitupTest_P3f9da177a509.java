/* @test    @summary Test for Byte.decode(String) method to verify exception handling and normal behavior    @run main ByteWitupTest_P3f9da177a509 */

import java.lang.Byte;

public class ByteWitupTest_P3f9da177a509 {

    public static void main(String[] args) {
        testDecodeValidInput();
        testDecodeOutOfRange();
        testDecodeInvalidFormat();
    }

    private static void testDecodeValidInput() {
        String validStr = "127";
        Byte result = Byte.decode(validStr);
        if (result == null || result.byteValue() != 127) {
            throw new AssertionError("Failed to decode valid input: " + validStr);
        }
        validStr = "-128";
        result = Byte.decode(validStr);
        if (result == null || result.byteValue() != -128) {
            throw new AssertionError("Failed to decode valid input: " + validStr);
        }
        validStr = "0";
        result = Byte.decode(validStr);
        if (result == null || result.byteValue() != 0) {
            throw new AssertionError("Failed to decode valid input: " + validStr);
        }
    }

    private static void testDecodeOutOfRange() {
        String outOfRangeHigh = "128";
        String outOfRangeLow = "-129";
        try {
            Byte.decode(outOfRangeHigh);
            throw new AssertionError("Expected NumberFormatException for input: " + outOfRangeHigh);
        } catch (NumberFormatException e) {
            // Expected exception
        }
        try {
            Byte.decode(outOfRangeLow);
            throw new AssertionError("Expected NumberFormatException for input: " + outOfRangeLow);
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private static void testDecodeInvalidFormat() {
        String invalidStr1 = "abc";
        String invalidStr2 = "12.3";
        String invalidStr3 = "";
        String[] invalidInputs = {invalidStr1, invalidStr2, invalidStr3};
        for (String input : invalidInputs) {
            try {
                Byte.decode(input);
                throw new AssertionError("Expected NumberFormatException for input: " + input);
            } catch (NumberFormatException e) {
                // Expected exception
            }
        }
    }
}
