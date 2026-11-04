/*
 * @test
 * @summary Verifies that InhibitAnyPolicyExtension.delete always throws IOException with the expected message
 * @modules java.base/sun.security.x509
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.IOException;

import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(1);

        checkDelete(ext, "skip_certs",
                "Attribute skip_certs may not be deleted.");
        checkDelete(ext, "SKIP_CERTS",
                "Attribute skip_certs may not be deleted.");
        checkDelete(ext, "Skip_Certs",
                "Attribute skip_certs may not be deleted.");
        checkDelete(ext, "other",
                "Attribute name not recognized by CertAttrSet:InhibitAnyPolicy.");
        checkDelete(ext, "",
                "Attribute name not recognized by CertAttrSet:InhibitAnyPolicy.");
    }

    private static void checkDelete(InhibitAnyPolicyExtension ext, String name,
            String expectedMessage) {
        try {
            ext.delete(name);
            throw new RuntimeException("No IOException thrown for name: " + name);
        } catch (IOException e) {
            if (!expectedMessage.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for name " + name
                        + ": " + e.getMessage());
            }
        }
    }
}
