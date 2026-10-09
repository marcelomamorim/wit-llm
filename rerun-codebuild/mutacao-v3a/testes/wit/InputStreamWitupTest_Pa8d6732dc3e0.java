/* @test    @summary Test for java.io.InputStream.reset() method to verify proper exception throwing behavior.    @run main InputStreamWitupTest_Pa8d6732dc3e0 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest_Pa8d6732dc3e0 {

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
            // Se nenhuma exceção for lançada, o teste falha
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage(), e);
            }
        }
    }
}
