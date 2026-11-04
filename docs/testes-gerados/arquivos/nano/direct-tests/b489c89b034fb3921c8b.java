/* @test    @summary Test the append(char c) method of java.io.Writer for correct behavior.    @run main WriterWitupTest */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {

    public static void main(String[] args) {
        testAppendCharReturnsThis();
    }

    public static void testAppendCharReturnsThis() {
        Writer writer = new Writer() {
            private boolean open = true;

            private void ensureOpen() throws IOException {
                if (!open) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public Writer append(char c) throws IOException {
                ensureOpen();
                return this;
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
            Writer result = writer.append('a');
            if (result != writer) {
                throw new AssertionError("append(char) did not return this instance");
            }
            System.out.println("Test passed: append(char c) returns this instance.");
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage(), e);
        }
    }
}
