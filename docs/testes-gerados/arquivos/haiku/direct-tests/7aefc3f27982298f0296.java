/*
 * @test
 * @summary Verifies the byte[] nonce constructor of OCSPNonceExtension produces a non-critical extension wrapping the supplied nonce
 * @modules java.base/sun.security.provider.certpath java.base/sun.security.x509 java.base/sun.security.util
 * @run main OCSPNonceExtensionWitupTest
 */

import java.util.Arrays;

import sun.security.provider.certpath.OCSPNonceExtension;
import sun.security.util.DerValue;
import sun.security.x509.PKIXExtensions;

public class OCSPNonceExtensionWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] nonce = new byte[] {1, 2, 3, 4, 5, 6, 7, 8};

        OCSPNonceExtension ext = new OCSPNonceExtension(nonce);
        check(!ext.isCritical(), "byte[] constructor must produce non-critical extension");
        check(PKIXExtensions.OCSPNonce_Id.equals(ext.getExtensionId()),
                "extension id must be OCSPNonce");

        byte[] value = ext.getExtensionValue();
        check(value != null, "extension value must not be null");
        DerValue wrapped = new DerValue(value);
        check(Arrays.equals(nonce, wrapped.getOctetString()),
                "extension value must wrap the incoming nonce");

        OCSPNonceExtension critical = new OCSPNonceExtension(true, nonce);
        check(critical.isCritical(), "two-argument byte[] constructor must honor isCritical=true");
        check(Arrays.equals(value, critical.getExtensionValue()),
                "same nonce must produce same extension value");
    }
}
