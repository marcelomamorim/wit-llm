/* @test    @summary Test do método doFinal() da classe javax.crypto.Mac para verificar comportamento esperado e exceções.    @run main MacWitupTest_Pc7dd5e9cb6e4 */

import javax.crypto.Mac;
import java.security.Provider;

public class MacWitupTest_Pc7dd5e9cb6e4 {

    public static void main(String[] args) throws Exception {
        testDoFinalUninitialized();
        testDoFinalInitialized();
    }

    private static void testDoFinalUninitialized() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            // Não inicializa o Mac
            byte[] result = mac.doFinal();
            System.out.println("Erro: Esperado IllegalStateException, mas obteve resultado: " + result);
        } catch (IllegalStateException e) {
            System.out.println("Passou: IllegalStateException lançada como esperado ao não inicializar.");
        } catch (Exception e) {
            System.out.println("Erro: Exceção inesperada: " + e);
        }
    }

    private static void testDoFinalInitialized() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        // Para o teste, usamos uma implementação padrão e inicializamos com uma chave dummy
        javax.crypto.spec.SecretKeySpec keySpec = new javax.crypto.spec.SecretKeySpec(new byte[]{1,2,3,4,5,6,7,8}, "HmacSHA256");
        mac.init(keySpec);
        byte[] result = mac.doFinal();
        if (result != null && result.length > 0) {
            System.out.println("Passou: doFinal retornou resultado de tamanho " + result.length);
        } else {
            System.out.println("Erro: doFinal retornou resultado nulo ou vazio.");
        }
    }
}
