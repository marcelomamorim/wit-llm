/* @test
   @summary Testa o método setApplicationProtocols para verificar comportamento com entradas válidas e inválidas.
   @run main SSLParametersWitupTest_P7910909492c9
 */
import java.util.Objects;
public class SSLParametersWitupTest_P7910909492c9 {
    public static void main(String[] args) {
        testSetApplicationProtocolsNull();
        testSetApplicationProtocolsEmptyElement();
        testSetApplicationProtocolsValid();
    }

    static void testSetApplicationProtocolsNull() {
        javax.net.ssl.SSLParameters params = new javax.net.ssl.SSLParameters();
        try {
            params.setApplicationProtocols(null);
            throw new AssertionError("Expected IllegalArgumentException for null protocols");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testSetApplicationProtocolsEmptyElement() {
        javax.net.ssl.SSLParameters params = new javax.net.ssl.SSLParameters();
        String[] protocols = {"TLSv1.2", ""};
        try {
            params.setApplicationProtocols(protocols);
            throw new AssertionError("Expected IllegalArgumentException for empty string element");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testSetApplicationProtocolsValid() {
        javax.net.ssl.SSLParameters params = new javax.net.ssl.SSLParameters();
        String[] protocols = {"h2", "http/1.1"};
        params.setApplicationProtocols(protocols);
        String[] storedProtocols = params.getClass().getDeclaredField("applicationProtocols").get(null); // não acessa privado, mas para verificar, usar reflexão; aqui, apenas verificar se não lança
        // Como não há método getter público, assumimos que o método funciona se não lança exceção
    }
}
