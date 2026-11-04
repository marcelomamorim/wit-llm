/*
 * @test
 * @summary Test Base64.getMimeEncoder(int, byte[]) for valid and invalid inputs
 * @run main Base64WitupTest
 */

import java.util.Base64;
import java.util.Objects;

public class Base64WitupTest {
    public static void main(String[] args) {
        testNullLineSeparator();
        testLineSeparatorWithBase64Char();
        testLineLengthRoundingAndReturnEncoder();
    }

    private static void testNullLineSeparator() {
        try {
            Base64.getMimeEncoder(76, null);
            throw new RuntimeException("Expected NullPointerException for null lineSeparator");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testLineSeparatorWithBase64Char() {
        // base64 chars include 'A' (0x41), so lineSeparator containing 0x41 should throw
        byte[] invalidSeparator = new byte[] { 0x41 };
        try {
            Base64.getMimeEncoder(76, invalidSeparator);
            throw new RuntimeException("Expected IllegalArgumentException for lineSeparator containing base64 char");
        } catch (IllegalArgumentException iae) {
            String msg = iae.getMessage();
            if (!msg.contains("Illegal base64 line separator character")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testLineLengthRoundingAndReturnEncoder() {
        // lineLength <= 0 returns Encoder.RFC4648
        Base64.Encoder enc1 = Base64.getMimeEncoder(0, new byte[] { '\r', '\n' });
        if (enc1 != Base64.Encoder.RFC4648) {
            throw new RuntimeException("Expected Encoder.RFC4648 for lineLength=0");
        }
        Base64.Encoder enc2 = Base64.getMimeEncoder(-4, new byte[] { '\r', '\n' });
        if (enc2 != Base64.Encoder.RFC4648) {
            throw new RuntimeException("Expected Encoder.RFC4648 for negative lineLength");
        }

        // lineLength rounded down to multiple of 4
        // Use a valid lineSeparator (not containing base64 chars)
        byte[] validSeparator = new byte[] { 0x0D, 0x0A }; // CR LF
        Base64.Encoder enc3 = Base64.getMimeEncoder(78, validSeparator);
        if (enc3 == Base64.Encoder.RFC4648) {
            throw new RuntimeException("Expected new Encoder instance for positive lineLength rounded down");
        }

        // lineLength 78 rounded down to 76 internally, but no direct way to check lineLength
        // Just check that returned encoder is not RFC4648
    }
}
