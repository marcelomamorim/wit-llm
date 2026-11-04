/*
 * @test
 * @summary Test CertificateSubjectName.delete(String) method behavior
 * @run main CertificateSubjectNameWitupTest
 */

import sun.security.x509.CertificateSubjectName;
import sun.security.x509.X500Name;
import java.io.IOException;

public class CertificateSubjectNameWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteValidName();
        testDeleteInvalidName();
    }

    private static void testDeleteValidName() throws Exception {
        X500Name x500Name = new X500Name("CN=Test Subject");
        CertificateSubjectName csn = new CertificateSubjectName(x500Name);

        // Before delete, toString should return non-empty string
        String before = csn.toString();
        if (before == null || before.isEmpty()) {
            throw new RuntimeException("toString() returned empty before delete");
        }

        // Delete with DN_NAME should clear dnName and dnPrincipal
        csn.delete("dname");

        // After delete, toString should return empty string
        String after = csn.toString();
        if (!"".equals(after)) {
            throw new RuntimeException("toString() did not return empty after delete");
        }
    }

    private static void testDeleteInvalidName() {
        X500Name x500Name = new X500Name("CN=Test Subject");
        CertificateSubjectName csn = new CertificateSubjectName(x500Name);

        try {
            csn.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
