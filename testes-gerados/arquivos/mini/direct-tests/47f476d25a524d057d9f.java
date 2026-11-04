/*
 * @test
 * @summary Test Reader.mark(int) throws IOException as expected
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
            public void close() throws IOException {}
        };

        try {
            r.mark(1);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"mark() not supported".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
