/*
 * @test
 * @summary Tests for java.io.Writer.write(String, int, int) method.
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    public static void main(String[] args) {
        testWriteWithInvalidIndices();
        testWriteWhenClosed();
    }

    private static void testWriteWithInvalidIndices() {
        String testString = "Hello";
        Writer writer = new Writer() {
            private boolean closed = false;
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException { }
            @Override
            public void flush() throws IOException { }
            @Override
            public void close() throws IOException { this.closed = true; }
            private void ensureOpen() throws IOException {
                if (closed) throw new IOException("Stream closed");
            }
            public void write(String str, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, str.length());
                ensureOpen();
            }
        };
        try {
            writer.write(testString, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative off");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
        try {
            writer.write(testString, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative len");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
        try {
            writer.write(testString, 3, 3); // valid case
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in valid case", e);
        }
        try {
            writer.write(testString, 4, 2); // off+len > str.length()
            throw new AssertionError("Expected IndexOutOfBoundsException for off+len > str.length()");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testWriteWhenClosed() {
        String testString = "Hello";
        Writer writer = new Writer() {
            private boolean closed = false;
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException { }
            @Override
            public void flush() throws IOException { }
            @Override
            public void close() throws IOException { this.closed = true; }
            private void ensureOpen() throws IOException {
                if (closed) throw new IOException("Stream closed");
            }
            public void write(String str, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, str.length());
                ensureOpen();
            }
        };
        try {
            writer.close();
            writer.write(testString, 0, 1);
            throw new AssertionError("Expected IOException after close");
        } catch (IOException e) {
            // Expected
        }
    }
}
