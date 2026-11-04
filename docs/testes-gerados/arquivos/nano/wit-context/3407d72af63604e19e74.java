/* @test    @summary Test for CertificateValidity.delete(String) method to verify exception handling and behavior.    @run main CertificateValidityWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateValidity;

public class CertificateValidityWitupTest {

    public static void main(String[] args) {
        testDeleteWithValidNotBefore();
        testDeleteWithValidNotAfter();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidNotBefore() {
        try {
            CertificateValidity cv = new CertificateValidity();
            // Set notBefore to a non-null value
            cv.notBefore = new java.util.Date();
            // Call delete with "notBefore"
            cv.delete("notBefore");
            assert cv.getNotBefore() == null : "notBefore should be null after delete";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in delete with notBefore", e);
        }
    }

    private static void testDeleteWithValidNotAfter() {
        try {
            CertificateValidity cv = new CertificateValidity();
            // Set notAfter to a non-null value
            cv.notAfter = new java.util.Date();
            // Call delete with "notAfter"
            cv.delete("notAfter");
            assert cv.getNotAfter() == null : "notAfter should be null after delete";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in delete with notAfter", e);
        }
    }

    private static void testDeleteWithInvalidName() {
        try {
            CertificateValidity cv = new CertificateValidity();
            // Call delete with an unrecognized name
            cv.delete("unknownAttribute");
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            // Expected exception
            assert e.getMessage().contains("Attribute name not recognized") : "Exception message mismatch";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
