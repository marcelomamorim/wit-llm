/*
 * @test
 * @summary Test permits method of DisabledAlgorithmConstraints for different Key inputs
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import sun.security.util.DisabledAlgorithmConstraints;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class DisabledAlgorithmConstraintsWitupTest {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        testPermitsWithValidKey();
        testPermitsWithNullKey();
    }

    private static void testPermitsWithValidKey() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Key key = kp.getPublic();
        DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");
        boolean result = dac.permits(key);
        System.out.println("permits with valid key: " + result);
    }

    private static void testPermitsWithNullKey() {
        DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");
        boolean result = dac.permits(null);
        System.out.println("permits with null key: " + result);
    }
}
