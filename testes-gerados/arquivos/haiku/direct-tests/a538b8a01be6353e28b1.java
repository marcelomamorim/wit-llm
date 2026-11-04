/*
 * @test
 * @summary Verifies that DSAKeyPairGenerator.initialize(int, SecureRandom) produces DSA keys with the requested prime size
 * @run main DSAKeyPairGeneratorWitupTest
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.DSAPublicKey;

public class DSAKeyPairGeneratorWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA", "SUN");
        SecureRandom random = new SecureRandom();

        kpg.initialize(1024, random);
        KeyPair kp1024 = kpg.generateKeyPair();
        DSAPublicKey pub1024 = (DSAPublicKey) kp1024.getPublic();
        if (pub1024.getParams().getP().bitLength() != 1024) {
            throw new RuntimeException("Expected 1024-bit P, got "
                + pub1024.getParams().getP().bitLength());
        }
        if (pub1024.getParams().getQ().bitLength() != 160) {
            throw new RuntimeException("Expected 160-bit Q for 1024-bit key, got "
                + pub1024.getParams().getQ().bitLength());
        }

        kpg.initialize(2048, random);
        KeyPair kp2048 = kpg.generateKeyPair();
        DSAPublicKey pub2048 = (DSAPublicKey) kp2048.getPublic();
        if (pub2048.getParams().getP().bitLength() != 2048) {
            throw new RuntimeException("Expected 2048-bit P, got "
                + pub2048.getParams().getP().bitLength());
        }
    }
}
