/*
 * @test
 * @summary Test sun.security.x509.DNSName constructor with DerValue input
 * @run main DNSNameWitupTest
 */

import sun.security.x509.DNSName;
import sun.security.util.DerValue;
import java.io.IOException;

public class DNSNameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidDerValue();
    }

    private static void testValidDerValue() throws Exception {
        // Create a DerValue with tag 22 (IA5String) and valid DNS name
        // The tag 22 is the IA5String tag in DER encoding
        byte[] encoded = new byte[] { 22, 11, 'e', 'x', 'a', 'm', 'p', 'l', 'e', '.', 'c', 'o', 'm' };
        DerValue derValue = new DerValue(encoded);
        DNSName dnsName = new DNSName(derValue);
        // If no exception, test passes
        if (!"example.com".equals(dnsName.toString())) {
            throw new RuntimeException("DNSName string mismatch");
        }
    }

    private static void testInvalidDerValue() {
        // Create a DerValue with invalid tag (not 22) to trigger IOException
        // Use tag 20 (GeneralString) which is invalid for DNSName
        byte[] encoded = new byte[] { 20, 11, 'e', 'x', 'a', 'm', 'p', 'l', 'e', '.', 'c', 'o', 'm' };
        try {
            DerValue derValue = new DerValue(encoded);
            new DNSName(derValue);
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
