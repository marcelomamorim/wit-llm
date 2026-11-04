/*
 * @test
 * @summary Verify that Reader.mark(int) throws IOException for a reader that does not support mark
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {

    private static final class EmptyReader extends Reader {
        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            return -1;
        }

        @Override
        public void close() throws IOException {
        }
    }

    public static void main(String[] args) throws Exception {
        Reader reader = new EmptyReader();
        if (reader.markSupported()) {
            throw new AssertionError("markSupported() should return false by default");
        }
        checkMarkThrows(reader, 0);
        checkMarkThrows(reader, 16);
        checkMarkThrows(reader, -1);
    }

    private static void checkMarkThrows(Reader reader, int readAheadLimit) {
        try {
            reader.mark(readAheadLimit);
            throw new AssertionError("Expected IOException for readAheadLimit " + readAheadLimit);
        } catch (IOException expected) {
            // expected: mark() is not supported
        }
    }
}
