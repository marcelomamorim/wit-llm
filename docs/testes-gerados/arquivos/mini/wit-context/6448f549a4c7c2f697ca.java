/*
 * @test
 * @summary Test SubjectKeyIdentifierExtension.delete(String) method behavior
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectKeyIdentifierExtension;

public class SubjectKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithKeyId();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithKeyId() throws Exception {
        byte[] keyId = {1, 2, 3, 4, 5};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyId);
        ext.delete("key_id");
        // After deletion, the id should be null and extensionValue should be null
        // We check that no exception is thrown and delete succeeds silently
    }

    private static void testDeleteWithInvalidName() {
        try {
            byte[] keyId = {1, 2, 3, 4, 5};
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyId);
            ext.delete("invalid_name");
            throw new RuntimeException("Expected IOException was not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
            // Expected exception caught
        }
    }
}
