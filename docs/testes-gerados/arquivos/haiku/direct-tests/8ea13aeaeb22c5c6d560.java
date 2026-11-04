/*
 * @test
 * @summary Verifies AuthorityKeyIdentifierExtension.delete accepts known attribute names case-insensitively, rejects unknown names with IOException, and rejects null names
 * @modules java.base/sun.security.x509
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        AuthorityKeyIdentifierExtension ext =
                new AuthorityKeyIdentifierExtension(null, null, null);

        boolean threw = false;
        String msg = null;
        try {
            ext.delete("unknown_attribute");
        } catch (IOException e) {
            threw = true;
            msg = e.getMessage();
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException for unknown attribute name");
        }
        if (msg == null || !msg.contains("Attribute name not recognized")) {
            throw new RuntimeException("Unexpected message: " + msg);
        }

        ext.delete(AuthorityKeyIdentifierExtension.KEY_ID);
        ext.delete("KEY_ID");
        ext.delete("key_id");
        ext.delete("Auth_Name");
        ext.delete(AuthorityKeyIdentifierExtension.AUTH_NAME);
        ext.delete("SERIAL_NUMBER");
        ext.delete(AuthorityKeyIdentifierExtension.SERIAL_NUMBER);

        boolean npe = false;
        try {
            ext.delete(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for null name");
        }
    }
}
