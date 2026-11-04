/* @test    @summary Test the reset() method of PushbackInputStream to ensure it throws IOException as specified.    @run main PushbackInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.io.IOException;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        PushbackInputStream pushbackStream = new PushbackInputStream(byteStream);
        try {
            pushbackStream.reset();
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
            // Test passes if IOException with correct message is thrown
        }
    }
}
