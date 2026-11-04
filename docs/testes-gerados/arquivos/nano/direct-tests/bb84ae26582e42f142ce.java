/* @test    @summary Test java.io.Writer.write(char[] cbuf, int off, int len) method    @run main WriterWitupTest */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {

    public static void main(String[] args) {
        testWriteValidRange();
        testWriteInvalidRange();
    }

    static void testWriteValidRange() {
        char[] buffer = {'a', 'b', 'c', 'd', 'e'};
        Writer writer = new Writer() {
            boolean open = true;
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, cbuf.length);
                ensureOpen();
            }
            private void ensureOpen() throws IOException {
                if (!open) {
                    throw new IOException("Stream closed");
                }
            }
            @Override
            public void close() throws IOException {
                open = false;
            }
            @Override
            public void flush() throws IOException {
                ensureOpen();
            }
        };
        try {
            writer.write(buffer, 1, 3); // should succeed
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid range", e);
        }
    }

    static void testWriteInvalidRange() {
        char[] buffer = {'a', 'b', 'c'};
        Writer writer = new Writer() {
            boolean open = true;
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, cbuf.length);
                ensureOpen();
            }
            private void ensureOpen() throws IOException {
                if (!open) {
                    throw new IOException("Stream closed");
                }
            }
            @Override
            public void close() throws IOException {
                open = false;
            }
            @Override
            public void flush() throws IOException {
                ensureOpen();
            }
        };
        boolean exceptionThrown = false;
        try {
            writer.write(buffer, 2, 5); // should throw IndexOutOfBoundsException due to checkFromIndexSize
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in invalid range", e);
        }
        if (!exceptionThrown) {
            throw new RuntimeException("Expected IndexOutOfBoundsException was not thrown");
        }
    }
}
