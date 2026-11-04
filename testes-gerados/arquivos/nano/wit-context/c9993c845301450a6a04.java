/*
 * @test
 * @summary Test for sun.security.util.DisabledAlgorithmConstraints.permits(Key)
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import sun.security.util.DisabledAlgorithmConstraints;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class DisabledAlgorithmConstraintsWitupTest {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        testPermitsReturnsTrueForValidKey();
        testPermitsReturnsFalseForNullKey();
    }

    private static void testPermitsReturnsTrueForValidKey() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");
        boolean result = dac.permits(kp.getPublic());
        if (!result) {
            throw new AssertionError("Expected permits to return true for valid key");
        }
    }

    private static void testPermitsReturnsFalseForNullKey() {
        DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");
        try {
            dac.permits(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
