/*
 * @test
 * @summary Verify public delete(String) behavior of PrivateKeyUsageExtension for recognized and unrecognized attribute names
 * @modules java.base/sun.security.x509
 * @run main PrivateKeyUsageExtensionWitupTest
 */

import java.security.cert.CertificateException;
import java.util.Date;
import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        Date notBefore = new Date(1000000000000L);
        Date notAfter = new Date(2000000000000L);

        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(notBefore, notAfter);
        ext.delete("not_before");
        ext.delete("not_after");

        PrivateKeyUsageExtension ext2 = new PrivateKeyUsageExtension(notBefore, notAfter);
        ext2.delete("NOT_AFTER");

        PrivateKeyUsageExtension ext3 = new PrivateKeyUsageExtension(notBefore, notAfter);
        ext3.delete("Not_Before");

        PrivateKeyUsageExtension ext4 = new PrivateKeyUsageExtension(notBefore, notAfter);
        try {
            ext4.delete("bogus");
            throw new RuntimeException("Expected CertificateException for unrecognized name");
        } catch (CertificateException expected) {
        }

        PrivateKeyUsageExtension ext5 = new PrivateKeyUsageExtension(notBefore, notAfter);
        try {
            ext5.delete("");
            throw new RuntimeException("Expected CertificateException for empty name");
        } catch (CertificateException expected) {
        }
    }
}
