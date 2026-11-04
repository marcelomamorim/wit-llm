/*
 * @test
 * @summary Test DistributionPointName constructor with RDN argument for null and valid inputs
 * @run main DistributionPointNameWitupTest
 */

import sun.security.x509.DistributionPointName;
import sun.security.x509.RDN;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullRDN();
        testConstructorWithValidRDN();
        System.out.println("All tests passed.");
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

    private static void testConstructorWithValidRDN() {
        // Create a minimal RDN instance for testing
        // Since RDN constructor and details are not provided, use a minimal valid RDN
        // RDN has a public constructor RDN(DerValue encoding) but no public no-arg constructor
        // We create a dummy DerValue with empty sequence to avoid complexity
        try {
            sun.security.util.DerValue derValue = new sun.security.util.DerValue(new byte[] {0x30, 0x00}); // empty sequence
            RDN rdn = new RDN(derValue);
            DistributionPointName dpn = new DistributionPointName(rdn);
            if (dpn == null) {
                throw new RuntimeException("DistributionPointName constructor returned null");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception creating DistributionPointName with valid RDN", e);
        }
    }
}
