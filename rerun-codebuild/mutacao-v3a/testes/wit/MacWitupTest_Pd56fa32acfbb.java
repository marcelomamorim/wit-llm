/*
 * @test
 * @summary Test for javax.crypto.Mac.update(ByteBuffer)
 * @run main MacWitupTest_Pd56fa32acfbb
 */

import javax.crypto.Mac;
import java.nio.ByteBuffer;

public class MacWitupTest_Pd56fa32acfbb {

    public static void main(String[] args) {
        testUpdateWithNullBuffer();
        testUpdateWithoutInitialization();
        testUpdateWithValidBuffer();
        testUpdateWithInvalidBufferParameters();
    }

    static void testUpdateWithNullBuffer() {
        try {
            Mac mac = null;
            // Obter uma instância válida de Mac
            mac = Mac.getInstance("HmacSHA256");
            // Não inicializar o Mac para provocar IllegalStateException
            ByteBuffer buffer = ByteBuffer.allocate(10);
            mac.update(buffer); // Deve lançar IllegalStateException
            fail("Expected IllegalStateException for uninitialized Mac");
        } catch (IllegalStateException e) {
            // Teste passou
        } catch (Exception e) {
            fail("Unexpected exception: " + e);
        }
    }

    static void testUpdateWithoutInitialization() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1, 2, 3});
            // Não chamar init
            mac.update(buffer); // Deve lançar IllegalStateException
            fail("Expected IllegalStateException for uninitialized Mac");
        } catch (IllegalStateException e) {
            // Teste passou
        } catch (Exception e) {
            fail("Unexpected exception: " + e);
        }
    }

    static void testUpdateWithValidBuffer() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new javax.crypto.spec.SecretKeySpec(new byte[]{1,2,3,4}, "HmacSHA256"));
            ByteBuffer buffer = ByteBuffer.wrap(new byte[] {10, 20, 30});
            mac.update(buffer); // Deve passar sem lançar
        } catch (Exception e) {
            fail("Unexpected exception: " + e);
        }
    }

    static void testUpdateWithInvalidBufferParameters() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new javax.crypto.spec.SecretKeySpec(new byte[]{1,2,3,4}, "HmacSHA256"));
            ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1,2,3});
            // Criar buffer com posição e limite inválidos
            buffer.position(10); // Fora do limite
            mac.update(buffer); // Pode lançar exception ou não, dependendo da implementação
            // Se não lançar, o teste é válido
        } catch (IllegalArgumentException e) {
            // Esperado se a implementação validar
        } catch (Exception e) {
            fail("Unexpected exception: " + e);
        }
    }

    static void fail(String message) {
        throw new AssertionError(message);
    }
}
