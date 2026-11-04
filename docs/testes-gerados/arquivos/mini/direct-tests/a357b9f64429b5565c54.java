/*
 * @test
 * @summary Test Base64.Encoder.wrap(InputStream) method behavior
 * @run main Base64WitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testWrapWithValidInputStream();
        testWrapWithNullInputStream();
    }

    private static void testWrapWithValidInputStream() throws IOException {
        byte[] inputBytes = "SGVsbG8gV29ybGQh".getBytes("US-ASCII"); // "Hello World!" base64 encoded
        InputStream bais = new ByteArrayInputStream(inputBytes);

        Base64.Decoder decoder = Base64.getDecoder();
        InputStream wrappedStream = decoder.wrap(bais);

        Objects.requireNonNull(wrappedStream);

        byte[] decoded = new byte[12];
        int readBytes = wrappedStream.read(decoded);

        if (readBytes != 12) {
            throw new RuntimeException("Expected 12 bytes decoded, got " + readBytes);
        }

        String decodedString = new String(decoded, "US-ASCII");
        if (!"Hello World!".equals(decodedString)) {
            throw new RuntimeException("Decoded string mismatch: " + decodedString);
        }

        wrappedStream.close();
    }

    private static void testWrapWithNullInputStream() {
        try {
            Base64.getDecoder().wrap(null);
            throw new RuntimeException("Expected NullPointerException for null InputStream");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
