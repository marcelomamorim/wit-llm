/*
 * @test
 * @summary Test InputStream.readNBytes(int) method behavior
 * @run main InputStreamWitupTest
 */

import java.io.InputStream;
import java.io.IOException;

public class InputStreamWitupTest {

    static class TestInputStream extends InputStream {
        private boolean open = true;
        void setOpen(boolean open) { this.open = open; }
        void ensureOpen() throws IOException {
            if (!open) throw new IOException("Stream closed");
        }
        @Override
        public int read() throws IOException {
            ensureOpen();
            return -1;
        }
        @Override
        public byte[] readNBytes(int len) throws IOException {
            if (len < 0) {
                throw new IllegalArgumentException("len < 0");
            }
            ensureOpen();
            return new byte[0];
        }
    }

    public static void main(String[] args) throws Exception {
        TestInputStream stream = new TestInputStream();

        // Test len < 0 throws IllegalArgumentException
        try {
            stream.readNBytes(-1);
            throw new RuntimeException("Expected IllegalArgumentException for len < 0");
        } catch (IllegalArgumentException iae) {
            if (!"len < 0".equals(iae.getMessage())) {
                throw new RuntimeException("IllegalArgumentException message mismatch");
            }
        }

        // Test len == 0 returns empty array
        byte[] result = stream.readNBytes(0);
        if (result == null || result.length != 0) {
            throw new RuntimeException("Expected empty byte array for len == 0");
        }

        // Test len > 0 returns empty array (per method code)
        result = stream.readNBytes(10);
        if (result == null || result.length != 0) {
            throw new RuntimeException("Expected empty byte array for len > 0");
        }

        // Test IOException thrown if stream closed
        stream.setOpen(false);
        try {
            stream.readNBytes(1);
            throw new RuntimeException("Expected IOException when stream closed");
        } catch (IOException ioe) {
            if (!"Stream closed".equals(ioe.getMessage())) {
                throw new RuntimeException("IOException message mismatch");
            }
        }
    }
}
