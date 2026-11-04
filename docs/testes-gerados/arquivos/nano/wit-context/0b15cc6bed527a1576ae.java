/* @test    @summary Test for Base64.encode(byte[] src, byte[] dst) method to verify behavior with valid and invalid inputs.    @run main Base64WitupTest */

import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) {
        testEncodeWithValidInput();
        testEncodeWithSmallOutputBuffer();
        testEncodeWithNullInput();
        testEncodeWithNullOutput();
        testEncodeWithInsufficientOutputBuffer();
    }

    static void testEncodeWithValidInput() {
        byte[] input = "hello".getBytes(StandardCharsets.UTF_8);
        byte[] output = new byte[10];
        int result = Base64.getEncoder().encode(input, output);
        assert result > 0 : "Expected positive result for valid input";
        String encoded = new String(output, 0, result, StandardCharsets.UTF_8);
        assert encoded.equals("aGVsbG8=") : "Unexpected encoding result: " + encoded;
    }

    static void testEncodeWithSmallOutputBuffer() {
        byte[] input = "hello".getBytes(StandardCharsets.UTF_8);
        byte[] output = new byte[2];
        try {
            Base64.getEncoder().encode(input, output);
            assert false : "Expected IllegalArgumentException for small output buffer";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Output byte array is too small") : "Unexpected exception message: " + e.getMessage();
        }
    }

    static void testEncodeWithNullInput() {
        byte[] output = new byte[10];
        try {
            Base64.getEncoder().encode(null, output);
            assert false : "Expected NullPointerException for null input";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testEncodeWithNullOutput() {
        byte[] input = "hello".getBytes(StandardCharsets.UTF_8);
        try {
            Base64.getEncoder().encode(input, null);
            assert false : "Expected NullPointerException for null output";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testEncodeWithInsufficientOutputBuffer() {
        byte[] input = "hello".getBytes(StandardCharsets.UTF_8);
        byte[] output = new byte[4]; // intentionally too small
        try {
            Base64.getEncoder().encode(input, output);
            assert false : "Expected IllegalArgumentException for insufficient buffer";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Output byte array is too small") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
