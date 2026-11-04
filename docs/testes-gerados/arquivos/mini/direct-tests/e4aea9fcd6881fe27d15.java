/*
 * @test
 * @summary Test Writer.write(int) method behavior
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal concrete subclass of Writer to test write(int)
        Writer w = new Writer() {
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
            protected void ensureOpen() throws IOException {
                if (closed) throw new IOException("Stream closed");
            }
        };

        // Test that write(int) does not throw when open
        w.write('a');
        w.write(0);
        w.write(65535);

        // Close and test that write(int) throws IOException
        w.close();
        boolean threw = false;
        try {
            w.write('a');
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IOException after close");
        }
    }
}
