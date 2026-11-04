/* @test    @summary Test for Reader.reset() method to ensure it throws IOException as specified.    @run main ReaderWitupTest */

import java.io.Reader;
import java.io.IOException;

public class ReaderWitupTest {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
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
            public void reset() throws IOException {
                throw new IOException("reset() not supported");
            }
        };
        try {
            reader.reset();
            // If no exception, test fails
            System.err.println("Test failed: reset() did not throw IOException");
            System.exit(1);
        } catch (IOException e) {
            if ("reset() not supported".equals(e.getMessage())) {
                System.out.println("Test passed: reset() threw expected IOException.");
            } else {
                System.err.println("Test failed: reset() threw IOException with unexpected message: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
