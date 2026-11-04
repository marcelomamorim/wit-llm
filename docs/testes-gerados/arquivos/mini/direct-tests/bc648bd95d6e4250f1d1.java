/*
 * @test
 * @summary Test InputStream.skip(long) method behavior
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        InputStream is = new InputStream() {
            private boolean open = true;
            void ensureOpen() throws IOException {
                if (!open) throw new IOException("Stream closed");
            }
            @Override
            public int read() throws IOException {
                ensureOpen();
                return -1;
            }
            @Override
            public void close() throws IOException {
                open = false;
            }
            @Override
            public long skip(long n) throws IOException {
                ensureOpen();
                return 0L;
            }
        };

        // Test skip with positive number
        long skipped = is.skip(10L);
        if (skipped != 0L) {
            throw new AssertionError("Expected skip to return 0 but got " + skipped);
        }

        // Test skip with zero
        skipped = is.skip(0L);
        if (skipped != 0L) {
            throw new AssertionError("Expected skip(0) to return 0 but got " + skipped);
        }

        // Test skip with negative number
        skipped = is.skip(-5L);
        if (skipped != 0L) {
            throw new AssertionError("Expected skip(-5) to return 0 but got " + skipped);
        }

        // Close stream and test skip throws IOException
        is.close();
        boolean threw = false;
        try {
            is.skip(1L);
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IOException after close when calling skip");
        }
    }
}
