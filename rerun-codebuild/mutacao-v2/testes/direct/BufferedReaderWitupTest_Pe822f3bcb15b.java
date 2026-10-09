/*
 * @test
 * @summary Test java.io.BufferedReader.mark(int) method behavior
 * @run main BufferedReaderWitupTest_Pe822f3bcb15b
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class BufferedReaderWitupTest_Pe822f3bcb15b {

    static class DummyReader extends Reader {
        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            return -1; // EOF
        }

        @Override
        public void close() throws IOException {
            // no-op
        }
    }

    public static void main(String[] args) throws Exception {
        testMarkWithNegativeReadAheadLimit();
        testMarkSetsStateCorrectly();
    }

    private static void testMarkWithNegativeReadAheadLimit() {
        BufferedReader br = new BufferedReader(new DummyReader());
        try {
            br.mark(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative readAheadLimit");
        } catch (IllegalArgumentException e) {
            if (!"Read-ahead limit < 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testMarkSetsStateCorrectly() throws IOException {
        BufferedReader br = new BufferedReader(new DummyReader());
        // mark with readAheadLimit 10
        br.mark(10);
        // We cannot access private fields directly, but we can test that no exception is thrown
        // and that subsequent mark calls with different values do not throw
        br.mark(0);
        br.mark(100);
    }
}
