/*
 * @test
 * @summary Verifies that the PBEWithSHA1AndRC2_128 cipher registered by SunJCE can be obtained and performs a PBE encrypt/decrypt round trip
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

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Cipher cipher = Cipher.getInstance("PBEWithSHA1AndRC2_128");
        check(cipher != null, "Cipher instance must not be null");
        check("PBEWithSHA1AndRC2_128".equalsIgnoreCase(cipher.getAlgorithm()),
              "Unexpected algorithm name: " + cipher.getAlgorithm());
        check("SunJCE".equals(cipher.getProvider().getName()),
              "Unexpected provider: " + cipher.getProvider().getName());

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithSHA1AndRC2_128");
        SecretKey key = factory.generateSecret(new PBEKeySpec("password".toCharArray()));
        byte[] salt = {1, 2, 3, 4, 5, 6, 7, 8};
        PBEParameterSpec params = new PBEParameterSpec(salt, 1000);
        byte[] data = "hello world".getBytes(StandardCharsets.UTF_8);

        cipher.init(Cipher.ENCRYPT_MODE, key, params);
        byte[] encrypted = cipher.doFinal(data);
        check(encrypted.length > 0, "Encrypted output must not be empty");
        check(!Arrays.equals(data, encrypted), "Ciphertext must differ from plaintext");

        Cipher decipher = Cipher.getInstance("PBEWithSHA1AndRC2_128");
        decipher.init(Cipher.DECRYPT_MODE, key, params);
        byte[] decrypted = decipher.doFinal(encrypted);
        check(Arrays.equals(data, decrypted), "Decrypted data must match original plaintext");
    }
}
