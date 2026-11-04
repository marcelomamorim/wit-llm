/*
 * @test
 * @summary Test CertificateRevokedException.getExtensions() behavior
 * @run main CertificateRevokedExceptionWitupTest
 */

import java.security.cert.CertificateRevokedException;
import java.security.cert.CRLReason;
import java.security.cert.Extension;
import javax.security.auth.x500.X500Principal;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class CertificateRevokedExceptionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetExtensionsReturnsUnmodifiableMap();
        testGetExtensionsThrowsNPEWhenExtensionsNull();
    }

    private static void testGetExtensionsReturnsUnmodifiableMap() throws Exception {
        Date revocationDate = new Date(123456789L);
        CRLReason reason = CRLReason.UNSPECIFIED;
        X500Principal authority = new X500Principal("CN=Test Authority");
        Map<String, Extension> extensions = new HashMap<>();

        CertificateRevokedException ex = new CertificateRevokedException(revocationDate, reason, authority, extensions);

        Map<String, Extension> returned = ex.getExtensions();
        if (returned == null) {
            throw new RuntimeException("getExtensions() returned null");
        }

        // The returned map should be unmodifiable: attempt to modify should throw
        boolean threw = false;
        try {
            returned.put("1.2.3", null);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("getExtensions() returned modifiable map");
        }
    }

    private static void testGetExtensionsThrowsNPEWhenExtensionsNull() {
        Date revocationDate = new Date(123456789L);
        CRLReason reason = CRLReason.UNSPECIFIED;
        X500Principal authority = new X500Principal("CN=Test Authority");

        try {
            new CertificateRevokedException(revocationDate, reason, authority, null);
            throw new RuntimeException("Constructor did not throw NullPointerException when extensions is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
