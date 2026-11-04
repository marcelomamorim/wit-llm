/* @test    @summary Test Reader.read(CharBuffer) method for correct behavior    @run main ReaderWitupTest */

import java.io.Reader;
import java.nio.CharBuffer;

public class ReaderWitupTest {

    public static void main(String[] args) {
        testReadWithRemainingBuffer();
        testReadWithEmptyBuffer();
    }

    private static void testReadWithRemainingBuffer() {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put('a');
        buffer.flip();
        Reader reader = new Reader() {
            private boolean closed = false;
            private void ensureOpen() throws java.io.IOException {
                if (closed) throw new java.io.IOException("Stream closed");
            }
            @Override
            public int read(CharBuffer target) throws java.io.IOException {
                Objects.requireNonNull(target);
                ensureOpen();
                if (target.hasRemaining()) {
                    return -1;
                }
                return 0;
            }
            @Override
            public void close() throws java.io.IOException {
                closed = true;
            }
        };
        int result = -1;
        try {
            result = reader.read(buffer);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        assert result == -1 : "Expected -1 when buffer has remaining space";
    }

    private static void testReadWithEmptyBuffer() {
        CharBuffer buffer = CharBuffer.allocate(0);
        Reader reader = new Reader() {
            private boolean closed = false;
            private void ensureOpen() throws java.io.IOException {
                if (closed) throw new java.io.IOException("Stream closed");
            }
            @Override
            public int read(CharBuffer target) throws java.io.IOException {
                Objects.requireNonNull(target);
                ensureOpen();
                if (target.hasRemaining()) {
                    return -1;
                }
                return 0;
            }
            @Override
            public void close() throws java.io.IOException {
                closed = true;
            }
        };
        int result = -1;
        try {
            result = reader.read(buffer);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        assert result == 0 : "Expected 0 when buffer is empty";
    }
}
