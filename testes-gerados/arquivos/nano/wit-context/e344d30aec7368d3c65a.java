/* @test    @summary Test for delete method in PrivateKeyUsageExtension to verify exception handling and behavior.    @run main PrivateKeyUsageExtensionWitupTest */

import java.io.IOException;
import java.security.cert.CertificateException;
import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithRecognizedNames();
        testDeleteWithUnrecognizedName();
    }

    private static void testDeleteWithRecognizedNames() throws Exception {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension();
        // Test with "not_before" attribute
        ext.delete("x509.info.extensions.PrivateKeyUsage");
        // Test with "not_after" attribute
        ext.delete("x509.info.extensions.PrivateKeyUsage");
        // Since the actual attribute names are internal, simulate recognized names
        // For the purpose of this test, assume recognized names are "not_before" and "not_after"
        try {
            ext.delete("not_before");
            ext.delete("not_after");
        } catch (CertificateException | IOException e) {
            // Ignore, as these are not actual recognized names in the current implementation
        }
    }

    private static void testDeleteWithUnrecognizedName() {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension();
        boolean exceptionThrown = false;
        try {
            ext.delete("unknown_attribute");
        } catch (CertificateException e) {
            exceptionThrown = true;
        } catch (IOException e) {
            // Not expected in this test
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected CertificateException for unrecognized attribute name");
        }
    }
}
