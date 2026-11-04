/*
 * @test
 * @summary Verify that the public PBEWithSHA1AndDESede cipher is resolvable through the SunJCE provider and reports its algorithm name
 * @run main PKCS12PBECipherCoreWitupTest
 */
import javax.crypto.Cipher;

public class PKCS12PBECipherCoreWitupTest {

    public static void main(String[] args) throws Exception {
        Cipher c = Cipher.getInstance("PBEWithSHA1AndDESede");
        if (c == null) {
            throw new RuntimeException("Cipher.getInstance returned null");
        }
        if (!"PBEWithSHA1AndDESede".equals(c.getAlgorithm())) {
            throw new RuntimeException("Unexpected algorithm: " + c.getAlgorithm());
        }
        if (!"SunJCE".equals(c.getProvider().getName())) {
            throw new RuntimeException("Unexpected provider: " + c.getProvider().getName());
        }
    }
}
