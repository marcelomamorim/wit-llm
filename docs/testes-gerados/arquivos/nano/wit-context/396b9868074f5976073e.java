/* @test    @summary Test delete method of IssuingDistributionPointExtension for various attribute names    @run main IssuingDistributionPointExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.IssuingDistributionPointExtension;

public class IssuingDistributionPointExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidNames();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidNames() throws IOException {
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(true, null);
        // Test deleting "point"
        ext.delete("point");
        // Test deleting "indirect_crl"
        ext.delete("indirect_crl");
        // Test deleting "reasons"
        ext.delete("reasons");
        // Test deleting "only_user_certs"
        ext.delete("only_user_certs");
        // Test deleting "only_ca_certs"
        ext.delete("only_ca_certs");
        // Test deleting "only_attribute_certs"
        ext.delete("only_attribute_certs");
    }

    private static void testDeleteWithInvalidName() {
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(true, null);
        boolean exceptionThrown = false;
        try {
            ext.delete("invalid_name");
        } catch (IOException e) {
            exceptionThrown = true;
            // Optionally, verify exception message
            if (!e.getMessage().contains("not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException was not thrown for invalid attribute name");
        }
    }
}
