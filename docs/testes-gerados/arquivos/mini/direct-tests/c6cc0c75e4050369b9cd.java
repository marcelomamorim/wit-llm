/*
 * @test
 * @summary Test CertificateRevokedException.getExtensions() returns unmodifiable map
 * @run main CertificateRevokedExceptionWitupTest
 */

import java.security.cert.CertificateRevokedException;
import java.security.cert.CRLReason;
import java.security.cert.Extension;
import javax.security.auth.x500.X500Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CertificateRevokedExceptionWitupTest {
    public static void main(String[] args) throws Exception {
        // Prepare test data
        Date revocationDate = new Date(123456789L);
        CRLReason reason = CRLReason.UNSPECIFIED;
        X500Principal authority = new X500Principal("CN=Test Authority");
        Map<String, Extension> extensions = new HashMap<>();

        // Create CertificateRevokedException instance
        CertificateRevokedException ex = new CertificateRevokedException(revocationDate, reason, authority, extensions);

        // getExtensions() should return an unmodifiable map equal to the original map
        Map<String, Extension> returned = ex.getExtensions();
        if (returned == null) {
            throw new RuntimeException("getExtensions() returned null");
        }
        if (!returned.isEmpty()) {
            throw new RuntimeException("getExtensions() returned non-empty map when empty expected");
        }

        // The returned map should be unmodifiable: attempt to add should throw UnsupportedOperationException
        boolean threw = false;
        try {
            returned.put("1.2.3", null);
        } catch (UnsupportedOperationException uoe) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Returned map from getExtensions() is modifiable");
        }

        // The returned map should reflect the original map content if modified before construction
        Map<String, Extension> ext2 = new HashMap<>();
        Extension dummyExt = new Extension(new byte[] {1,2,3});
        ext2.put("1.2.3.4", dummyExt);
        CertificateRevokedException ex2 = new CertificateRevokedException(revocationDate, reason, authority, ext2);
        Map<String, Extension> returned2 = ex2.getExtensions();
        if (returned2.size() != 1 || !returned2.containsKey("1.2.3.4") || returned2.get("1.2.3.4") != dummyExt) {
            throw new RuntimeException("Returned map from getExtensions() does not match expected content");
        }

        // Confirm unmodifiable for second instance as well
        threw = false;
        try {
            returned2.remove("1.2.3.4");
        } catch (UnsupportedOperationException uoe) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Returned map from getExtensions() is modifiable on second instance");
        }
    }
}
