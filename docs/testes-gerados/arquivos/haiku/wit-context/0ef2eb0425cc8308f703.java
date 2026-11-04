/*
 * @test
 * @summary KeyPairGenerator.initialize(int, SecureRandom) rejects an invalid RSA key size and accepts a valid one
 * @run main KeyPairGeneratorWitupTest
 */

import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class KeyPairGeneratorWitupTest {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();

        boolean thrown = false;
        try {
            kpg.initialize(16, random);
        } catch (InvalidParameterException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected InvalidParameterException for RSA keysize 16");
        }

        kpg.initialize(1024, random);
        KeyPair kp = kpg.generateKeyPair();
        if (!"RSA".equals(kp.getPublic().getAlgorithm())) {
            throw new AssertionError("Unexpected public key algorithm: " + kp.getPublic().getAlgorithm());
        }
    }
}
