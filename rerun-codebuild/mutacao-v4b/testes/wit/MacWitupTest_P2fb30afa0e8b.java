/* @test
   @summary Testa o método doFinal() do javax.crypto.Mac.
   @run main MacWitupTest_P2fb30afa0e8b
 */
import java.util.*;
import java.security.*;
import javax.crypto.*;

public class MacWitupTest_P2fb30afa0e8b {
    public static void main(String[] args) throws Exception {
        testDoFinalWithoutInitialization();
        testDoFinalAfterInitialization();
    }

    private static void testDoFinalWithoutInitialization() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        // Não inicializa o Mac, deve lançar IllegalStateException
        try {
            mac.doFinal();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // esperado
        }
    }

    private static void testDoFinalAfterInitialization() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKey key = new SecretKeySpec(new byte[16], "HmacSHA256");
        mac.init(key);
        byte[] result = mac.doFinal();
        if (result == null || result.length == 0) {
            throw new AssertionError("doFinal() retornou resultado inválido");
        }
    }
}
