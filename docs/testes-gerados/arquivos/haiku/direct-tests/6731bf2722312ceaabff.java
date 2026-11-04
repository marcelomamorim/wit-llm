/*
 * @test
 * @summary Verifies the public javax.crypto behavior of the PBEWithSHA1AndRC4_128 cipher registered by SunJCE, which is backed by PKCS12PBECipherCore
 * @run main PKCS12PBECipherCoreWitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class PKCS12PBECipherCoreWitupTest {

    private static final String ALGO = "PBEWithSHA1AndRC4_128";

    public static void main(String[] args) throws Exception {
        char[] password = "password".toCharArray();
        byte[] salt = new byte[20];
        for (int i = 0; i < salt.length; i++) {
            salt[i] = (byte) (i + 1);
        }
        PBEParameterSpec params = new PBEParameterSpec(salt, 1024);
        SecretKey key = SecretKeyFactory.getInstance(ALGO)
                .generateSecret(new PBEKeySpec(password));

        Cipher enc = Cipher.getInstance(ALGO);
        check(ALGO.equals(enc.getAlgorithm()), "algorithm name mismatch");

        byte[] plain = "hello PKCS12".getBytes(StandardCharsets.UTF_8);

        enc.init(Cipher.ENCRYPT_MODE, key, params);
        byte[] cipherText = enc.doFinal(plain);
        check(cipherText.length == plain.length, "RC4 output length must equal input length");
        check(!Arrays.equals(plain, cipherText), "ciphertext must differ from plaintext");

        Cipher dec = Cipher.getInstance(ALGO);
        dec.init(Cipher.DECRYPT_MODE, key, params);
        check(Arrays.equals(plain, dec.doFinal(cipherText)), "round trip failed");

        Cipher enc2 = Cipher.getInstance(ALGO);
        enc2.init(Cipher.ENCRYPT_MODE, key, params);
        check(Arrays.equals(cipherText, enc2.doFinal(plain)),
              "same inputs must produce the same ciphertext");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
