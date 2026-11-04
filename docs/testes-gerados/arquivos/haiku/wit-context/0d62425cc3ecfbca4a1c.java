/*
 * @test
 * @summary Witup-generated tests for CertificateValidity.get(String)
 * @modules java.base/sun.security.x509
 * @run main CertificateValidityWitupTest
 */

import java.io.IOException;
import java.util.Date;
import sun.security.x509.CertificateValidity;

public class CertificateValidityWitupTest {
    private static final long NB = 1000000000000L;
    private static final long NA = 1500000000000L;

    public static void main(String[] args) throws Exception {
        testGetNotBefore();
        testGetNotAfterCaseInsensitive();
        testUnknownNameThrows();
        testEmptyNameThrows();
        testReturnedDateIsCopy();
    }

    private static CertificateValidity newValidity() {
        return new CertificateValidity(new Date(NB), new Date(NA));
    }

    private static void testGetNotBefore() throws IOException {
        CertificateValidity cv = newValidity();
        check(cv.get("notBefore").getTime() == NB, "notBefore value mismatch");
    }

    private static void testGetNotAfterCaseInsensitive() throws IOException {
        CertificateValidity cv = newValidity();
        check(cv.get("notAfter").getTime() == NA, "notAfter value mismatch");
        check(cv.get("NOTAFTER").getTime() == NA, "case-insensitive notAfter mismatch");
        check(cv.get("NotBefore").getTime() == NB, "case-insensitive notBefore mismatch");
    }

    private static void testUnknownNameThrows() {
        CertificateValidity cv = newValidity();
        try {
            cv.get("bogus");
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for unknown name");
    }

    private static void testEmptyNameThrows() {
        CertificateValidity cv = newValidity();
        try {
            cv.get("");
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for empty name");
    }

    private static void testReturnedDateIsCopy() throws IOException {
        CertificateValidity cv = newValidity();
        Date d = cv.get("notBefore");
        d.setTime(0L);
        check(cv.get("notBefore").getTime() == NB, "returned date is not a defensive copy");
    }

    private static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }
}
