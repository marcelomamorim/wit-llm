/*
 * @test
 * @summary Test sun.security.util.DisabledAlgorithmConstraints.permits(Key) always returns false
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import java.security.Key;
import sun.security.util.DisabledAlgorithmConstraints;

public class DisabledAlgorithmConstraintsWitupTest {
    public static void main(String[] args) {
        DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints(DisabledAlgorithmConstraints.PROPERTY_CERTPATH_DISABLED_ALGS);

        // Test with null key
        boolean resultNull = dac.permits(null);
        if (resultNull != false) {
            throw new RuntimeException("permits(null) should return false but returned " + resultNull);
        }

        // Test with a dummy Key implementation
        Key dummyKey = new Key() {
            public String getAlgorithm() { return "DummyAlg"; }
            public String getFormat() { return "DummyFormat"; }
            public byte[] getEncoded() { return new byte[0]; }
        };

        boolean resultDummy = dac.permits(dummyKey);
        if (resultDummy != false) {
            throw new RuntimeException("permits(dummyKey) should return false but returned " + resultDummy);
        }

        System.out.println("All tests passed.");
    }
}
