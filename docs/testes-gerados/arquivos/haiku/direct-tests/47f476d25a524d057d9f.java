/*
 * @test
 * @summary Verifies that Reader.mark(int) always throws IOException with the message "mark() not supported"
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {

    static class StubReader extends Reader {
        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            return -1;
        }

        @Override
        public void close() throws IOException {
        }
    }

    static void expectMarkFails(Reader r, int limit) {
        try {
            r.mark(limit);
            throw new RuntimeException("mark(" + limit + ") did not throw");
        } catch (IOException e) {
            if (!"mark() not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage(), e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Reader stub = new StubReader();
        expectMarkFails(stub, 0);
        expectMarkFails(stub, 10);
        expectMarkFails(stub, -1);

        Reader nul = Reader.nullReader();
        expectMarkFails(nul, 1);
    }
}
