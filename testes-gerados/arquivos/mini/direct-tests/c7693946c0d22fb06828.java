/*
 * @test
 * @summary Test sun.security.util.DisabledAlgorithmConstraints.permits(Set<CryptoPrimitive>, Key) method
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
        // Create DisabledAlgorithmConstraints instance with known property
        DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints(DisabledAlgorithmConstraints.PROPERTY_CERTPATH_DISABLED_ALGS, new AlgorithmDecomposer());

        // Test 1: permits with empty primitives and null key should return true (no constraints)
        Set<CryptoPrimitive> emptyPrimitives = Collections.emptySet();
        boolean result1 = dac.permits(emptyPrimitives, null);
        if (!result1) {
            throw new RuntimeException("Test 1 failed: permits with empty primitives and null key should return true");
        }

        // Test 2: permits with non-empty primitives and null key
        Set<CryptoPrimitive> primitives = new HashSet<>();
        primitives.add(CryptoPrimitive.KEY_AGREEMENT);
        boolean result2 = dac.permits(primitives, null);
        // Result can be true or false depending on disabled algorithms, just call to check no exception

        // Test 3: permits with empty primitives and dummy Key implementation
        Key dummyKey = new Key() {
            private static final long serialVersionUID = 1L;
            public String getAlgorithm() { return "RSA"; }
            public String getFormat() { return "X.509"; }
            public byte[] getEncoded() { return new byte[0]; }
        };
        boolean result3 = dac.permits(Collections.<CryptoPrimitive>emptySet(), dummyKey);
        // Result can be true or false depending on disabled algorithms, just call to check no exception

        // Test 4: permits with primitives and dummy Key
        boolean result4 = dac.permits(primitives, dummyKey);
        // Result can be true or false depending on disabled algorithms, just call to check no exception

        // If reached here without exceptions, test passed
        System.out.println("All tests passed.");
    }
}
