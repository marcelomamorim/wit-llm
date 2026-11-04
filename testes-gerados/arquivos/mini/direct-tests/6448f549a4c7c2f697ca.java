/*
 * @test
 * @summary Test SubjectKeyIdentifierExtension.delete(String) method behavior
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import sun.security.x509.SubjectKeyIdentifierExtension;
import java.io.IOException;

public class SubjectKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidKeyId();
        testDeleteWithInvalidNameThrows();
    }

    private static void testDeleteWithValidKeyId() throws Exception {
        byte[] keyId = new byte[] {1, 2, 3, 4, 5};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyId);

        // Delete with correct key name (case insensitive)
        ext.delete("key_id");
        // After deletion, the extensionValue should be null (id == null)
        if (ext.extensionValue != null) {
            throw new RuntimeException("extensionValue should be null after delete with key_id");
        }

        // Recreate and delete with uppercase name
        ext = new SubjectKeyIdentifierExtension(keyId);
        ext.delete("KEY_ID");
        if (ext.extensionValue != null) {
            throw new RuntimeException("extensionValue should be null after delete with KEY_ID");
        }
    }

    private static void testDeleteWithInvalidNameThrows() {
        byte[] keyId = new byte[] {1, 2, 3, 4, 5};
        SubjectKeyIdentifierExtension ext = null;
        try {
            ext = new SubjectKeyIdentifierExtension(keyId);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException during construction", e);
        }

        try {
            ext.delete("invalid_name");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
