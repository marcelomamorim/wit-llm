/* @test    @summary Test for Base64.decode(byte[] src, byte[] dst) method to verify behavior with valid and invalid inputs    @run main Base64WitupTest */

import java.util.Base64;
import java.io.IOException;

public class Base64WitupTest {

    public static void main(String[] args) {
        testDecodeValidInput();
        testDecodeWithSmallOutputBuffer();
        testDecodeWithEmptyInput();
    }

    private static void testDecodeValidInput() {
        byte[] input = {65, 66, 67}; // "ABC" in Base64
        byte[] outputBuffer = new byte[4];
        try {
            int result = Base64.getDecoder().decode(input, outputBuffer);
            boolean success = (result == 3);
            if (!success) {
                throw new AssertionError("Expected decoded length 3, but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid input test: " + e);
        }
    }

    private static void testDecodeWithSmallOutputBuffer() {
        byte[] input = {65, 66, 67}; // "ABC" in Base64
        byte[] smallBuffer = new byte[2]; // too small
        try {
            Base64.getDecoder().decode(input, smallBuffer);
            throw new AssertionError("Expected IllegalArgumentException due to small buffer, but no exception was thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testDecodeWithEmptyInput() {
        byte[] emptyInput = {};
        byte[] outputBuffer = new byte[10];
        try {
            int result = Base64.getDecoder().decode(emptyInput, outputBuffer);
            if (result != 0) {
                throw new AssertionError("Expected decoded length 0 for empty input, but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in empty input test: " + e);
        }
    }
}
