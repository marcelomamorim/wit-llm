/*
 * @test
 * @summary Test sun.security.util.DisabledAlgorithmConstraints.permits(Key) method
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import java.security.Key;
import sun.security.util.DisabledAlgorithmConstraints;

public class DisabledAlgorithmConstraintsWitupTest {
    public static void main(String[] args) {
        // Create instance with a known property name
        DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints(DisabledAlgorithmConstraints.PROPERTY_CERTPATH_DISABLED_ALGS);

        // Create a dummy Key implementation for testing
        Key dummyKey = new Key() {
            private static final long serialVersionUID = 1L;
            public String getAlgorithm() { return "DummyAlgorithm"; }
            public String getFormat() { return "RAW"; }
            public byte[] getEncoded() { return new byte[0]; }
        };

        // The permits(Key) method currently returns false always
        boolean result = dac.permits(dummyKey);

        if (result != false) {
            throw new RuntimeException("permits(Key) should return false but returned " + result);
        }

        System.out.println("Test passed: permits(Key) returns false as expected.");
    }
}
