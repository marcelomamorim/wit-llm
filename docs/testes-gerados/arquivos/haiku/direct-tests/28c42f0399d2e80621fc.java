/*
 * @test
 * @summary Verifies that the default InputStream.reset() throws IOException with the expected message and that markSupported() is false by default
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        InputStream in = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        if (in.markSupported()) {
            throw new AssertionError("markSupported() should return false by default");
        }
        in.mark(10);
        try {
            in.reset();
            throw new AssertionError("reset() should throw IOException");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }
}
