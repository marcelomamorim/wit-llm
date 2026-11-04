/*
 * @test
 * @summary Test sun.security.x509.CertificateValidity.get(String) method
 * @run main CertificateValidityWitupTest
 */

import sun.security.x509.CertificateValidity;
import java.io.IOException;
import java.util.Date;

public class CertificateValidityWitupTest {
    public static void main(String[] args) throws Exception {
        CertificateValidity cv = new CertificateValidity();

        // Setup dates for constructor
        Date before = new Date(1000000000L); // Arbitrary fixed date
        Date after = new Date(2000000000L);  // Arbitrary fixed date after 'before'

        // Use constructor with dates to set internal state
        CertificateValidity cv2 = new CertificateValidity(before, after);

        // Test get with NOT_BEFORE
        Date nb = cv2.get("notBefore");
        if (!nb.equals(before)) {
            throw new RuntimeException("get(\"notBefore\") did not return expected date");
        }

        // Test get with NOT_AFTER
        Date na = cv2.get("notAfter");
        if (!na.equals(after)) {
            throw new RuntimeException("get(\"notAfter\") did not return expected date");
        }

        // Test get with invalid attribute name - expect IOException
        boolean threw = false;
        try {
            cv2.get("invalidName");
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("get() did not throw IOException for invalid attribute name");
        }

        // Test case-insensitivity for NOT_BEFORE
        Date nb2 = cv2.get("NoTbEfOrE");
        if (!nb2.equals(before)) {
            throw new RuntimeException("get() is not case-insensitive for notBefore");
        }

        // Test case-insensitivity for NOT_AFTER
        Date na2 = cv2.get("NoTaFtEr");
        if (!na2.equals(after)) {
            throw new RuntimeException("get() is not case-insensitive for notAfter");
        }

        System.out.println("All tests passed.");
    }
}
