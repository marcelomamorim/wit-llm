/*
 * @test
 * @summary Verifies CertificateValidity.get(String) returns notBefore/notAfter case-insensitively, returns defensive copies, and throws IOException for unknown names
 * @modules java.base/sun.security.x509
 * @run main CertificateValidityWitupTest
 */

import java.io.IOException;
import java.util.Date;
import sun.security.x509.CertificateValidity;

public class CertificateValidityWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Date before = new Date(1000000000000L);
        Date after = new Date(2000000000000L);
        CertificateValidity cv = new CertificateValidity(before, after);

        check(before.equals(cv.get("notBefore")), "notBefore mismatch");
        check(after.equals(cv.get("notAfter")), "notAfter mismatch");

        check(before.equals(cv.get("NOTBEFORE")), "case-insensitive notBefore mismatch");
        check(after.equals(cv.get("NotAfter")), "case-insensitive notAfter mismatch");
        check(before.equals(cv.get(CertificateValidity.NOT_BEFORE)), "NOT_BEFORE constant mismatch");
        check(after.equals(cv.get(CertificateValidity.NOT_AFTER)), "NOT_AFTER constant mismatch");

        Date returned = cv.get("notBefore");
        returned.setTime(0L);
        check(before.equals(cv.get("notBefore")), "returned notBefore is not a defensive copy");

        Date returnedAfter = cv.get("notAfter");
        returnedAfter.setTime(0L);
        check(after.equals(cv.get("notAfter")), "returned notAfter is not a defensive copy");

        boolean thrown = false;
        try {
            cv.get("unknown");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "unknown attribute name did not throw IOException");

        thrown = false;
        try {
            cv.get("");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "empty attribute name did not throw IOException");
    }
}
