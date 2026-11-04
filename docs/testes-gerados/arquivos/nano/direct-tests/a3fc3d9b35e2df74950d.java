/* @test    @summary Test delete method of InhibitAnyPolicyExtension class    @run main InhibitAnyPolicyExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithSkipCertsAttribute();
        testDeleteWithUnrecognizedAttribute();
    }

    private static void testDeleteWithSkipCertsAttribute() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
        try {
            ext.delete("skip_certs");
            throw new AssertionError("Expected IOException for deleting skip_certs attribute");
        } catch (IOException e) {
            if (!e.getMessage().contains("may not be deleted")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }

    private static void testDeleteWithUnrecognizedAttribute() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
        try {
            ext.delete("unknown_attribute");
            throw new AssertionError("Expected IOException for unrecognized attribute");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
