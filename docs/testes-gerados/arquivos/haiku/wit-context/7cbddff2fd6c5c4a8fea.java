/*
 * @test
 * @summary Verifies the public DHKeyPairGenerator constructor default initialization and key size validation in initialize
 * @modules java.base/com.sun.crypto.provider
 * @run main DHKeyPairGeneratorWitupTest
 */

import com.sun.crypto.provider.DHKeyPairGenerator;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import javax.crypto.interfaces.DHPublicKey;

public class DHKeyPairGeneratorWitupTest {

    public static void main(String[] args) throws Exception {
        DHKeyPairGenerator gen = new DHKeyPairGenerator();
        KeyPair kp = gen.generateKeyPair();
        if (kp == null || kp.getPublic() == null || kp.getPrivate() == null) {
            throw new RuntimeException("Default constructor produced an incomplete key pair");
        }
        if (!(kp.getPublic() instanceof DHPublicKey)) {
            throw new RuntimeException("Public key is not a DHPublicKey");
        }
        DHPublicKey pub = (DHPublicKey) kp.getPublic();
        if (pub.getParams() == null) {
            throw new RuntimeException("Public key has no DH parameters");
        }
        if (!"DH".equals(pub.getAlgorithm())) {
            throw new RuntimeException("Unexpected algorithm: " + pub.getAlgorithm());
        }

        expectInvalidKeySize(500);
        expectInvalidKeySize(1000);
        expectInvalidKeySize(8256);
    }

    private static void expectInvalidKeySize(int size) {
        DHKeyPairGenerator g = new DHKeyPairGenerator();
        try {
            g.initialize(size, null);
        } catch (InvalidParameterException e) {
            return;
        }
        throw new RuntimeException("Expected InvalidParameterException for key size " + size);
    }
}
