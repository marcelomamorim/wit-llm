/* @test
   @summary Testa o método setMaximumPacketSize para verificar comportamento com entradas inválidas.
   @run main SSLParametersWitupTest_P5043c6d23ed1
 */
import java.util.Objects;
public class SSLParametersWitupTest_P5043c6d23ed1 {
    public static void main(String[] args) throws Exception {
        javax.net.ssl.SSLParameters params = new javax.net.ssl.SSLParameters();
        // Teste de entrada inválida: valor negativo
        try {
            params.setMaximumPacketSize(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative value");
        } catch (IllegalArgumentException e) {
            // esperado
        }
        // Teste de entrada válida: zero
        try {
            params.setMaximumPacketSize(0);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero value", e);
        }
        // Teste de entrada válida: valor positivo
        try {
            params.setMaximumPacketSize(1024);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for positive value", e);
        }
    }
}
