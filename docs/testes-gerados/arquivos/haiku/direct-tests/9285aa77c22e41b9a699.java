/*
 * @test
 * @summary Verifies that DistributionPointName(RDN) rejects a null relativeName with the documented message
 * @modules java.base/sun.security.x509
 * @run main DistributionPointNameWitupTest
 */

import sun.security.x509.DistributionPointName;
import sun.security.x509.RDN;

public class DistributionPointNameWitupTest {
    public static void main(String[] args) {
        try {
            new DistributionPointName((RDN) null);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            if (!"relativeName must not be null".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }
}
