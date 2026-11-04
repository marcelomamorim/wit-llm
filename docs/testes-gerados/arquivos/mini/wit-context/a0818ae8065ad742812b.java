/*
 * @test
 * @summary Test sun.security.x509.PrivateKeyUsageExtension.get(String) method
 * @run main PrivateKeyUsageExtensionWitupTest
 */

import sun.security.x509.PrivateKeyUsageExtension;
import java.security.cert.CertificateException;
import java.util.Date;

public class PrivateKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetNotBefore();
        testGetNotAfter();
        testGetInvalidName();
    }

    private static void testGetNotBefore() throws Exception {
        Date before = new Date(1000000000L);
        Date after = new Date(2000000000L);
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before, after);
        Date result = ext.get("not_before");
        if (!result.equals(before)) {
            throw new RuntimeException("get(\"not_before\") returned wrong date");
        }
        // Defensive copy check
        if (result == before) {
            throw new RuntimeException("get(\"not_before\") did not return a new Date instance");
        }
    }

    private static void testGetNotAfter() throws Exception {
        Date before = new Date(1000000000L);
        Date after = new Date(2000000000L);
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before, after);
        Date result = ext.get("not_after");
        if (!result.equals(after)) {
            throw new RuntimeException("get(\"not_after\") returned wrong date");
        }
        // Defensive copy check
        if (result == after) {
            throw new RuntimeException("get(\"not_after\") did not return a new Date instance");
        }
    }

    private static void testGetInvalidName() throws Exception {
        Date before = new Date(1000000000L);
        Date after = new Date(2000000000L);
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before, after);
        try {
            ext.get("invalid_name");
            throw new RuntimeException("Expected CertificateException for invalid attribute name");
        } catch (CertificateException e) {
            // Expected exception
        }
    }
}
