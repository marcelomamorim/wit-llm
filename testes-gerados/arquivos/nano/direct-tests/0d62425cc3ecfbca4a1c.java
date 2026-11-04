/* @test    @summary Test CertificateValidity.get() method for valid and invalid attribute names    @run main CertificateValidityWitupTest */

import java.io.IOException;
import java.util.Date;
import sun.security.x509.CertificateValidity;

public class CertificateValidityWitupTest {

    public static void main(String[] args) throws Exception {
        testGetValidAttribute("notBefore", true);
        testGetValidAttribute("notAfter", false);
        testGetInvalidAttribute();
    }

    private static void testGetValidAttribute(String attributeName, boolean isNotBefore) throws Exception {
        CertificateValidity cv = new CertificateValidity();
        Date date = new Date();
        if (isNotBefore) {
            setPrivateField(cv, "notBefore", date);
        } else {
            setPrivateField(cv, "notAfter", date);
        }
        Date result = cv.get(attributeName);
        if (result != date) {
            throw new AssertionError("Failed for attribute: " + attributeName);
        }
        System.out.println("Passed get() test for attribute: " + attributeName);
    }

    private static void testGetInvalidAttribute() {
        CertificateValidity cv = new CertificateValidity();
        try {
            cv.get("invalidAttribute");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
            System.out.println("Passed invalid attribute test with expected exception.");
        }
    }

    private static void setPrivateField(CertificateValidity cv, String fieldName, Date value) throws Exception {
        java.lang.reflect.Field field = CertificateValidity.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(cv, value);
    }
}
