/*
 * @test
 * @summary Test encode(OutputStream) method of CertificateSubjectName for normal and edge cases
 * @run main CertificateSubjectNameWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.X500Name;

public class CertificateSubjectNameWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeNormal();
        testEncodeNullOutputStream();
    }

    private static void testEncodeNormal() throws IOException {
        // Create a simple X500Name from a distinguished name string
        String dnStr = "CN=Test User, OU=Test Unit, O=Test Org, L=Test City, ST=Test State, C=US";
        X500Name x500Name = new X500Name(dnStr);
        CertificateSubjectName csn = new CertificateSubjectName(x500Name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csn.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // Basic sanity check: encoded bytes should start with ASN.1 SEQUENCE tag (0x30)
        if (encoded[0] != 0x30) {
            throw new RuntimeException("Encoded output does not start with ASN.1 SEQUENCE tag");
        }
    }

    private static void testEncodeNullOutputStream() {
        // According to the source, encode throws IOException on errors.
        // Passing null OutputStream should cause NullPointerException.
        // We test that the method does not swallow this exception.
        try {
            String dnStr = "CN=Test";
            X500Name x500Name = new X500Name(dnStr);
            CertificateSubjectName csn = new CertificateSubjectName(x500Name);
            csn.encode(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        } catch (IOException ioe) {
            // IOException is declared, but NPE is expected here
            throw new RuntimeException("Unexpected IOException instead of NullPointerException", ioe);
        }
    }
}
