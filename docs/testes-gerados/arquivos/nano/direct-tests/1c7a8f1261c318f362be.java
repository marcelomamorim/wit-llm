/* @test    @summary Test Writer.append(CharSequence, int, int) for boundary and null cases    @run main WriterWitupTest */

import java.io.Writer;
import java.io.IOException;
import java.util.Objects;

public class WriterWitupTest {

    public static void main(String[] args) {
        testAppendNull();
        testAppendValidRange();
        testAppendInvalidRange();
    }

    static void testAppendNull() {
        Writer writer = new Writer() {
            private boolean closed = false;
            private void ensureOpen() throws IOException {
                if (closed) throw new IOException("Stream closed");
            }
            @Override
            public Writer append(CharSequence csq, int start, int end) throws IOException {
                ensureOpen();
                if (csq != null) {
                    Objects.checkFromToIndex(start, end, csq.length());
                }
                return this;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            writer.append(null, 0, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testAppendValidRange() {
        Writer writer = new Writer() {
            private boolean closed = false;
            private void ensureOpen() throws IOException {
                if (closed) throw new IOException("Stream closed");
            }
            @Override
            public Writer append(CharSequence csq, int start, int end) throws IOException {
                ensureOpen();
                if (csq != null) {
                    Objects.checkFromToIndex(start, end, csq.length());
                }
                return this;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        String seq = "HelloWorld";
        try {
            writer.append(seq, 0, 5); // "Hello"
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testAppendInvalidRange() {
        Writer writer = new Writer() {
            private boolean closed = false;
            private void ensureOpen() throws IOException {
                if (closed) throw new IOException("Stream closed");
            }
            @Override
            public Writer append(CharSequence csq, int start, int end) throws IOException {
                ensureOpen();
                if (csq != null) {
                    Objects.checkFromToIndex(start, end, csq.length());
                }
                return this;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        String seq = "Hello";
        boolean exceptionThrown = false;
        try {
            writer.append(seq, 3, 2); // invalid range
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!exceptionThrown) {
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown");
        }
    }
}
