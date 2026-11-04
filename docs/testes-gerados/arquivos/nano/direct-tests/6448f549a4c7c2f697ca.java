/* @test    @summary Test delete method of SubjectKeyIdentifierExtension for known and unknown attribute names    @run main SubjectKeyIdentifierExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.SubjectKeyIdentifierExtension;

public class SubjectKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteKnownAttribute();
        testDeleteUnknownAttribute();
    }

    private static void testDeleteKnownAttribute() throws IOException {
        byte[] keyIdBytes = {1, 2, 3, 4};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyIdBytes);
        ext.delete("key_id");
        // After deletion, id should be null
        if (ext.id != null) {
            throw new AssertionError("Expected id to be null after delete.");
        }
        // Also test delete with different case
        ext = new SubjectKeyIdentifierExtension(keyIdBytes);
        ext.delete("KEY_ID");
        if (ext.id != null) {
            throw new AssertionError("Expected id to be null after delete with uppercase.");
        }
    }

    private static void testDeleteUnknownAttribute() {
        byte[] keyIdBytes = {5, 6, 7, 8};
        SubjectKeyIdentifierExtension ext;
        try {
            ext = new SubjectKeyIdentifierExtension(keyIdBytes);
            ext.delete("unknown_attribute");
            throw new AssertionError("Expected IOException for unknown attribute.");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
