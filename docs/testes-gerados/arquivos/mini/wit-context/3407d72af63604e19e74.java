/*
 * @test
 * @summary Test sun.security.x509.CertificateValidity.delete(String) method
 * @run main CertificateValidityWitupTest
 */

import sun.security.x509.CertificateValidity;
import java.io.IOException;

public class CertificateValidityWitupTest {
    public static void main(String[] args) throws Exception {
        CertificateValidity cv = new CertificateValidity();

        // Test deleting NOT_BEFORE attribute
        cv.delete("notBefore");

        // Test deleting NOT_AFTER attribute
        cv.delete("notAfter");

        // Test deleting with different case (case insensitive check)
        cv.delete("NoTbEfOrE");
        cv.delete("NoTaFtEr");

        // Test deleting unrecognized attribute triggers IOException
        boolean threw = false;
        try {
            cv.delete("invalidName");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException was not thrown for invalid attribute name");
        }
    }
}
