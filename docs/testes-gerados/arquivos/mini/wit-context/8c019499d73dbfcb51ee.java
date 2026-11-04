/*
 * @test
 * @summary Test sun.security.x509.DistributionPointName.encode(DerOutputStream) for normal and edge cases
 * @run main DistributionPointNameWitupTest
 */

import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNames;
import sun.security.x509.RDN;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithFullName();
        testEncodeWithRelativeName();
        testEncodeNullOutputStream();
    }

    private static void testEncodeWithFullName() throws IOException {
        // Create a GeneralName with a DNSName
        GeneralName gn = new GeneralName(GeneralName.DNS_NAME, "example.com");
        GeneralNames gns = new GeneralNames();
        gns.add(gn);

        DistributionPointName dpn = new DistributionPointName(gns);

        DerOutputStream out = new DerOutputStream();
        dpn.encode(out);

        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for fullName");
        }
    }

    private static void testEncodeWithRelativeName() throws IOException {
        // Create an RDN with a single AttributeTypeAndValue
        // Use a simple RDN with a common attribute type and value
        RDN rdn = new RDN();
        rdn.add(new sun.security.x509.AttributeTypeAndValue(
                new sun.security.util.ObjectIdentifier(new int[] {2,5,4,3}), // commonName OID
                new sun.security.x509.DirectoryString("relativeNameTest")
        ));

        DistributionPointName dpn = new DistributionPointName(rdn);

        DerOutputStream out = new DerOutputStream();
        dpn.encode(out);

        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for relativeName");
        }
    }

    private static void testEncodeNullOutputStream() {
        try {
            GeneralName gn = new GeneralName(GeneralName.DNS_NAME, "example.com");
            GeneralNames gns = new GeneralNames();
            gns.add(gn);
            DistributionPointName dpn = new DistributionPointName(gns);
            dpn.encode(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            // IOException is declared but NullPointerException expected for null out
            throw new RuntimeException("Unexpected IOException thrown", e);
        }
    }
}
