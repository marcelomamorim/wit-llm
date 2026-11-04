/*
 * @test
 * @summary Testa o método available() de DeflaterInputStream para verificar comportamento esperado e exceções.
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) {
        testAvailableWithNullInputStream();
        testAvailableNormalFlow();
    }

    private static void testAvailableWithNullInputStream() {
        try {
            // Cria uma instância de DeflaterInputStream com in = null para disparar IOException
            new DeflaterInputStream(null, new Deflater());
            // Se não lançar exceção, falha o teste
            throw new AssertionError("Expected IOException not thrown for null InputStream");
        } catch (NullPointerException e) {
            // Esperado, pois o construtor lança NullPointerException se in == null
        } catch (IOException e) {
            // Não esperado neste caso, pois o construtor não lança IOException
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static void testAvailableNormalFlow() {
        InputStream dummyStream = new InputStream() {
            private boolean closed = false;
            @Override
            public int read() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
                return -1; // EOF
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        Deflater deflater = new Deflater();
        try (DeflaterInputStream dis = new DeflaterInputStream(dummyStream, deflater)) {
            int available = dis.available();
            if (available != 0 && available != 1) {
                throw new AssertionError("available() should return 0 or 1, but got: " + available);
            }
        } catch (IOException e) {
            throw new AssertionError("IOException during available(): " + e);
        }
    }
}
