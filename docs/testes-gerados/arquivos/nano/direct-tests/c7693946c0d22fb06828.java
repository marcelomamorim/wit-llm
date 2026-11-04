/*
 * @test
 * @summary Tests the permits method of DisabledAlgorithmConstraints for various inputs
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import sun.security.util.DisabledAlgorithmConstraints;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;

public class DisabledAlgorithmConstraintsWitupTest {

    public static void main(String[] args) throws Exception {
        testPermitsWithValidKey();
        testPermitsWithNullKey();
        testPermitsWithEmptyPrimitives();
    }

    private static void testPermitsWithValidKey() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Key publicKey = kp.getPublic();
        DisabledAlgorithmConstraints constraints = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");
        Set<sun.security.util.CryptoPrimitive> primitives = new HashSet<>();
        primitives.add(sun.security.util.CryptoPrimitive.KEY_ENC); // example primitive
        boolean result = constraints.permits(primitives, publicKey);
        // Since constraints are default, permits should return true
        if (!result) {
            throw new AssertionError("permits returned false for valid key and primitives");
        }
    }

    private static void testPermitsWithNullKey() {
        try {
            DisabledAlgorithmConstraints constraints = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");
            Set<sun.security.util.CryptoPrimitive> primitives = new HashSet<>();
            primitives.add(sun.security.util.CryptoPrimitive.SIGNATURE); // example primitive
            boolean result = constraints.permits(primitives, null);
            // Expecting false or exception, depending on implementation
            if (result) {
                throw new AssertionError("permits returned true for null key");
            }
        } catch (Exception e) {
            // Expected exception or handling
        }
    }

    private static void testPermitsWithEmptyPrimitives() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.generateKeyPair();
            Key publicKey = kp.getPublic();
            DisabledAlgorithmConstraints constraints = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");
            Set<sun.security.util.CryptoPrimitive> primitives = new HashSet<>();
            boolean result = constraints.permits(primitives, publicKey);
            // Depending on implementation, may return true or false
            // Here, just check that it does not throw
        } catch (Exception e) {
            throw new AssertionError("Exception thrown in testPermitsWithEmptyPrimitives", e);
        }
    }
}
