/* @test    @summary Test getHandshakeSession method of SSLEngine for UnsupportedOperationException    @run main SSLEngineWitupTest */

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;

public class SSLEngineWitupTest {

    public static void main(String[] args) {
        testGetHandshakeSessionThrows();
    }

    public static void testGetHandshakeSessionThrows() {
        SSLEngine engine = new SSLEngine() {
            @Override
            public SSLSession getHandshakeSession() {
                throw new UnsupportedOperationException();
            }
        };
        try {
            engine.getHandshakeSession();
            // Se chegou aqui, o método não lançou exceção, o que é inesperado
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Teste bem-sucedido, exceção esperada
        }
    }
}
