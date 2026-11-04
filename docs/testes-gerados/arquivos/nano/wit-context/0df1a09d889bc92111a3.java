/* @test    @summary Test for InflaterOutputStream.flush() handling of closed stream    @run main InflaterOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.ZipException;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) {
        testFlushOnClosedStream();
    }

    public static void testFlushOnClosedStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        InflaterOutputStream ios = null;
        try {
            ios = new InflaterOutputStream(baos, inflater);
            // Close the stream to set the closed flag
            ios.close();
            // Attempt to flush after close, expecting an IOException
            try {
                ios.flush();
                // If no exception, the test fails
                throw new AssertionError("Expected IOException not thrown on flush after close");
            } catch (IOException e) {
                // Verify that the exception message indicates stream is closed
                if (!"Stream closed".equals(e.getMessage())) {
                    throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
                }
                // Test passes if IOException with message "Stream closed" is thrown
            }
        } finally {
            if (ios != null) {
                try {
                    ios.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }
}
