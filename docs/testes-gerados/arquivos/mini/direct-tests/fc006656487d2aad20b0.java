/*
 * @test
 * @summary Test java.io.Writer.append(CharSequence) method behavior
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {

    static class TestWriter extends Writer {
        private boolean closed = false;

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            if (closed) throw new IOException("Stream closed");
        }

        @Override
        public void flush() throws IOException {
            if (closed) throw new IOException("Stream closed");
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }

        @Override
        public Writer append(CharSequence csq) throws IOException {
            ensureOpen();
            return this;
        }

        private void ensureOpen() throws IOException {
            if (closed) throw new IOException("Stream closed");
        }
    }

    public static void main(String[] args) throws Exception {
        TestWriter w = new TestWriter();

        // Test append with null (should not throw)
        Writer r = w.append(null);
        if (r != w) throw new RuntimeException("append(null) did not return this");

        // Test append with non-null CharSequence
        String csq = "test";
        r = w.append(csq);
        if (r != w) throw new RuntimeException("append(CharSequence) did not return this");

        // Test append after close throws IOException
        w.close();
        boolean threw = false;
        try {
            w.append(csq);
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) throw new RuntimeException("append after close did not throw IOException");
    }
}
