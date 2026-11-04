/*
 * @test
 * @summary Test java.util.Base64 available() method behavior
 * @run main Base64WitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Base64WitupTest {

    static class DummyInputStream extends InputStream {
        private int availableCount;
        DummyInputStream(int availableCount) {
            this.availableCount = availableCount;
        }
        @Override
        public int read() {
            return -1; // EOF
        }
        @Override
        public int available() {
            return availableCount;
        }
    }

    static class TestDecoder extends Base64.Decoder {
        boolean closed = false;
        InputStream is;
        TestDecoder(InputStream is) {
            this.is = is;
        }
        @Override
        public int available() throws IOException {
            if (closed)
                throw new IOException("Stream is closed");
            return is.available();
        }
        void close() {
            closed = true;
        }
    }

    public static void main(String[] args) throws Exception {
        // Setup dummy InputStream with known available count
        DummyInputStream dis = new DummyInputStream(42);
        TestDecoder decoder = new TestDecoder(dis);

        // Test available() returns the underlying InputStream.available()
        int avail = decoder.available();
        if (avail != 42) {
            throw new RuntimeException("available() returned " + avail + ", expected 42");
        }

        // Test available() throws IOException if closed
        decoder.close();
        boolean threw = false;
        try {
            decoder.available();
        } catch (IOException e) {
            if (!"Stream is closed".equals(e.getMessage())) {
                throw new RuntimeException("IOException message mismatch: " + e.getMessage());
            }
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("available() did not throw IOException when closed");
        }
    }
}
