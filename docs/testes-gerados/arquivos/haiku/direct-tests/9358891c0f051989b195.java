/*
 * @test
 * @summary Round-trip and determinism of PBEWithSHA1AndRC2_40 via public JCE API, which exercises the PBEWithSHA1AndRC2_40 constructor path
 * @run main PKCS12PBECipherCoreWitupTest
 */

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class PKCS12PBECipherCoreWitupTest {

    private static final String ALGO = "PBEWithSHA1AndRC2_40";

    public static void main(String[] args) throws Exception {
        char[] password = "secret".toCharArray();
        byte[] salt = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] data = "PKCS12 RC2 40-bit test data".getBytes("UTF-8");

        SecretKey key = SecretKeyFactory.getInstance(ALGO)
                .generateSecret(new PBEKeySpec(password));
        PBEParameterSpec params = new PBEParameterSpec(salt, 1000);

        Cipher enc = Cipher.getInstance(ALGO);
        check("SunJCE".equals(enc.getProvider().getName()), "provider must be SunJCE");
        enc.init(Cipher.ENCRYPT_MODE, key, params);
        byte[] ct1 = enc.doFinal(data);
        check(ct1.length > 0, "ciphertext must not be empty");
        check(!Arrays.equals(data, ct1), "ciphertext must differ from plaintext");

        Cipher enc2 = Cipher.getInstance(ALGO);
        enc2.init(Cipher.ENCRYPT_MODE, key, params);
        byte[] ct2 = enc2.doFinal(data);
        check(Arrays.equals(ct1, ct2), "same key/salt/count must give same ciphertext");

        Cipher dec = Cipher.getInstance(ALGO);
        dec.init(Cipher.DECRYPT_MODE, key, params);
        byte[] pt = dec.doFinal(ct1);
        check(Arrays.equals(data, pt), "decrypted data must equal original");
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
