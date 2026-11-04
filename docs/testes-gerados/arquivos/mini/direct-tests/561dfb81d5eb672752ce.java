/*
 * @test
 * @summary Test sun.security.x509.SubjectKeyIdentifierExtension.set method
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import sun.security.x509.SubjectKeyIdentifierExtension;
import sun.security.x509.KeyIdentifier;
import java.io.IOException;

public class SubjectKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidKeyIdentifier();
        testSetWithInvalidObject();
        testSetWithInvalidName();
    }

    private static void testSetWithValidKeyIdentifier() throws Exception {
        byte[] keyIdBytes = {1, 2, 3, 4, 5};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyIdBytes);
        KeyIdentifier newKeyId = new KeyIdentifier(new byte[] {10, 20, 30});
        ext.set(SubjectKeyIdentifierExtension.KEY_ID, newKeyId);
        // No exception means success
    }

    private static void testSetWithInvalidObject() {
        try {
            byte[] keyIdBytes = {1, 2, 3, 4, 5};
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyIdBytes);
            ext.set(SubjectKeyIdentifierExtension.KEY_ID, "not a KeyIdentifier");
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute value should be of type KeyIdentifier.")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidName() {
        try {
            byte[] keyIdBytes = {1, 2, 3, 4, 5};
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyIdBytes);
            ext.set("invalidName", new KeyIdentifier(new byte[] {1}));
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized by CertAttrSet:SubjectKeyIdentifierExtension.")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
