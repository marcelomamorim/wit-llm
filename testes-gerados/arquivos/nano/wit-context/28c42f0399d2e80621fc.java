/* @test    @summary Test for InputStream.reset() method to verify it throws IOException as specified.    @run main InputStreamWitupTest */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

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
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Verifica se a mensagem de exceção é a esperada
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
