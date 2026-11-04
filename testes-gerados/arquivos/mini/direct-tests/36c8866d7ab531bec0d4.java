/*
 * @test
 * @summary Test sun.security.x509.X500Name.getEncodedInternal() for correct encoding and caching
 * @run main sun.security.x509.X500NameWitupTest
 */

import sun.security.x509.X500Name;
import sun.security.x509.RDN;
import sun.security.x509.AttributeTypeAndValue;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;
import java.io.IOException;

public class X500NameWitupTest {
    public static void main(String[] args) throws Exception {
        testGetEncodedInternalCaching();
        testGetEncodedInternalContent();
    }

    private static void testGetEncodedInternalCaching() throws IOException {
        // Create X500Name with one RDN
        AttributeTypeAndValue atv = new AttributeTypeAndValue(
            new ObjectIdentifier(new int[] {2, 5, 4, 3}), // commonName OID
            new DerValue(DerValue.tag_PrintableString, "Test User".getBytes("UTF8"))
        );
        RDN rdn = new RDN(new AttributeTypeAndValue[] {atv});
        X500Name name = new X500Name(new RDN[] {rdn});

        // First call to getEncodedInternal should produce non-null byte array
        byte[] encoded1 = name.getEncodedInternal();
        if (encoded1 == null || encoded1.length == 0) {
            throw new RuntimeException("getEncodedInternal returned null or empty on first call");
        }

        // Second call should return the same byte array instance (cached)
        byte[] encoded2 = name.getEncodedInternal();
        if (encoded1 != encoded2) {
            throw new RuntimeException("getEncodedInternal did not cache the encoded byte array");
        }
    }

    private static void testGetEncodedInternalContent() throws IOException {
        // Create X500Name with two RDNs
        AttributeTypeAndValue atv1 = new AttributeTypeAndValue(
            new ObjectIdentifier(new int[] {2, 5, 4, 3}), // commonName
            new DerValue(DerValue.tag_PrintableString, "Alice".getBytes("UTF8"))
        );
        AttributeTypeAndValue atv2 = new AttributeTypeAndValue(
            new ObjectIdentifier(new int[] {2, 5, 4, 10}), // organizationName
            new DerValue(DerValue.tag_PrintableString, "ExampleOrg".getBytes("UTF8"))
        );
        RDN rdn1 = new RDN(new AttributeTypeAndValue[] {atv1});
        RDN rdn2 = new RDN(new AttributeTypeAndValue[] {atv2});
        X500Name name = new X500Name(new RDN[] {rdn1, rdn2});

        byte[] encoded = name.getEncodedInternal();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("getEncodedInternal returned null or empty for multiple RDNs");
        }

        // Basic sanity check: encoded data should start with SEQUENCE tag (0x30)
        if (encoded[0] != 0x30) {
            throw new RuntimeException("Encoded data does not start with SEQUENCE tag");
        }
    }
}
