/*
 * @test
 * @summary Test CertificateSubjectName.get(String) method behavior
 * @run main CertificateSubjectNameWitupTest
 */

import java.io.IOException;
import javax.security.auth.x500.X500Principal;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.X500Name;

public class CertificateSubjectNameWitupTest {
    public static void main(String[] args) throws Exception {
        testGetDnName();
        testGetDnPrincipalCaching();
        testGetUnrecognizedNameThrows();
    }

    private static void testGetDnName() throws IOException {
        X500Name x500Name = new X500Name("CN=Test Subject");
        CertificateSubjectName csn = new CertificateSubjectName(x500Name);

        Object result = csn.get(CertificateSubjectName.DN_NAME);
        if (!(result instanceof X500Name)) {
            throw new AssertionError("Expected instance of X500Name for DN_NAME");
        }
        if (!result.equals(x500Name)) {
            throw new AssertionError("Returned X500Name does not match the original");
        }
    }

    private static void testGetDnPrincipalCaching() throws IOException {
        X500Name x500Name = new X500Name("CN=Test Subject");
        CertificateSubjectName csn = new CertificateSubjectName(x500Name);

        // First call should create and cache dnPrincipal
        Object firstCall = csn.get(CertificateSubjectName.DN_PRINCIPAL);
        if (!(firstCall instanceof X500Principal)) {
            throw new AssertionError("Expected instance of X500Principal for DN_PRINCIPAL");
        }

        // Second call should return the same cached instance
        Object secondCall = csn.get(CertificateSubjectName.DN_PRINCIPAL);
        if (firstCall != secondCall) {
            throw new AssertionError("DN_PRINCIPAL not cached properly");
        }
    }

    private static void testGetUnrecognizedNameThrows() {
        X500Name x500Name = new X500Name("CN=Test Subject");
        CertificateSubjectName csn = new CertificateSubjectName(x500Name);

        try {
            csn.get("invalidName");
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new AssertionError("IOException message unexpected: " + msg);
            }
        }
    }
}
