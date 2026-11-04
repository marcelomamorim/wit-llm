/* @test    @summary Test for SubjectKeyIdentifierExtension.get(String) method to verify behavior with valid and invalid attribute names.    @run main SubjectKeyIdentifierExtensionWitupTest */

import sun.security.x509.SubjectKeyIdentifierExtension;
import sun.security.util.KeyIdentifier;
import java.io.IOException;

public class SubjectKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidKey();
        testGetWithInvalidKey();
    }

    private static void testGetWithValidKey() throws IOException {
        byte[] keyBytes = {1, 2, 3, 4, 5};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyBytes);
        KeyIdentifier result = ext.get("key_id");
        if (result == null) {
            throw new AssertionError("Expected non-null KeyIdentifier for 'key_id'");
        }
        System.out.println("testGetWithValidKey passed.");
    }

    private static void testGetWithInvalidKey() {
        byte[] keyBytes = {1, 2, 3, 4, 5};
        try {
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyBytes);
            ext.get("invalid_attribute");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String expectedMessage = "Attribute name not recognized by " +
                "CertAttrSet:SubjectKeyIdentifierExtension.";
            if (!e.getMessage().equals(expectedMessage)) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            System.out.println("testGetWithInvalidKey passed.");
        }
    }
}
