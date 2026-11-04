/*
 * @test
 * @summary Checks DESKeySpec(byte[], int) size validation and the InvalidKeyException path from the WITUP expath
 * @run main DESKeySpecWitupTest
 */

import java.security.InvalidKeyException;
import javax.crypto.spec.DESKeySpec;

public class DESKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        new DESKeySpec(new byte[8]);
        new DESKeySpec(new byte[16], 8);
        new DESKeySpec(new byte[9], 1);

        expectInvalidKey(new byte[7], 0);
        expectInvalidKey(new byte[15], 8);
        expectInvalidKey(new byte[16], 9);
        expectInvalidKey(new byte[0], 0);

        try {
            new DESKeySpec(null, 0);
            throw new RuntimeException("expected NullPointerException for null key");
        } catch (NullPointerException e) {
        }
    }

    static void expectInvalidKey(byte[] key, int offset) {
        try {
            new DESKeySpec(key, offset);
            throw new RuntimeException("expected InvalidKeyException for length=" + key.length + " offset=" + offset);
        } catch (InvalidKeyException e) {
            if (!"Wrong key size".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
