/*
 * @test
 * @summary Checks ISO10126Padding unpad behavior through the public javax.crypto.Cipher API
 * @run main ISO10126PaddingWitupTest
 */

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class ISO10126PaddingWitupTest {

    private static final String TRANSFORMATION = "AES/CBC/ISO10126Padding";
    private static final int BLOCK = 16;

    private static SecretKeySpec key;
    private static IvParameterSpec iv;

    public static void main(String[] args) throws Exception {
        byte[] keyBytes = new byte[BLOCK];
        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = (byte) i;
        }
        key = new SecretKeySpec(keyBytes, "AES");
        iv = new IvParameterSpec(new byte[BLOCK]);

        testRoundTrip();
        testInvalidPadZero();
        testInvalidPadTooLarge();
        testFullBlockPadding();
    }

    private static void testRoundTrip() throws Exception {
        for (int n = 0; n <= 40; n++) {
            byte[] pt = new byte[n];
            for (int i = 0; i < n; i++) {
                pt[i] = (byte) (i * 7 + 1);
            }
            Cipher enc = Cipher.getInstance(TRANSFORMATION);
            enc.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] ct = enc.doFinal(pt);

            int expectedLen = (n / BLOCK + 1) * BLOCK;
            if (ct.length != expectedLen) {
                throw new RuntimeException("unexpected ciphertext length for n=" + n
                        + ": " + ct.length);
            }

            Cipher dec = Cipher.getInstance(TRANSFORMATION);
            dec.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] back = dec.doFinal(ct);
            if (!Arrays.equals(pt, back)) {
                throw new RuntimeException("round trip mismatch for n=" + n);
            }
        }
    }

    private static void testInvalidPadZero() throws Exception {
        expectBadPadding(craftCiphertext((byte) 0x00));
    }

    private static void testInvalidPadTooLarge() throws Exception {
        expectBadPadding(craftCiphertext((byte) 0x11));
    }

    private static void testFullBlockPadding() throws Exception {
        byte[] ct = craftCiphertext((byte) BLOCK);
        Cipher dec = Cipher.getInstance(TRANSFORMATION);
        dec.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] out = dec.doFinal(ct);
        if (out.length != 0) {
            throw new RuntimeException("expected empty output for full-block padding, got "
                    + out.length);
        }
    }

    private static byte[] craftCiphertext(byte lastByte) throws Exception {
        byte[] pt = new byte[BLOCK];
        pt[BLOCK - 1] = lastByte;
        Cipher enc = Cipher.getInstance("AES/CBC/NoPadding");
        enc.init(Cipher.ENCRYPT_MODE, key, iv);
        return enc.doFinal(pt);
    }

    private static void expectBadPadding(byte[] ct) throws Exception {
        Cipher dec = Cipher.getInstance(TRANSFORMATION);
        dec.init(Cipher.DECRYPT_MODE, key, iv);
        try {
            dec.doFinal(ct);
        } catch (BadPaddingException e) {
            return;
        }
        throw new RuntimeException("expected BadPaddingException was not thrown");
    }
}
