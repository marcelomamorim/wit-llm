/*
 * @test
 * @summary Test CertificateSubjectName.delete(String) method behavior
 * @run main CertificateSubjectNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.X500Name;

public class CertificateSubjectNameWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        // Create a dummy X500Name instance for construction
        X500Name name = new X500Name();
        CertificateSubjectName csn = new CertificateSubjectName(name);

        // Call delete with "dname" (case insensitive) should clear dnName and dnPrincipal
        csn.delete("dname");

        // After deletion, toString() should return empty string since dnName is null
        String s = csn.toString();
        if (!"".equals(s)) {
            throw new RuntimeException("Expected empty string after delete with 'dname', got: " + s);
        }
    }

    private static void testDeleteWithInvalidName() {
        try {
            X500Name name = new X500Name();
            CertificateSubjectName csn = new CertificateSubjectName(name);

            // Call delete with invalid name should throw IOException
            csn.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e, e);
        }
    }
}
