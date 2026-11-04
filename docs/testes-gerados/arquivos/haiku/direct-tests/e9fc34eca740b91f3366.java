/*
 * @test
 * @summary Verify SubjectKeyIdentifierExtension.get(String) returns the key identifier for KEY_ID (case-insensitive) and throws IOException for unrecognized names
 * @modules java.base/sun.security.x509
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.KeyIdentifier;
import sun.security.x509.SubjectKeyIdentifierExtension;

public class SubjectKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] keyBytes = {1, 2, 3, 4, 5, 6, 7, 8};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyBytes);

        KeyIdentifier id = ext.get("key_id");
        if (id == null) {
            throw new RuntimeException("get(\"key_id\") returned null");
        }
        if (!id.equals(new KeyIdentifier(keyBytes))) {
            throw new RuntimeException("get(\"key_id\") does not match the constructed key identifier");
        }

        if (ext.get("KEY_ID") != id) {
            throw new RuntimeException("get(\"KEY_ID\") did not return the same identifier");
        }
        if (ext.get("Key_Id") != id) {
            throw new RuntimeException("get(\"Key_Id\") did not return the same identifier");
        }

        String[] unknownNames = {"keyid", "", "SubjectKeyIdentifier"};
        for (String name : unknownNames) {
            boolean thrown = false;
            try {
                ext.get(name);
            } catch (IOException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IOException for name: \"" + name + "\"");
            }
        }
    }
}
