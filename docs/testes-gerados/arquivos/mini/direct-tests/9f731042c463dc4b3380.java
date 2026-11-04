/*
 * @test
 * @summary Test String constructor String(byte[], Charset) for basic correctness
 * @run main StringWitupTest
 */

import java.lang.String;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringWitupTest {
    public static void main(String[] args) {
        // Test with ASCII bytes and UTF-8 charset
        byte[] asciiBytes = {65, 66, 67}; // 'A', 'B', 'C'
        String s1 = new String(asciiBytes, StandardCharsets.UTF_8);
        if (!s1.equals("ABC")) {
            throw new RuntimeException("Failed: expected 'ABC' but got '" + s1 + "'");
        }

        // Test with empty byte array
        byte[] emptyBytes = new byte[0];
        String s2 = new String(emptyBytes, StandardCharsets.UTF_8);
        if (!s2.equals("")) {
            throw new RuntimeException("Failed: expected empty string but got '" + s2 + "'");
        }

        // Test with non-ASCII UTF-8 bytes (e.g., Euro sign U+20AC)
        byte[] euroBytes = {(byte)0xE2, (byte)0x82, (byte)0xAC};
        String s3 = new String(euroBytes, StandardCharsets.UTF_8);
        if (!s3.equals("€")) {
            throw new RuntimeException("Failed: expected '€' but got '" + s3 + "'");
        }

        // Test with null Charset should throw NullPointerException
        boolean npeThrown = false;
        try {
            new String(asciiBytes, null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Failed: expected NullPointerException when charset is null");
        }
    }
}
