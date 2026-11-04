/*
 * @test
 * @summary Test java.io.Writer.write(String) method behavior
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;
import java.util.Objects;

public class WriterWitupTest {

    static class TestWriter extends Writer {
        private boolean open = true;

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            if (!open) throw new IOException("Stream closed");
        }

        @Override
        public void flush() throws IOException {
            if (!open) throw new IOException("Stream closed");
        }

        @Override
        public void close() throws IOException {
            open = false;
        }

        void ensureOpen() throws IOException {
            if (!open) throw new IOException("Stream closed");
        }

        @Override
        public void write(String str) throws IOException {
            Objects.requireNonNull(str);
            ensureOpen();
        }
    }

    public static void main(String[] args) throws Exception {
        TestWriter w = new TestWriter();

        // Test null argument throws NullPointerException
        try {
            w.write(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test writing a non-null string when open succeeds
        w.write("");
        w.write("test");

        // Test writing after close throws IOException
        w.close();
        try {
            w.write("fail");
            throw new RuntimeException("Expected IOException not thrown after close");
        } catch (IOException ioe) {
            // expected
        }
    }
}
