/*
 * @test
 * @summary Test LegacyAlgorithmConstraints.permits(Set<CryptoPrimitive>, Key) always returns true
 * @run main LegacyAlgorithmConstraintsWitupTest
 */

import sun.security.util.LegacyAlgorithmConstraints;
import sun.security.util.AlgorithmDecomposer;
import java.security.CryptoPrimitive;
import java.security.Key;
import java.util.Collections;
import java.util.Set;

public class LegacyAlgorithmConstraintsWitupTest {
    public static void main(String[] args) throws Exception {
        // Use the known public constructor with a dummy property name and null decomposer
        LegacyAlgorithmConstraints constraints = new LegacyAlgorithmConstraints("jdk.tls.legacyAlgorithms", null);

        // Create an empty set of CryptoPrimitive
        Set<CryptoPrimitive> emptyPrimitives = Collections.emptySet();

        // Create a dummy Key implementation for testing
        Key dummyKey = new Key() {
            private static final long serialVersionUID = 1L;
            public String getAlgorithm() { return "DummyAlgorithm"; }
            public String getFormat() { return "RAW"; }
            public byte[] getEncoded() { return new byte[0]; }
        };

        // The permits method with (Set<CryptoPrimitive>, Key) always returns true
        boolean result = constraints.permits(emptyPrimitives, dummyKey);

        if (!result) {
            throw new AssertionError("permits(Set<CryptoPrimitive>, Key) should return true but returned false");
        }

        System.out.println("Test passed: permits(Set<CryptoPrimitive>, Key) returns true as expected.");
    }
}
