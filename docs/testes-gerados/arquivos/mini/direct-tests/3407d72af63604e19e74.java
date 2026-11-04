/*
 * @test
 * @summary Test sun.security.x509.CertificateValidity.delete method behavior
 * @run main CertificateValidityWitupTest
 */

import sun.security.x509.CertificateValidity;
import java.io.IOException;

public class CertificateValidityWitupTest {
    public static void main(String[] args) throws Exception {
        CertificateValidity cv = new CertificateValidity();

        // Set initial notBefore and notAfter dates via reflection is not allowed,
        // so we test delete behavior by calling delete and checking no exception for valid names

        // Test delete with NOT_BEFORE
        try {
            cv.delete("notBefore");
        } catch (IOException e) {
            throw new RuntimeException("delete(notBefore) should not throw", e);
        }

        // Test delete with NOT_AFTER
        try {
            cv.delete("notAfter");
        } catch (IOException e) {
            throw new RuntimeException("delete(notAfter) should not throw", e);
        }

        // Test delete with case-insensitive names
        try {
            cv.delete("NoTbEfOrE");
            cv.delete("NoTaFtEr");
        } catch (IOException e) {
            throw new RuntimeException("delete(case-insensitive) should not throw", e);
        }

        // Test delete with invalid attribute name throws IOException
        boolean threw = false;
        try {
            cv.delete("invalidName");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("delete(invalidName) did not throw IOException");
        }

        System.out.println("PASS");
    }
}
