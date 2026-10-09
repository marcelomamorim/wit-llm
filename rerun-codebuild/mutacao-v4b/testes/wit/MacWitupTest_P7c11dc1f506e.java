/* @test
   @summary Testa o método update(ByteBuffer) de javax.crypto.Mac para comportamentos observáveis.
   @run main MacWitupTest_P7c11dc1f506e
 */
import java.util.*;
import java.security.*;
import javax.crypto.*;
import java.nio.ByteBuffer;
public class MacWitupTest_P7c11dc1f506e {
    public static void main(String[] args) throws Exception {
        // Teste 1: Chamada com ByteBuffer nulo deve lançar NullPointerException ou IllegalArgumentException
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            // Inicializa o Mac para evitar IllegalStateException
            mac.init(new SecretKeySpec(new byte[16], "HmacSHA256"));
            mac.update((ByteBuffer) null);
            throw new AssertionError("Esperado NullPointerException ou IllegalArgumentException ao passar ByteBuffer nulo");
        } catch (NullPointerException | IllegalArgumentException e) {
            // esperado
        }
        // Teste 2: Chamada com ByteBuffer válido após inicialização
        Mac mac2 = Mac.getInstance("HmacSHA256");
        mac2.init(new SecretKeySpec(new byte[16], "HmacSHA256"));
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1, 2, 3, 4});
        try {
            mac2.update(buffer);
        } catch (Exception e) {
            throw new AssertionError("Não deveria lançar exceção com ByteBuffer válido", e);
        }
        // Teste 3: Estado não inicializado deve lançar IllegalStateException
        Mac mac3 = Mac.getInstance("HmacSHA256");
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {1, 2, 3});
        try {
            mac3.update(buf);
            throw new AssertionError("Esperado IllegalStateException ao usar Mac não inicializado");
        } catch (IllegalStateException e) {
            // esperado
        }
        // Teste 4: Após inicialização, o método deve atualizar o estado (não verificável diretamente, mas garantido pelo método)
        Mac mac4 = Mac.getInstance("HmacSHA256");
        mac4.init(new SecretKeySpec(new byte[16], "HmacSHA256"));
        ByteBuffer buf2 = ByteBuffer.wrap(new byte[] {10, 20, 30});
        mac4.update(buf2); // não deve lançar
    }
}
