/*
 * @test
 * @summary Test DistributionPointName constructor with GeneralNames argument
 * @run main DistributionPointNameWitupTest
 */

import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralNames;
import sun.security.x509.GeneralName;
import sun.security.x509.DNSName;

public class DistributionPointNameWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorRejectsNull();
        testConstructorAcceptsValidGeneralNames();
        System.out.println("All tests passed.");
    }

    private static void testConstructorRejectsNull() {
        try {
            new DistributionPointName((GeneralNames) null);
            throw new RuntimeException("Expected IllegalArgumentException for null fullName");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("fullName must not be null")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }

    private static void testConstructorAcceptsValidGeneralNames() throws Exception {
        // Create a GeneralNames with one DNSName
        GeneralName dnsName = new DNSName("example.com");
        GeneralNames generalNames = new GeneralNames(dnsName);

        DistributionPointName dpn = new DistributionPointName(generalNames);
        if (dpn == null) {
            throw new RuntimeException("DistributionPointName constructor returned null");
        }
    }
}
