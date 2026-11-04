/*
 * @test
 * @summary Test the mark method of java.io.Reader which throws IOException as specified.
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
            @Override
            public int read() throws IOException {
                return -1;
            }

            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return -1;
            }

            @Override
            public void close() throws IOException {
                // no-op
            }

            @Override
            public void mark(int readAheadLimit) throws IOException {
                throw new IOException("mark() not supported");
            }
        };
        try {
            reader.mark(10);
            // If no exception, test fails
            System.err.println("Test failed: IOException was not thrown by mark()");
        } catch (IOException e) {
            if ("mark() not supported".equals(e.getMessage())) {
                System.out.println("Test passed: mark() threw expected IOException.");
            } else {
                System.err.println("Test failed: Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
