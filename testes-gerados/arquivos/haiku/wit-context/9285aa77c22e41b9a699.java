/*
 * @test
 * @summary Verify that DistributionPointName(RDN) rejects a null relativeName with IllegalArgumentException
 * @modules java.base/sun.security.x509
 * @run main DistributionPointNameWitupTest
 */

import sun.security.x509.DistributionPointName;
import sun.security.x509.RDN;

public class DistributionPointNameWitupTest {
    public static void main(String[] args) {
        try {
            new DistributionPointName((RDN) null);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException expected) {
            if (expected.getMessage() == null || !expected.getMessage().contains("relativeName")) {
                throw new RuntimeException("Unexpected message: " + expected.getMessage());
            }
        }
    }
}
