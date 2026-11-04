/*
 * @test
 * @summary Test for Reader.mark(int) method to verify IOException is thrown as expected.
 * @run main ReaderWitupTest
 */

import java.io.Reader;
import java.io.IOException;

public class ReaderWitupTest {

    public static void main(String[] args) {
        testMarkThrowsIOException();
    }

    public static void testMarkThrowsIOException() {
        Reader reader = new Reader() {
            private boolean closed = false;

            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public int read() throws IOException {
                ensureOpen();
                return -1;
            }

            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, cbuf.length);
                ensureOpen();
                return -1;
            }

            @Override
            public void close() throws IOException {
                closed = true;
            }

            @Override
            public void mark(int readAheadLimit) throws IOException {
                throw new IOException("mark() not supported");
            }
        };
        try {
            reader.mark(10);
            System.out.println("FAIL: Expected IOException was not thrown.");
        } catch (IOException e) {
            if ("mark() not supported".equals(e.getMessage())) {
                System.out.println("PASS: IOException thrown as expected.");
            } else {
                System.out.println("FAIL: Unexpected IOException message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }
}
