/* @test    @summary Test for PushbackReader.mark method to verify IOException is thrown as expected    @run main PushbackReaderWitupTest */

import java.io.PushbackReader;
import java.io.StringReader;
import java.io.IOException;

public class PushbackReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testMarkThrowsIOException();
    }

    private static void testMarkThrowsIOException() {
        StringReader sr = new StringReader("test");
        PushbackReader pbr = new PushbackReader(sr);
        try {
            pbr.mark(10);
            // If no exception, fail the test
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Verify that the exception message matches expected
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage(), e);
            }
            // Test passes if IOException is thrown with correct message
        }
    }
}
