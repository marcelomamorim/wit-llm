/*
 * @test
 * @summary Test sun.security.x509.DistributionPointName.encode method
 * @run main DistributionPointNameWitupTest
 */

import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralNames;
import sun.security.x509.GeneralName;
import sun.security.x509.RDN;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithFullName();
        testEncodeWithRelativeName();
    }

    private static void testEncodeWithFullName() throws IOException {
        // Create a GeneralName with a DNSName
        GeneralName gn = new GeneralName(GeneralName.DNS_NAME, "example.com");
        GeneralNames gns = new GeneralNames();
        gns.add(gn);

        DistributionPointName dpn = new DistributionPointName(gns);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream out = new DerOutputStream(baos);

        dpn.encode(out);

        byte[] encoded = baos.toByteArray();

        // Basic check: encoded data should not be empty
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for fullName");
        }

        // Check that the tag is context-specific constructed with tag 0
        // DER tag byte for [0] constructed context-specific is 0xA0
        if ((encoded[0] & 0xFF) != 0xA0) {
            throw new RuntimeException("Encoded output does not start with expected tag 0xA0 for fullName");
        }
    }

    private static void testEncodeWithRelativeName() throws IOException {
        // Create an RDN with a single AttributeTypeAndValue
        // Use a common OID for CN (2.5.4.3) and a simple value
        sun.security.x509.AttributeTypeAndValue atv = new sun.security.x509.AttributeTypeAndValue(
                new sun.security.util.ObjectIdentifier("2.5.4.3"), "TestCN");
        RDN rdn = new RDN(atv);

        DistributionPointName dpn = new DistributionPointName(rdn);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream out = new DerOutputStream(baos);

        dpn.encode(out);

        byte[] encoded = baos.toByteArray();

        // Basic check: encoded data should not be empty
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for relativeName");
        }

        // Check that the tag is context-specific constructed with tag 1
        // DER tag byte for [1] constructed context-specific is 0xA1
        if ((encoded[0] & 0xFF) != 0xA1) {
            throw new RuntimeException("Encoded output does not start with expected tag 0xA1 for relativeName");
        }
    }
}
