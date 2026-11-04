/*
 * @test
 * @summary Verify PrivateKeyUsageExtension.get(String) returns defensive copies of notBefore/notAfter, matches names case-insensitively, and rejects unknown names
 * @modules java.base/sun.security.x509
 * @run main PrivateKeyUsageExtensionWitupTest
 */

import java.security.cert.CertificateException;
import java.util.Date;

import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Date before = new Date(1000L);
        Date after = new Date(2000L);
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before, after);

        Date gotBefore = ext.get(PrivateKeyUsageExtension.NOT_BEFORE);
        check(gotBefore.getTime() == 1000L, "notBefore mismatch: " + gotBefore.getTime());

        Date gotAfter = ext.get(PrivateKeyUsageExtension.NOT_AFTER);
        check(gotAfter.getTime() == 2000L, "notAfter mismatch: " + gotAfter.getTime());

        Date upper = ext.get("NOT_BEFORE");
        check(upper.getTime() == 1000L, "case-insensitive NOT_BEFORE failed");

        Date mixed = ext.get("Not_After");
        check(mixed.getTime() == 2000L, "case-insensitive Not_After failed");

        gotBefore.setTime(9999L);
        Date again = ext.get(PrivateKeyUsageExtension.NOT_BEFORE);
        check(again.getTime() == 1000L, "returned Date is not a defensive copy");

        gotAfter.setTime(9999L);
        Date againAfter = ext.get(PrivateKeyUsageExtension.NOT_AFTER);
        check(againAfter.getTime() == 2000L, "returned notAfter is not a defensive copy");

        boolean thrown = false;
        try {
            ext.get("unknown_attribute");
        } catch (CertificateException e) {
            thrown = true;
        }
        check(thrown, "unknown attribute name should throw CertificateException");

        thrown = false;
        try {
            ext.get("");
        } catch (CertificateException e) {
            thrown = true;
        }
        check(thrown, "empty attribute name should throw CertificateException");
    }
}
