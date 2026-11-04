/*
 * @test
 * @summary Test java.io.Writer.write(char[], int, int) method for argument validation and open state
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
            Objects.checkFromIndexSize(off, len, cbuf.length);
            ensureOpen();
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
    }

    public static void main(String[] args) throws Exception {
        TestWriter w = new TestWriter();

        // Test valid write call
        char[] data = new char[] {'a', 'b', 'c', 'd', 'e'};
        w.write(data, 0, 5);
        w.write(data, 1, 3);

        // Test offset and length validation
        try {
            w.write(data, -1, 2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException expected) {}

        try {
            w.write(data, 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException expected) {}

        try {
            w.write(data, 4, 2); // 4 + 2 > 5
            throw new RuntimeException("Expected IndexOutOfBoundsException for offset+length exceeding array length");
        } catch (IndexOutOfBoundsException expected) {}

        // Test null array causes NullPointerException
        try {
            w.write(null, 0, 1);
            throw new RuntimeException("Expected NullPointerException for null array");
        } catch (NullPointerException expected) {}

        // Test IOException when closed
        w.close();
        try {
            w.write(data, 0, 1);
            throw new RuntimeException("Expected IOException after close");
        } catch (IOException expected) {}
    }
}
