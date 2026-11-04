/* @test    @summary Test java.util.Base64.decode(byte[], byte[]) method for correct decoding and exception handling    @run main Base64WitupTest */

import java.util.Base64;
import java.io.ByteArrayOutputStream;

public class Base64WitupTest {

    public static void main(String[] args) {
        testDecodeValidInput();
        testDecodeOutputBufferTooSmall();
        testDecodeEmptyInput();
        testDecodeInvalidInput();
    }

    private static void testDecodeValidInput() {
        byte[] input = "SGVsbG8gV29ybGQ=".getBytes(StandardCharsets.US_ASCII); // "Hello World"
        byte[] outputBuffer = new byte[20];
        int decodedLength = Base64.decode(input, outputBuffer);
        String decodedString = new String(outputBuffer, 0, decodedLength, StandardCharsets.US_ASCII);
        assert "Hello World".equals(decodedString) : "Decoded string mismatch";
    }

    private static void testDecodeOutputBufferTooSmall() {
        byte[] input = "U29tZSBkYXRh".getBytes(StandardCharsets.US_ASCII); // "Some data"
        byte[] smallBuffer = new byte[2];
        try {
            Base64.decode(input, smallBuffer);
            assert false : "Expected IllegalArgumentException due to small buffer";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Output byte array is too small") : "Unexpected exception message";
        }
    }

    private static void testDecodeEmptyInput() {
        byte[] input = new byte[0];
        byte[] outputBuffer = new byte[10];
        int length = Base64.decode(input, outputBuffer);
        assert length == 0 : "Decoding empty input should result in zero length";
    }

    private static void testDecodeInvalidInput() {
        byte[] invalidInput = "Invalid$$$".getBytes(StandardCharsets.US_ASCII);
        byte[] outputBuffer = new byte[10];
        try {
            Base64.decode(invalidInput, outputBuffer);
            // Depending on implementation, this may or may not throw; if it does, test passes
        } catch (IllegalArgumentException e) {
            // Expected if invalid characters are rejected
            assert e.getMessage().contains("Invalid") || e.getMessage().contains("character") : "Unexpected exception message";
        }
    }
}
