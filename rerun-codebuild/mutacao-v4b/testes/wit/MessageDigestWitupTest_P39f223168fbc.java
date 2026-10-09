/* @test
   @summary Testa o método update(ByteBuffer) de MessageDigest, incluindo o caminho que lança NullPointerException.
   @run main MessageDigestWitupTest_P39f223168fbc
 */
import java.security.*;
import java.nio.ByteBuffer;

public class MessageDigestWitupTest_P39f223168fbc {
    public static void main(String[] args) throws Exception {
        testUpdateWithNull();
        testUpdateWithValidBuffer();
    }

    private static void testUpdateWithNull() {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try {
            md.update((ByteBuffer) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // esperado
        }
    }

    private static void testUpdateWithValidBuffer() {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1, 2, 3});
        md.update(buffer);
        byte[] digest1 = md.digest();
        // Executa novamente para verificar se o estado foi resetado após digest
        ByteBuffer buffer2 = ByteBuffer.wrap(new byte[] {4, 5, 6});
        md.update(buffer2);
        byte[] digest2 = md.digest();
        if (digest1 == null || digest2 == null) {
            throw new AssertionError("Digest should not be null");
        }
        if (digest1.length != 32 || digest2.length != 32) {
            throw new AssertionError("Digest length mismatch");
        }
    }
}
