/*
 * @test
 * @summary Test sun.security.x509.PrivateKeyUsageExtension.delete method
 * @run main PrivateKeyUsageExtensionWitupTest
 */

import sun.security.x509.PrivateKeyUsageExtension;
import java.security.cert.CertificateException;
import java.io.IOException;
import java.util.Date;

public class PrivateKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteNotBefore();
        testDeleteNotAfter();
        testDeleteInvalidName();
    }

    private static void testDeleteNotBefore() throws Exception {
        Date before = new Date(1000000000L);
        Date after = new Date(2000000000L);
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before, after);

        ext.delete(PrivateKeyUsageExtension.NOT_BEFORE);

        // After deleting notBefore, notBefore should be null, notAfter unchanged
        // We cannot access fields directly, so we test by deleting notAfter and expect no exception
        ext.delete(PrivateKeyUsageExtension.NOT_AFTER);

        // Now both deleted, deleting again NOT_BEFORE or NOT_AFTER should not throw
        ext.delete(PrivateKeyUsageExtension.NOT_BEFORE);
        ext.delete(PrivateKeyUsageExtension.NOT_AFTER);
    }

    private static void testDeleteNotAfter() throws Exception {
        Date before = new Date(1000000000L);
        Date after = new Date(2000000000L);
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before, after);

        ext.delete(PrivateKeyUsageExtension.NOT_AFTER);

        // After deleting notAfter, notAfter should be null, notBefore unchanged
        // Deleting notBefore should succeed
        ext.delete(PrivateKeyUsageExtension.NOT_BEFORE);
    }

    private static void testDeleteInvalidName() {
        Date before = new Date(1000000000L);
        Date after = new Date(2000000000L);
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before, after);

        try {
            ext.delete("invalid_name");
            throw new RuntimeException("Expected CertificateException not thrown");
        } catch (CertificateException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException thrown", e);
        }
    }
}
