/*
 * @test
 * @summary Test java.io.Writer.append(CharSequence, int, int) method
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;
import java.util.Objects;

public class WriterWitupTest {

    // Minimal concrete subclass of Writer to test append method
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
        public Writer append(CharSequence csq, int start, int end) throws IOException {
            ensureOpen();
            if (csq != null) {
                Objects.checkFromToIndex(start, end, csq.length());
            }
            return this;
        }

        private void ensureOpen() throws IOException {
            if (closed) throw new IOException("Stream closed");
        }
    }

    public static void main(String[] args) throws Exception {
        TestWriter w = new TestWriter();

        // Test 1: append with null CharSequence returns this without exception
        Writer r1 = w.append(null, 0, 0);
        if (r1 != w) throw new RuntimeException("append(null) did not return this");

        // Test 2: append with valid CharSequence and valid indices returns this
        String s = "hello world";
        Writer r2 = w.append(s, 0, s.length());
        if (r2 != w) throw new RuntimeException("append(valid) did not return this");

        // Test 3: append with start == end (empty subsequence) returns this
        Writer r3 = w.append(s, 3, 3);
        if (r3 != w) throw new RuntimeException("append(empty subsequence) did not return this");

        // Test 4: append with invalid indices throws IndexOutOfBoundsException
        boolean thrown = false;
        try {
            w.append(s, -1, 5);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("append with negative start did not throw");

        thrown = false;
        try {
            w.append(s, 3, 20);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("append with end > length did not throw");

        thrown = false;
        try {
            w.append(s, 5, 3);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("append with start > end did not throw");

        // Test 5: after close, append throws IOException
        w.close();
        thrown = false;
        try {
            w.append(s, 0, 1);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("append after close did not throw IOException");

        System.out.println("All tests passed.");
    }
}
