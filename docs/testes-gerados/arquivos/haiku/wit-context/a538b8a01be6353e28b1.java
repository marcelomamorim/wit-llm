/*
 * @test
 * @summary Verify DSA KeyPairGenerator initialize(int, SecureRandom) accepts supported sizes and rejects unsupported ones
 * @run main DSAKeyPairGeneratorWitupTest
 */
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;

public class DSAKeyPairGeneratorWitupTest {

    public static void main(String[] args) throws Exception {
        SecureRandom random = new SecureRandom();
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA", "SUN");

        int[] supported = {512, 576, 1024, 2048, 3072};
        for (int size : supported) {
            kpg.initialize(size, random);
        }

        kpg.initialize(1024, random);
        KeyPair kp = kpg.generateKeyPair();
        DSAPublicKey pub = (DSAPublicKey) kp.getPublic();
        DSAParams params = pub.getParams();
        if (params.getP().bitLength() != 1024) {
            throw new RuntimeException("Unexpected prime size: " + params.getP().bitLength());
        }

        int[] unsupported = {256, 1000, 1536, 4096};
        for (int size : unsupported) {
            expectRejected(kpg, size, random);
        }
    }

    private static void expectRejected(KeyPairGenerator kpg, int size, SecureRandom random) {
        try {
            kpg.initialize(size, random);
        } catch (InvalidParameterException e) {
            return;
        }
        throw new RuntimeException("Expected InvalidParameterException for size " + size);
    }
}
