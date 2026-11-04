/*
 * @test
 * @summary Verify InputStream.reset() throws IOException when mark/reset is not supported
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    static class SimpleStream extends InputStream {
        @Override
        public int read() throws IOException {
            return -1;
        }
    }

    public static void main(String[] args) throws Exception {
        checkResetThrows(new SimpleStream(), "mark/reset not supported");

        InputStream marked = new SimpleStream();
        marked.mark(16);
        checkResetThrows(marked, "mark/reset not supported");

        checkResetThrows(InputStream.nullInputStream(), null);
    }

    static void checkResetThrows(InputStream in, String expectedMessage) {
        try {
            in.reset();
        } catch (IOException e) {
            if (expectedMessage != null && !expectedMessage.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("reset() did not throw IOException");
    }
}
