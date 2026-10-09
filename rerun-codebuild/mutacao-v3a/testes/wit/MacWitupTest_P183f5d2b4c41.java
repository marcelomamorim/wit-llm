/* @test    @summary Tests for javax.crypto.Mac.doFinal() method
 * @run main MacWitupTest_P183f5d2b4c41
 */

import javax.crypto.Mac;
import java.security.Provider;

public class MacWitupTest_P183f5d2b4c41 {

    public static void main(String[] args) throws Exception {
        testDoFinalInitialized();
        testDoFinalNotInitialized();
    }

    private static void testDoFinalInitialized() throws Exception {
        // Cria uma instância de Mac usando uma implementação padrão
        Mac mac = Mac.getInstance("HmacSHA256");
        // Inicializa com uma chave dummy para evitar IllegalStateException
        byte[] keyBytes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        javax.crypto.SecretKey key = new javax.crypto.spec.SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(key);
        // Executa o método doFinal, que deve retornar um array de bytes
        byte[] result = mac.doFinal();
        if (result == null || result.length == 0) {
            throw new AssertionError("doFinal() retornou resultado inválido quando inicializado");
        }
        System.out.println("testDoFinalInitialized passou.");
    }

    private static void testDoFinalNotInitialized() {
        try {
            // Cria uma instância de Mac sem inicializar
            Mac mac = Mac.getInstance("HmacSHA256");
            // Tenta executar doFinal sem inicializar
            mac.doFinal();
            throw new AssertionError("Esperado IllegalStateException ao chamar doFinal() sem init()");
        } catch (IllegalStateException e) {
            // Esperado
            System.out.println("testDoFinalNotInitialized passou.");
        } catch (Exception e) {
            throw new AssertionError("Erro inesperado: " + e);
        }
    }
}
