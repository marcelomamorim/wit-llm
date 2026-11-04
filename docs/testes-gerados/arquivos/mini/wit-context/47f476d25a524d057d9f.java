/*
 * @test
 * @summary Test java.io.Reader.mark(int) throws IOException as expected
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {
    public static void main(String[] args) {
        Reader r = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                throw new UnsupportedOperationException();
            }
            @Override
            public void close() throws IOException {
                // no-op
            }
        };

        try {
            r.mark(10);
            throw new AssertionError("Expected IOException not thrown by mark()");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("mark() not supported")) {
                throw new AssertionError("Unexpected IOException message: " + msg);
            }
        }
    }
}
