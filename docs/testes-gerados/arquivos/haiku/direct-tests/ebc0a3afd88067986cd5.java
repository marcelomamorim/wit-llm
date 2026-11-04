/*
 * @test
 * @summary Checks DESKeySpec(byte[]) delegating to DESKeySpec(byte[], int) for key length handling
 * @run main DESKeySpecWitupTest
 */
import java.security.InvalidKeyException;
import java.security.spec.KeySpec;
import javax.crypto.spec.DESKeySpec;

public class DESKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        if (DESKeySpec.DES_KEY_LEN != 8) {
            throw new RuntimeException("DES_KEY_LEN expected 8");
        }

        byte[] key = new byte[DESKeySpec.DES_KEY_LEN];
        for (int i = 0; i < key.length; i++) {
            key[i] = (byte) (i + 1);
        }
        DESKeySpec spec = new DESKeySpec(key);
        if (!(spec instanceof KeySpec)) {
            throw new RuntimeException("DESKeySpec must implement KeySpec");
        }

        DESKeySpec longer = new DESKeySpec(new byte[16]);
        if (longer == null) {
            throw new RuntimeException("Longer key rejected");
        }

        DESKeySpec withOffset = new DESKeySpec(new byte[16], 8);
        if (withOffset == null) {
            throw new RuntimeException("Offset constructor returned null");
        }

        try {
            new DESKeySpec(new byte[7]);
            throw new RuntimeException("Short key of 7 bytes was accepted");
        } catch (InvalidKeyException expected) {
            // expected
        }

        try {
            new DESKeySpec(new byte[0]);
            throw new RuntimeException("Empty key was accepted");
        } catch (InvalidKeyException expected) {
            // expected
        }
    }
}
