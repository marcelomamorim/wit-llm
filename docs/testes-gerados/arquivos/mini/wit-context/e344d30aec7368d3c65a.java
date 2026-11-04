/*
 * @test
 * @summary Test sun.security.x509.PrivateKeyUsageExtension.delete(String) method
 * @run main PrivateKeyUsageExtensionWitupTest
 */

import sun.security.x509.PrivateKeyUsageExtension;
import java.security.cert.CertificateException;
import java.io.IOException;
import java.util.Date;

public class PrivateKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteValidNames();
        testDeleteInvalidNameThrows();
    }

    private static void testDeleteValidNames() throws Exception {
        Date before = new Date(1000000000L); // arbitrary date
        Date after = new Date(2000000000L);  // arbitrary date
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before, after);

        // Delete NOT_BEFORE
        ext.delete(PrivateKeyUsageExtension.NOT_BEFORE);
        // After deletion, deleting NOT_BEFORE again should not throw, but keep null
        ext.delete(PrivateKeyUsageExtension.NOT_BEFORE);

        // Delete NOT_AFTER
        ext.delete(PrivateKeyUsageExtension.NOT_AFTER);
        // After deletion, deleting NOT_AFTER again should not throw, but keep null
        ext.delete(PrivateKeyUsageExtension.NOT_AFTER);
    }

    private static void testDeleteInvalidNameThrows() throws Exception {
        Date before = new Date(1000000000L);
        Date after = new Date(2000000000L);
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before, after);

        boolean thrown = false;
        try {
            ext.delete("invalid_name");
        } catch (CertificateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected CertificateException not thrown for invalid attribute name");
        }
    }
}
