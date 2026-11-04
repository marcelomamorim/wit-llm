/* @test    @summary Test for java.io.Reader.read(CharBuffer target) method.
 * @run main ReaderWitupTest
 */

import java.io.Reader;
import java.io.IOException;
import java.nio.CharBuffer;

public class ReaderWitupTest {

    public static void main(String[] args) {
        testReadWithNullTarget();
        testReadWhenClosed();
    }

    private static void testReadWithNullTarget() {
        Reader reader = new Reader() {
            private boolean closed = false;
            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }
            @Override
            public int read() throws IOException { return -1; }
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException { return -1; }
            @Override
            public int read(CharBuffer target) throws IOException {
                Objects.requireNonNull(target);
                ensureOpen();
                if (target.hasRemaining()) {
                    return -1;
                }
                return 0;
            }
            @Override
            public void close() throws IOException { closed = true; }
        };
        try {
            CharBuffer cb = null;
            reader.read(cb);
            fail("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            fail("Unexpected IOException: " + e);
        }
    }

    private static void testReadWhenClosed() {
        Reader reader = new Reader() {
            private boolean closed = false;
            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }
            @Override
            public int read() throws IOException { return -1; }
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException { return -1; }
            @Override
            public int read(CharBuffer target) throws IOException {
                Objects.requireNonNull(target);
                ensureOpen();
                if (target.hasRemaining()) {
                    return -1;
                }
                return 0;
            }
            @Override
            public void close() throws IOException { closed = true; }
        };
        CharBuffer cb = CharBuffer.allocate(10);
        try {
            reader.close();
            reader.read(cb);
            fail("Expected IOException not thrown after close");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void fail(String message) {
        throw new AssertionError(message);
    }
}
