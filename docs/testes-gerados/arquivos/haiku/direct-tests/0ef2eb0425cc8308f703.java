/*
 * @test
 * @summary Verifies KeyPairGenerator.initialize(int, SecureRandom) initializes the generator for later key pair generation and rejects an invalid key size.
 * @run main KeyPairGeneratorWitupTest
 */

import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class KeyPairGeneratorWitupTest {

    public static void main(String[] args) throws Exception {
        testInitializeThenGenerate();
        testInvalidKeySizeRejected();
    }

    static void testInitializeThenGenerate() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        kpg.initialize(1024, random);
        KeyPair kp = kpg.generateKeyPair();
        if (kp == null) {
            throw new RuntimeException("generateKeyPair returned null");
        }
        if (kp.getPublic() == null || kp.getPrivate() == null) {
            throw new RuntimeException("key pair has null component");
        }
        if (!"RSA".equals(kp.getPublic().getAlgorithm())) {
            throw new RuntimeException("unexpected public key algorithm: "
                    + kp.getPublic().getAlgorithm());
        }
    }

    static void testInvalidKeySizeRejected() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        boolean thrown = false;
        try {
            kpg.initialize(-1, new SecureRandom());
        } catch (InvalidParameterException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected InvalidParameterException for keysize -1");
        }
    }
}
