/*
 * @test
 * @summary Test java.util.Base64.wrap(InputStream) method for null input and normal wrapping
 * @run main Base64WitupTest
 */

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class Base64WitupTest {
    public static void main(String[] args) throws IOException {
        testWrapNullInput();
        testWrapValidInputStream();
    }

    private static void testWrapNullInput() {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            decoder.wrap(null);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testWrapValidInputStream() throws IOException {
        byte[] data = "test".getBytes("UTF-8");
        InputStream original = new ByteArrayInputStream(data);
        Base64.Decoder decoder = Base64.getDecoder();
        InputStream wrapped = decoder.wrap(original);
        if (wrapped == null) {
            throw new AssertionError("wrap() returned null InputStream");
        }
        // Read from wrapped stream and verify it does not throw and returns bytes
        int firstByte = wrapped.read();
        if (firstByte == -1) {
            throw new AssertionError("Wrapped InputStream is empty, expected data");
        }
        wrapped.close();
    }
}
