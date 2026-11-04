/*
 * @test
 * @summary Verify that CertificateRevokedException.getExtensions() returns an unmodifiable, defensively copied view
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
        testEmptyExtensions();
        testUnmodifiable();
        testDefensiveCopy();
        testNullExtensionsRejected();
    }

    static CertificateRevokedException create(Map<String, Extension> ext) {
        return new CertificateRevokedException(new Date(0L),
                CRLReason.KEY_COMPROMISE,
                new X500Principal("CN=Test CA"),
                ext);
    }

    static void testEmptyExtensions() {
        CertificateRevokedException e = create(new HashMap<String, Extension>());
        Map<String, Extension> m = e.getExtensions();
        if (m == null) {
            throw new RuntimeException("getExtensions() returned null");
        }
        if (!m.isEmpty()) {
            throw new RuntimeException("Expected empty map, size=" + m.size());
        }
    }

    static void testUnmodifiable() {
        CertificateRevokedException e = create(new HashMap<String, Extension>());
        Map<String, Extension> m = e.getExtensions();
        try {
            m.put("1.2.3", null);
            throw new RuntimeException("Map returned by getExtensions() is modifiable");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testDefensiveCopy() {
        Map<String, Extension> src = new HashMap<String, Extension>();
        CertificateRevokedException e = create(src);
        src.put("1.2.3", null);
        Map<String, Extension> m = e.getExtensions();
        if (!m.isEmpty()) {
            throw new RuntimeException("Exception map was affected by later source mutation");
        }
    }

    static void testNullExtensionsRejected() {
        try {
            create(null);
            throw new RuntimeException("Expected NullPointerException for null extensions");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
