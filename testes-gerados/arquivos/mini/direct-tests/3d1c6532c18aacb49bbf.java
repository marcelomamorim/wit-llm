/*
 * @test
 * @summary Test java.io.Writer.write(String, int, int) method for boundary and error conditions
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
            // no-op
        }

        @Override
        public void flush() throws IOException {
            if (!open) throw new IOException("Stream closed");
        }

        @Override
        public void close() throws IOException {
            open = false;
        }

        @Override
        protected void ensureOpen() throws IOException {
            if (!open) throw new IOException("Stream closed");
        }
    }

    public static void main(String[] args) throws Exception {
        TestWriter w = new TestWriter();

        // Test valid write call with full string
        String s = "Hello, world!";
        w.write(s, 0, s.length());

        // Test valid write call with substring
        w.write(s, 7, 5); // "world"

        // Test zero length write
        w.write(s, 5, 0);

        // Test offset at end of string with zero length
        w.write(s, s.length(), 0);

        // Test invalid offset (negative)
        try {
            w.write(s, -1, 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test invalid length (negative)
        try {
            w.write(s, 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test offset + length beyond string length
        try {
            w.write(s, 5, s.length());
            throw new RuntimeException("Expected IndexOutOfBoundsException for offset+length beyond string length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test write after close throws IOException
        w.close();
        try {
            w.write(s, 0, 1);
            throw new RuntimeException("Expected IOException after close");
        } catch (IOException expected) {
            // expected
        }
    }
}
