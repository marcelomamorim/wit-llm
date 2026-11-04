/*
 * @test
 * @summary Test sun.security.x509.SubjectKeyIdentifierExtension.set(String,Object) method
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectKeyIdentifierExtension;
import sun.security.x509.KeyIdentifier;

public class SubjectKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidKeyId();
        testSetWithInvalidName();
        testSetWithInvalidObjectType();
    }

    private static void testSetWithValidKeyId() throws Exception {
        byte[] keyIdBytes = new byte[] {1, 2, 3, 4, 5};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyIdBytes);
        KeyIdentifier newKeyId = new KeyIdentifier(new byte[] {5, 4, 3, 2, 1});
        ext.set("key_id", newKeyId);
        // If no exception, test passes
    }

    private static void testSetWithInvalidName() {
        try {
            byte[] keyIdBytes = new byte[] {1, 2, 3};
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyIdBytes);
            ext.set("invalid_name", new KeyIdentifier(new byte[] {1}));
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidObjectType() {
        try {
            byte[] keyIdBytes = new byte[] {1, 2, 3};
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyIdBytes);
            ext.set("key_id", "not a KeyIdentifier");
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute value should be of type KeyIdentifier")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
