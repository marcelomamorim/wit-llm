/*
 * @test
 * @summary Test sun.security.x509.CertificateIssuerName.encode(OutputStream) method
 * @run main CertificateIssuerNameWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;

public class CertificateIssuerNameWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidX500Name();
        testEncodeWithNullX500Name();
    }

    private static void testEncodeWithValidX500Name() throws IOException {
        // Create a simple X500Name with a common name
        X500Name name = new X500Name("CN=Test");
        CertificateIssuerName issuerName = new CertificateIssuerName(name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        issuerName.encode(baos);
        byte[] encoded = baos.toByteArray();

        // The encoded output should not be empty
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // The toString of issuerName should contain the CN
        String str = issuerName.toString();
        if (!str.contains("CN=Test")) {
            throw new RuntimeException("toString() does not contain expected CN");
        }
    }

    private static void testEncodeWithNullX500Name() throws IOException {
        // Create a CertificateIssuerName with null X500Name via subclassing to test encode behavior
        CertificateIssuerName issuerName = new CertificateIssuerName((X500Name) null) {
            // Override to allow encode call with null dnName
            public void encode(java.io.OutputStream out) throws IOException {
                super.encode(out);
            }

            public String toString() {
                return super.toString();
            }
        };

        // toString should return empty string
        if (!"".equals(issuerName.toString())) {
            throw new RuntimeException("toString() with null dnName should return empty string");
        }

        // Encoding with null dnName should throw NullPointerException
        try {
            issuerName.encode(new ByteArrayOutputStream());
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
