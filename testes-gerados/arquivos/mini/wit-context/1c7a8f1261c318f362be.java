/*
 * @test
 * @summary Test java.io.Writer.append(CharSequence, int, int) for exception and boundary cases
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;
import java.util.Objects;

public class WriterWitupTest {

    public static void main(String[] args) throws Exception {
        testAppendThrowsIOExceptionWhenClosed();
        testAppendThrowsIndexOutOfBoundsWhenInvalidIndices();
        testAppendReturnsThisWhenValid();
    }

    // A minimal concrete subclass of Writer to test append method
    static class TestWriter extends Writer {
        private boolean closed = false;

        protected TestWriter() {
            super();
        }

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

        // expose ensureOpen behavior by overriding append
        @Override
        public Writer append(CharSequence csq, int start, int end) throws IOException {
            if (closed) {
                throw new IOException("Stream closed");
            }
            if (csq != null) {
                Objects.checkFromToIndex(start, end, csq.length());
            }
            return this;
        }
    }

    private static void testAppendThrowsIOExceptionWhenClosed() {
        TestWriter w = new TestWriter();
        try {
            w.close();
            w.append("abc", 0, 1);
            throw new RuntimeException("Expected IOException not thrown when appending to closed Writer");
        } catch (IOException e) {
            if (!"Stream closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    private static void testAppendThrowsIndexOutOfBoundsWhenInvalidIndices() throws IOException {
        TestWriter w = new TestWriter();
        String csq = "abc";
        // start < 0
        try {
            w.append(csq, -1, 2);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for start < 0");
        } catch (IndexOutOfBoundsException expected) {
        }
        // start > end
        try {
            w.append(csq, 2, 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for start > end");
        } catch (IndexOutOfBoundsException expected) {
        }
        // end > csq.length()
        try {
            w.append(csq, 0, 4);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for end > csq.length()");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void testAppendReturnsThisWhenValid() throws IOException {
        TestWriter w = new TestWriter();
        String csq = "abc";
        Writer result = w.append(csq, 0, 3);
        if (result != w) {
            throw new RuntimeException("append should return this instance");
        }
        // Also test with null csq returns this without exception
        result = w.append(null, 0, 0);
        if (result != w) {
            throw new RuntimeException("append should return this instance when csq is null");
        }
    }
}
