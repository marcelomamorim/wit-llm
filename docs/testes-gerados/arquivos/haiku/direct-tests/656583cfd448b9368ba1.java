/*
 * @test
 * @summary Verifies DESKeySpec(byte[] key, int offset) copies 8 bytes from the offset and rejects keys that are too short
 * @run main DESKeySpecWitupTest
 */

import java.security.InvalidKeyException;
import java.util.Arrays;
import javax.crypto.spec.DESKeySpec;

public class DESKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] key = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        DESKeySpec spec = new DESKeySpec(key, 0);
        check(Arrays.equals(spec.getEncoded(), Arrays.copyOfRange(key, 0, 8)), "offset 0");

        spec = new DESKeySpec(key, 2);
        check(Arrays.equals(spec.getEncoded(), Arrays.copyOfRange(key, 2, 10)), "offset 2");

        spec = new DESKeySpec(key, 3);
        check(Arrays.equals(spec.getEncoded(), Arrays.copyOfRange(key, 3, 11)), "offset 3 (last valid)");

        byte[] exact = new byte[8];
        for (int i = 0; i < exact.length; i++) {
            exact[i] = (byte) (0x10 + i);
        }
        spec = new DESKeySpec(exact, 0);
        check(Arrays.equals(spec.getEncoded(), exact), "exact 8-byte key");

        byte[] source = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        spec = new DESKeySpec(source, 1);
        source[1] = (byte) 0x7F;
        check(Arrays.equals(spec.getEncoded(), new byte[] {2, 3, 4, 5, 6, 7, 8, 9}),
              "key must be copied, not aliased");

        expectInvalidKey(new byte[7], 0);
        expectInvalidKey(new byte[10], 3);
        expectInvalidKey(new byte[8], 1);
        expectInvalidKey(new byte[0], 0);
    }

    private static void expectInvalidKey(byte[] key, int offset) {
        try {
            new DESKeySpec(key, offset);
        } catch (InvalidKeyException e) {
            return;
        }
        throw new RuntimeException("Expected InvalidKeyException for length "
                + key.length + " offset " + offset);
    }

    private static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Unexpected result: " + what);
        }
    }
}
