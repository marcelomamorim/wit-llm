/* @test    @summary Test do método doFinal() da classe javax.crypto.Mac para verificar comportamento esperado e exceções.    @run main MacWitupTest_Pb6f68f7f3641 */

import javax.crypto.Mac;
import java.security.Provider;

public class MacWitupTest_Pb6f68f7f3641 {

    public static void main(String[] args) {
        testDoFinalUninitialized();
        testDoFinalInitialized();
    }

    private static void testDoFinalUninitialized() {
        try {
            Mac mac = null;
            // Cria uma instância de Mac usando getInstance com algoritmo padrão (exemplo: HmacSHA256)
            mac = Mac.getInstance("HmacSHA256");
            // Não inicializa o Mac com init()
            byte[] result = mac.doFinal();
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // Esperado
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testDoFinalInitialized() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            // Inicializa o Mac com uma chave válida
            javax.crypto.SecretKey key = javax.crypto.KeyGenerator.getInstance("HmacSHA256").generateKey();
            mac.init(key);
            byte[] result = mac.doFinal();
            if (result == null || result.length == 0) {
                throw new AssertionError("doFinal() retornou resultado inválido");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during initialized doFinal(): " + e);
        }
    }
}
