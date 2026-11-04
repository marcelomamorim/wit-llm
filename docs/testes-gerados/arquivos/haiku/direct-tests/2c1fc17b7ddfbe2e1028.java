/*
 * @test
 * @summary ISO10126Padding unpad observable behavior through SunJCE AES/ECB/ISO10126Padding
 * @run main ISO10126PaddingWitupTest
 */

import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ISO10126PaddingWitupTest {

    private static SecretKeySpec key() {
        return new SecretKeySpec(new byte[16], "AES");
    }

    private static Cipher decryptor() throws Exception {
        Cipher dec = Cipher.getInstance("AES/ECB/ISO10126Padding", "SunJCE");
        dec.init(Cipher.DECRYPT_MODE, key());
        return dec;
    }

    private static void expectBadPadding(byte[] ciphertext) throws Exception {
        try {
            decryptor().doFinal(ciphertext);
        } catch (BadPaddingException expected) {
            return;
        }
        throw new RuntimeException("Expected BadPaddingException for improperly padded block");
    }

    public static void main(String[] args) throws Exception {
        Cipher enc = Cipher.getInstance("AES/ECB/ISO10126Padding", "SunJCE");
        enc.init(Cipher.ENCRYPT_MODE, key());
        byte[] plain = "hello".getBytes("US-ASCII");
        byte[] ct = enc.doFinal(plain);
        byte[] out = decryptor().doFinal(ct);
        if (!Arrays.equals(plain, out)) {
            throw new RuntimeException("Round trip failed for 5-byte input");
        }

        byte[] plain15 = new byte[15];
        Arrays.fill(plain15, (byte) 'a');
        byte[] ct15 = enc.doFinal(plain15);
        byte[] out15 = decryptor().doFinal(ct15);
        if (!Arrays.equals(plain15, out15)) {
            throw new RuntimeException("Round trip failed for 15-byte input (padValue equals blockSize path)");
        }

        Cipher raw = Cipher.getInstance("AES/ECB/NoPadding", "SunJCE");
        raw.init(Cipher.ENCRYPT_MODE, key());

        byte[] zeroPad = new byte[16];
        Arrays.fill(zeroPad, (byte) 1);
        zeroPad[15] = 0x00;
        expectBadPadding(raw.doFinal(zeroPad));

        byte[] tooLarge = new byte[16];
        Arrays.fill(tooLarge, (byte) 2);
        tooLarge[15] = 0x11;
        expectBadPadding(raw.doFinal(tooLarge));
    }
}
