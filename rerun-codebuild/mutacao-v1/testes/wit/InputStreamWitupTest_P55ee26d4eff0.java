/* @test    @summary Test for InputStream.reset() method to verify it throws IOException as specified.    @run main InputStreamWitupTest_P55ee26d4eff0 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest_P55ee26d4eff0 {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        InputStream stream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        try {
            stream.reset();
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            return;
        }
        throw new AssertionError("Expected IOException was not thrown.");
    }
}
