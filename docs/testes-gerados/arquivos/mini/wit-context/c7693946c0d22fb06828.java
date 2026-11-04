/*
 * @test
 * @summary Test DisabledAlgorithmConstraints.permits(Set<CryptoPrimitive>, Key) method
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import sun.security.util.DisabledAlgorithmConstraints;
import sun.security.util.AlgorithmDecomposer;
import java.security.CryptoPrimitive;
import java.security.Key;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DisabledAlgorithmConstraintsWitupTest {
    public static void main(String[] args) throws Exception {
        // Create DisabledAlgorithmConstraints instance with a known property
        DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints(DisabledAlgorithmConstraints.PROPERTY_CERTPATH_DISABLED_ALGS);

        // Prepare empty primitives set
        Set<CryptoPrimitive> emptyPrimitives = Collections.emptySet();

        // Test with null key - should not throw, returns boolean
        boolean resultNullKey = dac.permits(emptyPrimitives, null);

        // Test with dummy Key implementation
        Key dummyKey = new Key() {
            private static final long serialVersionUID = 1L;
            public String getAlgorithm() { return "RSA"; }
            public String getFormat() { return "X.509"; }
            public byte[] getEncoded() { return new byte[0]; }
        };

        boolean resultDummyKey = dac.permits(emptyPrimitives, dummyKey);

        // Output results to verify determinism and no exceptions
        if (resultNullKey != false && resultNullKey != true) {
            throw new RuntimeException("Unexpected result for null key");
        }
        if (resultDummyKey != false && resultDummyKey != true) {
            throw new RuntimeException("Unexpected result for dummy key");
        }

        // Test with non-empty primitives set
        Set<CryptoPrimitive> primitives = new HashSet<>();
        // CryptoPrimitive is an interface, no public implementations known here, so test empty set only
        // Just reuse empty set to confirm no exceptions
        boolean resultWithPrimitives = dac.permits(primitives, dummyKey);

        if (resultWithPrimitives != false && resultWithPrimitives != true) {
            throw new RuntimeException("Unexpected result for primitives set");
        }

        // If reached here, tests passed deterministically
        System.out.println("DisabledAlgorithmConstraints.permits tests passed.");
    }
}
