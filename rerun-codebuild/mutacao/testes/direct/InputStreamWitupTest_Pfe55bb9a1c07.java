/*
 * @test
 * @summary Test for InputStream.reset() method to ensure it throws IOException with correct message.
 * @run main InputStreamWitupTest_Pfe55bb9a1c07
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest_Pfe55bb9a1c07 {

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
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
