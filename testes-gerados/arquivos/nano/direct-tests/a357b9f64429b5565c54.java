/* @test    @summary Test wrapping InputStream with null and valid InputStream    @run main Base64WitupTest */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) {
        testWrapWithNullInput();
        testWrapWithValidInput();
    }

    static void testWrapWithNullInput() {
        try {
            Base64.wrap(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testWrapWithValidInput() {
        String original = "Test data for Base64 wrap";
        byte[] bytes = original.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        InputStream wrappedStream = Base64.wrap(inputStream);
        try {
            byte[] buffer = new byte[1024];
            int readBytes = wrappedStream.read(buffer);
            String result = new String(buffer, 0, readBytes, java.nio.charset.StandardCharsets.UTF_8);
            if (!result.equals(original)) {
                System.err.println("Wrapped stream data does not match original");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Exception during reading wrapped stream: " + e);
            System.exit(1);
        }
    }
}
