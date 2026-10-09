/* @test    @summary Test InputStream.reset() throws IOException when mark/reset not supported    @run main InputStreamWitupTest_Pa8d6732dc3e0 */

import java.io.InputStream;
import java.io.IOException;

public class InputStreamWitupTest_Pa8d6732dc3e0 {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        try {
            is.reset();
            // If no exception is thrown, the test fails.
            System.err.println("FAIL: reset() did not throw IOException as expected.");
            System.exit(1);
        } catch (IOException e) {
            if ("mark/reset not supported".equals(e.getMessage())) {
                // Expected exception, test passes.
                System.out.println("PASS: reset() threw expected IOException.");
                System.exit(0);
            } else {
                System.err.println("FAIL: reset() threw IOException with unexpected message: " + e.getMessage());
                System.exit(1);
            }
        } catch (Throwable t) {
            System.err.println("FAIL: reset() threw unexpected exception: " + t);
            System.exit(1);
        }
    }
}
