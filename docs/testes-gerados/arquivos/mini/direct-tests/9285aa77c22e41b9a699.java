/*
 * @test
 * @summary Test DistributionPointName constructor with RDN argument
 * @run main DistributionPointNameWitupTest
 */

import sun.security.x509.DistributionPointName;
import sun.security.x509.RDN;

public class DistributionPointNameWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidRDN();
        testConstructorWithNullRDN();
    }

    private static void testConstructorWithValidRDN() {
        // Create a minimal RDN instance for testing
        RDN rdn = new RDN();
        DistributionPointName dpn = new DistributionPointName(rdn);
        if (dpn == null) {
            throw new RuntimeException("DistributionPointName constructor returned null");
        }
    }

    private static void testConstructorWithNullRDN() {
        try {
            new DistributionPointName((RDN) null);
            throw new RuntimeException("Expected IllegalArgumentException for null RDN");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("relativeName must not be null")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }
}
