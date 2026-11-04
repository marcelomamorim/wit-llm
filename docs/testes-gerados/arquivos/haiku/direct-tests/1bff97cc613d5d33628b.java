/*
 * @test
 * @summary CipherInputStream.read() returns processed bytes as unsigned values and -1 at end of stream
 * @run main CipherInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

public class CipherInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] keyBytes = new byte[16];
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        byte[] plain = new byte[16];
        for (int i = 0; i < plain.length; i++) {
            plain[i] = (byte) (i * 17);
        }

        Cipher expectedCipher = Cipher.getInstance("AES/ECB/NoPadding");
        expectedCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] expected = expectedCipher.doFinal(plain);

        Cipher c = Cipher.getInstance("AES/ECB/NoPadding");
        c.init(Cipher.ENCRYPT_MODE, key);
        CipherInputStream cis = new CipherInputStream(new ByteArrayInputStream(plain), c);
        for (int i = 0; i < expected.length; i++) {
            int b = cis.read();
            int exp = expected[i] & 0xff;
            if (b != exp) {
                throw new RuntimeException("byte " + i + ": expected " + exp + " but was " + b);
            }
        }
        if (cis.read() != -1) {
            throw new RuntimeException("expected -1 at end of stream");
        }
        cis.close();

        Cipher c2 = Cipher.getInstance("AES/ECB/NoPadding");
        c2.init(Cipher.ENCRYPT_MODE, key);
        CipherInputStream empty = new CipherInputStream(new ByteArrayInputStream(new byte[0]), c2);
        if (empty.read() != -1) {
            throw new RuntimeException("expected -1 for empty underlying stream");
        }
        empty.close();
    }
}
