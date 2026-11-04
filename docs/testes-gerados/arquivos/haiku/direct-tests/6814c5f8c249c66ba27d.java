/*
 * @test
 * @summary Verifies AuthorityKeyIdentifierExtension.get(String) lookup names and unrecognized attribute handling
 * @modules java.base/sun.security.x509
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        AuthorityKeyIdentifierExtension ext =
                new AuthorityKeyIdentifierExtension(null, null, null);

        if (ext.get(AuthorityKeyIdentifierExtension.KEY_ID) != null) {
            throw new RuntimeException("key_id should be null when no id is set");
        }
        if (ext.get(AuthorityKeyIdentifierExtension.AUTH_NAME) != null) {
            throw new RuntimeException("auth_name should be null when no names are set");
        }
        if (ext.get(AuthorityKeyIdentifierExtension.SERIAL_NUMBER) != null) {
            throw new RuntimeException("serial_number should be null when no serial is set");
        }

        if (ext.get("KEY_ID") != null || ext.get("Auth_Name") != null) {
            throw new RuntimeException("case-insensitive lookup returned unexpected value");
        }
        if (ext.get("SERIAL_NUMBER") != null) {
            throw new RuntimeException("case-insensitive serial_number lookup failed");
        }

        try {
            ext.get("no_such_attribute");
            throw new RuntimeException("expected IOException for unrecognized name");
        } catch (IOException e) {
            // expected
        }

        try {
            ext.get("");
            throw new RuntimeException("expected IOException for empty name");
        } catch (IOException e) {
            // expected
        }
    }
}
