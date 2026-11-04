/*
 * @test
 * @summary Test sun.security.x509.CertificateValidity.get(String) method
 * @run main CertificateValidityWitupTest
 */

import sun.security.x509.CertificateValidity;
import java.util.Date;
import java.io.IOException;

public class CertificateValidityWitupTest {
    public static void main(String[] args) throws Exception {
        testGetNotBefore();
        testGetNotAfter();
        testGetInvalidName();
    }

    private static void testGetNotBefore() throws Exception {
        Date before = new Date(1000000000L);
        Date after = new Date(2000000000L);
        CertificateValidity cv = new CertificateValidity(before, after);
        Date result = cv.get("notBefore");
        if (!result.equals(before)) {
            throw new RuntimeException("get(\"notBefore\") returned wrong date");
        }
        // Check defensive copy
        if (result == before) {
            throw new RuntimeException("get(\"notBefore\") did not return a new Date instance");
        }
    }

    private static void testGetNotAfter() throws Exception {
        Date before = new Date(1000000000L);
        Date after = new Date(2000000000L);
        CertificateValidity cv = new CertificateValidity(before, after);
        Date result = cv.get("notAfter");
        if (!result.equals(after)) {
            throw new RuntimeException("get(\"notAfter\") returned wrong date");
        }
        // Check defensive copy
        if (result == after) {
            throw new RuntimeException("get(\"notAfter\") did not return a new Date instance");
        }
    }

    private static void testGetInvalidName() {
        Date before = new Date(1000000000L);
        Date after = new Date(2000000000L);
        CertificateValidity cv = new CertificateValidity(before, after);
        try {
            cv.get("invalidName");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
