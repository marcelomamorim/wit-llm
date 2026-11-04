/*
 * @test
 * @summary Test for CertificateValidity.get(String) method behavior.
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
        // Setup: create CertificateValidity with known notBefore
        Date notBeforeDate = new Date(1622505600000L); // 2021-06-01
        Date notAfterDate = new Date(1654041600000L); // 2022-06-01
        CertificateValidity cv = new CertificateValidity();
        setPrivateField(cv, "notBefore", notBeforeDate);
        setPrivateField(cv, "notAfter", notAfterDate);
        // Test: get "notBefore"
        Date result = cv.get("notBefore");
        if (!result.equals(notBeforeDate)) {
            throw new AssertionError("Expected notBefore date does not match.");
        }
    }

    private static void testGetNotAfter() throws Exception {
        // Setup: create CertificateValidity with known notAfter
        Date notBeforeDate = new Date(1622505600000L); // 2021-06-01
        Date notAfterDate = new Date(1654041600000L); // 2022-06-01
        CertificateValidity cv = new CertificateValidity();
        setPrivateField(cv, "notBefore", notBeforeDate);
        setPrivateField(cv, "notAfter", notAfterDate);
        // Test: get "notAfter"
        Date result = cv.get("notAfter");
        if (!result.equals(notAfterDate)) {
            throw new AssertionError("Expected notAfter date does not match.");
        }
    }

    private static void testGetInvalidName() throws Exception {
        // Setup: create CertificateValidity
        Date notBeforeDate = new Date(1622505600000L);
        Date notAfterDate = new Date(1654041600000L);
        CertificateValidity cv = new CertificateValidity();
        setPrivateField(cv, "notBefore", notBeforeDate);
        setPrivateField(cv, "notAfter", notAfterDate);
        // Test: get with invalid name, expect IOException
        boolean thrown = false;
        try {
            cv.get("invalidName");
        } catch (IOException e) {
            thrown = true;
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message.");
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException was not thrown.");
        }
    }

    private static void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
}
