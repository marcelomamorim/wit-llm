/*
 * @test
 * @summary Verify PBEWithSHA1AndRC4_128 is instantiable through the public SunJCE Cipher API
 * @run main PKCS12PBECipherCoreWitupTest
 */
import javax.crypto.Cipher;

public class PKCS12PBECipherCoreWitupTest {

    public static void main(String[] args) throws Exception {
        Cipher cipher = Cipher.getInstance("PBEWithSHA1AndRC4_128", "SunJCE");
        if (cipher == null) {
            throw new RuntimeException("Cipher instance is null");
        }
        if (!"SunJCE".equals(cipher.getProvider().getName())) {
            throw new RuntimeException("Unexpected provider: " + cipher.getProvider().getName());
        }
        if (!"PBEWithSHA1AndRC4_128".equalsIgnoreCase(cipher.getAlgorithm())) {
            throw new RuntimeException("Unexpected algorithm: " + cipher.getAlgorithm());
        }
    }
}
