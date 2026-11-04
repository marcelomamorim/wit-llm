/*
 * @test
 * @summary Test Base64.getMimeEncoder(int, byte[]) for null and invalid inputs
 * @run main Base64WitupTest
 */

import java.util.Base64;
import java.util.Objects;

public class Base64WitupTest {
    public static void main(String[] args) {
        testNullLineSeparator();
        testInvalidLineSeparatorChar();
        testLineLengthRoundingAndReturn();
    }

    private static void testNullLineSeparator() {
        try {
            Base64.getMimeEncoder(76, null);
            throw new RuntimeException("Expected NullPointerException for null lineSeparator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testInvalidLineSeparatorChar() {
        // lineSeparator contains a base64 alphabet character (e.g. 'A' = 65)
        byte[] invalidSeparator = new byte[] { 'A' };
        try {
            Base64.getMimeEncoder(76, invalidSeparator);
            throw new RuntimeException("Expected IllegalArgumentException for invalid lineSeparator char");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testLineLengthRoundingAndReturn() {
        // lineLength <= 0 returns Encoder.RFC4648
        Base64.Encoder enc1 = Base64.getMimeEncoder(0, new byte[] { '\r', '\n' });
        if (enc1 != Base64.Encoder.RFC4648) {
            throw new RuntimeException("Expected RFC4648 encoder for lineLength=0");
        }

        Base64.Encoder enc2 = Base64.getMimeEncoder(-4, new byte[] { '\r', '\n' });
        if (enc2 != Base64.Encoder.RFC4648) {
            throw new RuntimeException("Expected RFC4648 encoder for negative lineLength");
        }

        // lineLength rounded down to multiple of 4
        // test with lineLength=77 (rounded down to 76)
        byte[] sep = new byte[] { '\r', '\n' };
        Base64.Encoder enc3 = Base64.getMimeEncoder(77, sep);
        if (enc3 == Base64.Encoder.RFC4648) {
            throw new RuntimeException("Expected non-RFC4648 encoder for positive lineLength");
        }
    }
}
