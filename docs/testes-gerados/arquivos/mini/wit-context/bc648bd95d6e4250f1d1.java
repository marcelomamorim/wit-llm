/*
 * @test
 * @summary Test InputStream.skip(long) behavior including IOException on closed stream
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSkipOnOpenStream();
        testSkipOnClosedStreamThrowsIOException();
    }

    private static void testSkipOnOpenStream() throws IOException {
        InputStream is = InputStream.nullInputStream();
        // skip should return 0 on open nullInputStream
        long skipped = is.skip(10L);
        if (skipped != 0L) {
            throw new AssertionError("Expected skip to return 0 but got " + skipped);
        }
        is.close();
    }

    private static void testSkipOnClosedStreamThrowsIOException() {
        InputStream is = InputStream.nullInputStream();
        try {
            is.close();
            is.skip(1L);
            throw new AssertionError("Expected IOException on skip after close");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new AssertionError("IOException message mismatch: " + msg);
            }
        }
    }
}
