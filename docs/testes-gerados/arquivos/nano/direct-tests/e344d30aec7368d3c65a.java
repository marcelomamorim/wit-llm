/* @test    @summary Test delete method of PrivateKeyUsageExtension class    @run main PrivateKeyUsageExtensionWitupTest */

import java.io.IOException;
import java.security.cert.CertificateException;
import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteNotBefore();
        testDeleteNotAfter();
        testDeleteInvalidAttribute();
    }

    static void testDeleteNotBefore() throws Exception {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension();
        ext.delete("not_before");
        if (ext.notBefore != null) {
            throw new AssertionError("Expected notBefore to be null after delete") ;
        }
    }

    static void testDeleteNotAfter() throws Exception {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension();
        ext.delete("not_after");
        if (ext.notAfter != null) {
            throw new AssertionError("Expected notAfter to be null after delete") ;
        }
    }

    static void testDeleteInvalidAttribute() {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension();
        boolean thrown = false;
        try {
            ext.delete("invalid_attribute");
        } catch (CertificateException | IOException e) {
            thrown = true;
            if (!(e instanceof CertificateException)) {
                throw e;
            }
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected exception for invalid attribute") ;
        }
    }
}
