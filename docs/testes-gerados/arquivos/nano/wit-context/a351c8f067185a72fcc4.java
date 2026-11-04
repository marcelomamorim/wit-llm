/*
 * @test
 * @summary Test for Writer.write(String) method, focusing on exception paths.
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;
import java.util.Objects;

public class WriterWitupTest {

    public static void main(String[] args) {
        testWriteNullString();
        testWriteClosedWriter();
    }

    private static void testWriteNullString() {
        Writer writer = new Writer() {
            private boolean closed = false;
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException { }
            @Override
            public void flush() throws IOException { }
            @Override
            public void close() throws IOException { this.closed = true; }
            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }
            public void write(String str) throws IOException {
                Objects.requireNonNull(str);
                ensureOpen();
            }
        };
        try {
            writer.write(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            assert false : "Did not expect IOException";
        }
    }

    private static void testWriteClosedWriter() {
        Writer writer = new Writer() {
            private boolean closed = false;
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException { }
            @Override
            public void flush() throws IOException { }
            @Override
            public void close() throws IOException { this.closed = true; }
            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }
            public void write(String str) throws IOException {
                Objects.requireNonNull(str);
                ensureOpen();
            }
        };
        // Close the writer to trigger IOException
        try {
            writer.close();
            writer.write("test");
            assert false : "Expected IOException";
        } catch (IOException e) {
            // Expected
        }
    }
}
