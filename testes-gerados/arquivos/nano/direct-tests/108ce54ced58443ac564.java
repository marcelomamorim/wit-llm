/* @test    @summary Test Base64.getMimeEncoder(int, byte[]) for valid and invalid inputs    @run main Base64WitupTest */

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Arrays;

public class Base64WitupTest {

    public static void main(String[] args) {
        testValidLineSeparator();
        testNullLineSeparator();
        testInvalidLineSeparatorCharacter();
        testLineLengthZero();
        testNegativeLineLength();
    }

    static void testValidLineSeparator() {
        byte[] separator = { '\r', '\n' };
        int lineLength = 76;
        Encoder encoder = Base64.getMimeEncoder(lineLength, separator);
        byte[] encoded = encoder.encode("test".getBytes(StandardCharsets.UTF_8));
        String encodedStr = new String(encoded, StandardCharsets.US_ASCII);
        // Basic check: encoded string should contain only base64 characters
        assert encodedStr.matches("[A-Za-z0-9+/=\r\n]+") : "Encoded string contains invalid characters";
    }

    static void testNullLineSeparator() {
        try {
            Base64.getMimeEncoder(76, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidLineSeparatorCharacter() {
        byte[] invalidSeparator = { 0x00 };
        try {
            Base64.getMimeEncoder(76, invalidSeparator);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Illegal base64 line separator character") : "Unexpected exception message";
        }
    }

    static void testLineLengthZero() {
        Encoder encoder = Base64.getMimeEncoder(0, new byte[] { '\r', '\n' });
        byte[] encoded = encoder.encode("test".getBytes(StandardCharsets.UTF_8));
        String encodedStr = new String(encoded, StandardCharsets.US_ASCII);
        assert encodedStr.matches("[A-Za-z0-9+/=\r\n]+") : "Encoded string contains invalid characters";
    }

    static void testNegativeLineLength() {
        Encoder encoder = Base64.getMimeEncoder(-1, new byte[] { '\r', '\n' });
        byte[] encoded = encoder.encode("test".getBytes(StandardCharsets.UTF_8));
        String encodedStr = new String(encoded, StandardCharsets.US_ASCII);
        assert encodedStr.matches("[A-Za-z0-9+/=\r\n]+") : "Encoded string contains invalid characters";
    }
}
