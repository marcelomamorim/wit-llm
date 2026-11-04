/*
 * @test
 * @summary Verifies that Reader.reset() throws IOException with the documented message for Reader instances that do not override it
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {

    public static void main(String[] args) throws Exception {
        expectResetFails(Reader.nullReader());

        Reader custom = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) {
                return -1;
            }

            @Override
            public void close() {
            }
        };
        expectResetFails(custom);
    }

    private static void expectResetFails(Reader r) {
        try {
            r.reset();
            throw new AssertionError("reset() did not throw IOException");
        } catch (IOException e) {
            if (!"reset() not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
