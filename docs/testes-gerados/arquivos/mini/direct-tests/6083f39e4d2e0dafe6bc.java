/*
 * @test
 * @summary Test LegacyAlgorithmConstraints.permits(Set<CryptoPrimitive>, Key) always returns true
 * @run main LegacyAlgorithmConstraintsWitupTest
 */

import sun.security.util.LegacyAlgorithmConstraints;
import sun.security.util.AlgorithmDecomposer;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.security.CryptoPrimitive;

public class LegacyAlgorithmConstraintsWitupTest {
    public static void main(String[] args) throws Exception {
        // Use the known property for legacy algorithms (may be empty or null)
        LegacyAlgorithmConstraints constraints = new LegacyAlgorithmConstraints(LegacyAlgorithmConstraints.PROPERTY_TLS_LEGACY_ALGS, new AlgorithmDecomposer() {
            @Override
            public Set<String> decompose(String algorithm) {
                return Collections.emptySet();
            }
        });

        // Prepare empty and non-empty sets of CryptoPrimitive
        Set<CryptoPrimitive> emptyPrimitives = Collections.emptySet();
        Set<CryptoPrimitive> somePrimitives = new HashSet<CryptoPrimitive>();
        somePrimitives.add(CryptoPrimitive.KEY_AGREEMENT);

        // Prepare dummy Key implementations
        Key dummyKey = new DummyKey("DummyAlgorithm");
        Key anotherKey = new DummyKey("AnotherAlgorithm");

        // Test permits returns true for empty primitives and dummyKey
        if (!constraints.permits(emptyPrimitives, dummyKey)) {
            throw new RuntimeException("permits returned false for empty primitives and dummyKey");
        }

        // Test permits returns true for some primitives and dummyKey
        if (!constraints.permits(somePrimitives, dummyKey)) {
            throw new RuntimeException("permits returned false for some primitives and dummyKey");
        }

        // Test permits returns true for empty primitives and anotherKey
        if (!constraints.permits(emptyPrimitives, anotherKey)) {
            throw new RuntimeException("permits returned false for empty primitives and anotherKey");
        }

        // Test permits returns true for some primitives and anotherKey
        if (!constraints.permits(somePrimitives, anotherKey)) {
            throw new RuntimeException("permits returned false for some primitives and anotherKey");
        }

        System.out.println("All tests passed.");
    }

    // Minimal dummy Key implementation for testing
    private static class DummyKey implements Key {
        private static final long serialVersionUID = 1L;
        private final String algorithm;

        DummyKey(String algorithm) {
            this.algorithm = algorithm;
        }

        @Override
        public String getAlgorithm() {
            return algorithm;
        }

        @Override
        public String getFormat() {
            return null;
        }

        @Override
        public byte[] getEncoded() {
            return null;
        }
    }
}
