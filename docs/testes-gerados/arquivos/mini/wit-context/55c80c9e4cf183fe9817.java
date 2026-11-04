/*
 * @test
 * @summary Test sun.security.x509.CertificateIssuerName.encode(OutputStream) method
 * @run main CertificateIssuerNameWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;
import sun.security.util.DerOutputStream;

public class CertificateIssuerNameWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidX500Name();
    }

    private static void testEncodeWithValidX500Name() throws IOException {
        // Create a simple X500Name with a common name
        X500Name x500Name = new X500Name("CN=Test User");
        CertificateIssuerName issuerName = new CertificateIssuerName(x500Name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        issuerName.encode(baos);

        byte[] encodedBytes = baos.toByteArray();

        // The encoded byte array should not be empty
        if (encodedBytes == null || encodedBytes.length == 0) {
            throw new AssertionError("Encoded output is empty");
        }

        // The toString() should return a non-empty string containing the CN
        String str = issuerName.toString();
        if (str == null || !str.contains("CN=Test User")) {
            throw new AssertionError("toString() output unexpected: " + str);
        }
    }
}
