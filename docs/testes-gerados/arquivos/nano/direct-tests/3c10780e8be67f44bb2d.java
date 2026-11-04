/* @test    @summary Test the reset() method of PushbackReader which throws IOException as specified.    @run main PushbackReaderWitupTest */

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        Reader reader = new StringReader("test");
        java.io.PushbackReader pushbackReader = new java.io.PushbackReader(reader);
        try {
            pushbackReader.reset();
            // If no exception is thrown, the test should fail
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Verify that the exception message matches the expected message
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
            // Test passes if IOException with correct message is thrown
        }
    }
}
