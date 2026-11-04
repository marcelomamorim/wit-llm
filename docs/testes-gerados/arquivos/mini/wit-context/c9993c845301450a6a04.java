/*
 * @test
 * @summary Test sun.security.util.DisabledAlgorithmConstraints.permits(Key) method
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import sun.security.util.DisabledAlgorithmConstraints;
import java.security.Key;

public class DisabledAlgorithmConstraintsWitupTest {
    public static void main(String[] args) throws Exception {
        // Use a known property to create instance
        DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints(DisabledAlgorithmConstraints.PROPERTY_CERTPATH_DISABLED_ALGS);

        // Create a dummy Key implementation for testing
        Key dummyKey = new Key() {
            public String getAlgorithm() { return "RSA"; }
            public String getFormat() { return "X.509"; }
            public byte[] getEncoded() { return new byte[0]; }
        };

        // Test permits returns boolean without exception
        boolean result = dac.permits(dummyKey);

        // Output result to ensure method is exercised
        System.out.println("permits returned: " + result);

        // Test with null key - expect permits to return false (or not throw)
        boolean nullResult = dac.permits(null);
        System.out.println("permits with null key returned: " + nullResult);
    }
}
