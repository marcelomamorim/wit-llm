/*
 * @test
 * @summary Witup-derived tests for CertificateRevokedException.getExtensions
 * @run main CertificateRevokedExceptionWitupTest
 */

import java.security.cert.CRLReason;
import java.security.cert.CertificateRevokedException;
import java.security.cert.Extension;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.x500.X500Principal;

public class CertificateRevokedExceptionWitupTest {

    public static void main(String[] args) throws Exception {
        Date date = new Date(1000L);
        X500Principal authority = new X500Principal("CN=Test CA");
        Map<String, Extension> source = new HashMap<>();

        CertificateRevokedException e = new CertificateRevokedException(
                date, CRLReason.KEY_COMPROMISE, authority, source);

        Map<String, Extension> got = e.getExtensions();
        check(got != null, "getExtensions returned null");
        check(got.isEmpty(), "expected empty extensions map");

        try {
            got.put("1.2.3", null);
            throw new RuntimeException("returned map must be unmodifiable");
        } catch (UnsupportedOperationException expected) {
        }

        source.put("1.2.3", null);
        check(e.getExtensions().isEmpty(),
                "constructor must copy the extensions map");

        check(e.getExtensions().equals(e.getExtensions()),
                "repeated calls must return equal maps");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
