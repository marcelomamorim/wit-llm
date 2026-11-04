/* @test    @summary Test encode method of CertificateIssuerName for correct DER encoding behavior    @run main CertificateIssuerNameWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.security.auth.x500.X500Principal;
import sun.security.x509.CertificateIssuerName;
import sun.security.util.DerValue;
import sun.security.util.DerOutputStream;

public class CertificateIssuerNameWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithValidX500Name();
        testEncodeWithNullName();
    }

    private static void testEncodeWithValidX500Name() throws IOException {
        X500Principal principal = new X500Principal("CN=Test, OU=Unit, O=Organization, L=City, ST=State, C=US");
        DerValue derValue = new DerValue(principal.getEncoded());
        CertificateIssuerName issuerName = new CertificateIssuerName(new sun.security.x509.X500Name(derValue));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        issuerName.encode(baos);
        byte[] encodedBytes = baos.toByteArray();
        // Verify that the output is not empty and contains the DER encoding
        if (encodedBytes == null || encodedBytes.length == 0) {
            throw new AssertionError("Encoding produced empty byte array");
        }
        // Optionally, verify that the encoded bytes start with expected DER tag (0x30 for SEQUENCE)
        if (encodedBytes[0] != 0x30) {
            throw new AssertionError("Encoded DER does not start with SEQUENCE tag");
        }
        System.out.println("testEncodeWithValidX500Name passed.");
    }

    private static void testEncodeWithNullName() {
        try {
            CertificateIssuerName issuerName = new CertificateIssuerName((sun.security.x509.X500Name) null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            issuerName.encode(baos);
            throw new AssertionError("Expected NullPointerException or IOException not thrown");
        } catch (NullPointerException | IOException e) {
            System.out.println("testEncodeWithNullName passed with expected exception: " + e.getClass().getSimpleName());
        }
    }
}
