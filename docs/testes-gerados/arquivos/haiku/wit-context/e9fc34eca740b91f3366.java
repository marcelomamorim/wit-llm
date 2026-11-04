/*
 * @test
 * @summary Witup generated test for SubjectKeyIdentifierExtension.get(String)
 * @modules java.base/sun.security.x509
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectKeyIdentifierExtension;

public class SubjectKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        byte[] octets = {1, 2, 3, 4};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(octets);

        Object first = ext.get("key_id");
        if (first == null) {
            throw new RuntimeException("get(\"key_id\") returned null");
        }

        Object second = ext.get("KEY_ID");
        if (first != second) {
            throw new RuntimeException("get is not case-insensitive for key_id");
        }

        String[] invalidNames = {"other", "SubjectKeyIdentifier", ""};
        for (String name : invalidNames) {
            boolean thrown = false;
            try {
                ext.get(name);
            } catch (IOException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("expected IOException for name: " + name);
            }
        }
    }
}
