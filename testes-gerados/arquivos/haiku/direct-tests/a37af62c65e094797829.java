/*
 * @test
 * @summary Reader.reset() must throw IOException with message "reset() not supported" by default
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {

    static class EmptyReader extends Reader {
        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            return -1;
        }

        @Override
        public void close() throws IOException {
        }
    }

    static void checkResetThrows(Reader r) {
        try {
            r.reset();
            throw new AssertionError("expected IOException from reset()");
        } catch (IOException e) {
            if (!"reset() not supported".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Reader r = new EmptyReader();
        checkResetThrows(r);
        checkResetThrows(r);

        int c = r.read();
        if (c != -1) {
            throw new AssertionError("expected end of stream, got " + c);
        }
        checkResetThrows(r);

        r.close();
        checkResetThrows(r);
    }
}
