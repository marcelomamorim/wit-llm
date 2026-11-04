/*
 * @test
 * @summary Test sun.security.x509.URIName.nameConstraint method behavior
 * @run main URINameWitupTest
 */

import sun.security.x509.URIName;
import sun.security.util.DerValue;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class URINameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidHostWithoutLeadingDot();
        testValidHostWithLeadingDot();
        testInvalidSchemePresent();
        testInvalidHostDNSNameThrowsIOException();
    }

    private static void testValidHostWithoutLeadingDot() throws Exception {
        // Create DerValue with IA5String containing host without leading dot
        DerValue derValue = new DerValue(DerValue.tag_IA5String, "example.com".getBytes("US-ASCII"));
        URIName uriName = URIName.nameConstraint(derValue);
        if (uriName == null) {
            throw new RuntimeException("Expected non-null URIName for valid host without leading dot");
        }
        // The host field should be "example.com"
        if (!"example.com".equals(uriName.getHost())) {
            throw new RuntimeException("Host mismatch: expected example.com but got " + uriName.getHost());
        }
    }

    private static void testValidHostWithLeadingDot() throws Exception {
        // Create DerValue with IA5String containing host with leading dot
        DerValue derValue = new DerValue(DerValue.tag_IA5String, ".example.com".getBytes("US-ASCII"));
        URIName uriName = URIName.nameConstraint(derValue);
        if (uriName == null) {
            throw new RuntimeException("Expected non-null URIName for valid host with leading dot");
        }
        // The host field should be ".example.com"
        if (!".example.com".equals(uriName.getHost())) {
            throw new RuntimeException("Host mismatch: expected .example.com but got " + uriName.getHost());
        }
    }

    private static void testInvalidSchemePresent() {
        // Create DerValue with IA5String containing a URI with scheme
        try {
            DerValue derValue = new DerValue(DerValue.tag_IA5String, "http://example.com".getBytes("US-ASCII"));
            URIName.nameConstraint(derValue);
            throw new RuntimeException("Expected IOException for URI with scheme");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("invalid URI name constraint (should not include scheme):")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testInvalidHostDNSNameThrowsIOException() {
        // Create DerValue with IA5String containing host that causes DNSName constructor to throw IOException
        // We simulate this by passing an invalid DNS name string that DNSName rejects
        // Since DNSName is not public, we rely on the fact that DNSName constructor throws IOException on invalid input
        // Use a string with invalid characters for DNSName
        try {
            DerValue derValue = new DerValue(DerValue.tag_IA5String, "invalid_host_name_with_@_char".getBytes("US-ASCII"));
            URIName.nameConstraint(derValue);
            throw new RuntimeException("Expected IOException for invalid DNSName host");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("invalid URI name constraint:")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}