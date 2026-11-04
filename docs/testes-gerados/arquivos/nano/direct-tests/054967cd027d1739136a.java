/* @test    @summary Test for KeepAliveStream.reset() method to ensure it throws IOException as specified.    @run main KeepAliveStreamWitupTest */

import java.io.*;

public class KeepAliveStreamWitupTest {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        KeepAliveStream kas = null;
        try {
            // Create a dummy InputStream for testing
            InputStream is = new ByteArrayInputStream(new byte[0]);
            // Instantiate KeepAliveStream with dummy parameters
            kas = new sun.net.www.http.KeepAliveStream(is, null, 0L, null);
        } catch (Exception e) {
            // If instantiation fails, fail the test
            throw new RuntimeException("Failed to instantiate KeepAliveStream", e);
        }
        // Test that reset() throws IOException
        try {
            kas.reset();
            // If no exception thrown, test fails
            throw new AssertionError("Expected IOException was not thrown by reset()");
        } catch (IOException e) {
            // Expected exception, test passes
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
