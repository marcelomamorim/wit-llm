/*
 * @test
 * @summary Test for java.io.Writer.append(CharSequence, int, int) method, focusing on exception paths.
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {

    public static void main(String[] args) {
        testAppendThrowsIfClosed();
        testAppendThrowsIfIndicesInvalid();
    }

    private static void testAppendThrowsIfClosed() {
        Writer writer = new Writer() {
            private boolean closed = false;
            @Override
            public void close() throws IOException {
                closed = true;
            }
            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }
            @Override
            public Writer append(CharSequence csq, int start, int end) throws IOException {
                ensureOpen();
                if (csq != null) {
                    Objects.checkFromToIndex(start, end, csq.length());
                }
                return this;
            }
        };
        try {
            writer.close();
            writer.append("test", 0, 4);
            throw new AssertionError("Expected IOException for closed stream");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testAppendThrowsIfIndicesInvalid() {
        String testString = "abc";
        Writer writer = new Writer() {
            private boolean closed = false;
            @Override
            public void close() throws IOException {
                closed = true;
            }
            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }
            @Override
            public Writer append(CharSequence csq, int start, int end) throws IOException {
                ensureOpen();
                if (csq != null) {
                    Objects.checkFromToIndex(start, end, csq.length());
                }
                return this;
            }
        };
        try {
            writer.append(testString, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for start index -1");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        try {
            writer.append(testString, 1, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for end index 5");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        try {
            writer.append(testString, 2, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > end");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }
}
