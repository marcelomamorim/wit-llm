/*
 * @test
 * @summary Test Reader.reset() throws IOException as expected
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Reader r = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return -1;
            }
            @Override
            public void close() throws IOException {
            }
        };

        try {
            r.reset();
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"reset() not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
