/*
 * @test
 * @summary Test encode method of CertificateSubjectName
 * @run main CertificateSubjectNameWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.X500Name;
import sun.security.util.DerOutputStream;

public class CertificateSubjectNameWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeProducesNonEmptyOutput();
        testEncodeWithNullNameThrows();
    }

    private static void testEncodeProducesNonEmptyOutput() throws IOException {
        // Create a simple X500Name with a common name
        X500Name name = new X500Name("CN=Test");
        CertificateSubjectName csn = new CertificateSubjectName(name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csn.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeWithNullNameThrows() {
        try {
            CertificateSubjectName csn = new CertificateSubjectName((X500Name) null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            csn.encode(baos);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException | IOException e) {
            // Expected: dnName is null, so dnName.encode(tmp) should throw NPE or IOException
        }
    }
}
