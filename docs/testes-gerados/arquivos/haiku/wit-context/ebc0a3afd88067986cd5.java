/*
 * @test
 * @summary Verify DESKeySpec(byte[]) rejects keys shorter than 8 bytes and accepts keys of at least 8 bytes
 * @run main DESKeySpecWitupTest
 */
import java.security.InvalidKeyException;
import javax.crypto.spec.DESKeySpec;

public class DESKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        new DESKeySpec(new byte[8]);
        new DESKeySpec(new byte[16]);
        expectInvalidKey(new byte[0]);
        expectInvalidKey(new byte[1]);
        expectInvalidKey(new byte[7]);
    }

    static void expectInvalidKey(byte[] key) {
        try {
            new DESKeySpec(key);
        } catch (InvalidKeyException e) {
            return;
        }
        throw new RuntimeException("InvalidKeyException expected for key length " + key.length);
    }
}
