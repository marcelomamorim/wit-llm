/* @test    @summary Test for Reader.skip(long) method to verify exception on negative input    @run main ReaderWitupTest_P1abbab286f21 */

import java.io.Reader;
import java.io.IOException;

public class ReaderWitupTest_P1abbab286f21 {

    public static void main(String[] args) throws Exception {
        testSkipNegative();
    }

    private static void testSkipNegative() throws IOException {
        Reader reader = new Reader() {
            private boolean closed = false;
            private final Object lock = new Object();
            private char[] skipBuffer = null;
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                if (closed) throw new IOException("Stream closed");
                return -1;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            reader.skip(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative skip value");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
