/*
 * @test
 * @summary Verify that the default DH key pair generator (public no-arg constructor, reached via the SunJCE provider) produces usable keys without an explicit initialize call
 * @run main DHKeyPairGeneratorWitupTest
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

public class DHKeyPairGeneratorWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DH", "SunJCE");

        KeyPair kp1 = kpg.generateKeyPair();
        if (kp1 == null || kp1.getPublic() == null || kp1.getPrivate() == null) {
            throw new RuntimeException("Default DH generator returned an incomplete key pair");
        }

        if (!"DH".equals(kp1.getPublic().getAlgorithm())) {
            throw new RuntimeException("Unexpected public key algorithm: " + kp1.getPublic().getAlgorithm());
        }

        DHPublicKey pub = (DHPublicKey) kp1.getPublic();
        DHParameterSpec spec = pub.getParams();
        if (spec == null || spec.getP() == null || spec.getG() == null) {
            throw new RuntimeException("Missing DH parameters in generated public key");
        }

        int bits = spec.getP().bitLength();
        if (bits < 512 || bits > 8192 || (bits % 64) != 0) {
            throw new RuntimeException("Prime size " + bits + " violates DH key size rules");
        }

        KeyPair kp2 = kpg.generateKeyPair();
        if (kp1.getPublic().equals(kp2.getPublic())) {
            throw new RuntimeException("Two generated DH public keys are unexpectedly equal");
        }
    }
}
