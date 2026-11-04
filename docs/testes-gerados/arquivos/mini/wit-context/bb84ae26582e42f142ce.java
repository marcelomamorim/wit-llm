/*
 * @test
 * @summary Test java.io.Writer.write(char[], int, int) for bounds and closed stream exceptions
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public class WriterWitupTest {

    // A minimal concrete subclass of Writer to test the abstract class
    static class TestWriter extends Writer {
        private boolean closed = false;

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            Objects.checkFromIndexSize(off, len, cbuf.length);
            ensureOpen();
            // no actual writing
        }

        @Override
        public void flush() throws IOException {
            ensureOpen();
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }

        void ensureOpen() throws IOException {
            if (closed) {
                throw new IOException("Stream closed");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        TestWriter writer = new TestWriter();

        // Test 1: Valid write call should not throw
        char[] validBuf = new char[10];
        writer.write(validBuf, 0, 10);

        // Test 2: Off < 0 should throw IndexOutOfBoundsException
        try {
            writer.write(validBuf, -1, 5);
            throw new RuntimeException("Expected IndexOutOfBoundsException for off < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test 3: Len < 0 should throw IndexOutOfBoundsException
        try {
            writer.write(validBuf, 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for len < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test 4: off + len > cbuf.length should throw IndexOutOfBoundsException
        try {
            writer.write(validBuf, 5, 6); // 5 + 6 = 11 > 10
            throw new RuntimeException("Expected IndexOutOfBoundsException for off + len > cbuf.length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test 5: After close, write should throw IOException
        writer.close();
        try {
            writer.write(validBuf, 0, 1);
            throw new RuntimeException("Expected IOException after close");
        } catch (IOException expected) {
            // expected
        }
    }
}
