/* @test    @summary Test for java.util.Base64.available() method.    @run main Base64WitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) {
        testAvailableWithOpenStream();
        testAvailableAfterClose();
    }

    static void testAvailableWithOpenStream() {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(data);
        ByteArrayInputStream encodedStream = new ByteArrayInputStream(encodedBytes);
        try {
            int available = encodedStream.available();
            if (available != encodedBytes.length) {
                throw new AssertionError("Expected available() to return " + encodedBytes.length + " but got " + available);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    static void testAvailableAfterClose() {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(data);
        ByteArrayInputStream encodedStream = new ByteArrayInputStream(encodedBytes);
        try {
            encodedStream.close();
            encodedStream.available();
            throw new AssertionError("Expected IOException after close was not thrown.");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
