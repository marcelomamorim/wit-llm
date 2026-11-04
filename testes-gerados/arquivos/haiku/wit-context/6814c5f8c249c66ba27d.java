/*
 * @test
 * @summary Witup-derived checks for AuthorityKeyIdentifierExtension.get(String)
 * @modules java.base/sun.security.x509
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        AuthorityKeyIdentifierExtension ext =
            new AuthorityKeyIdentifierExtension(Boolean.FALSE, new byte[] {0x30, 0x00});

        check(ext.get(AuthorityKeyIdentifierExtension.KEY_ID) == null, "key_id");
        check(ext.get(AuthorityKeyIdentifierExtension.AUTH_NAME) == null, "auth_name");
        check(ext.get(AuthorityKeyIdentifierExtension.SERIAL_NUMBER) == null, "serial_number");
        check(ext.get("KEY_ID") == null, "KEY_ID upper case");
        check(ext.get("Auth_Name") == null, "Auth_Name mixed case");
        check(ext.get("SERIAL_NUMBER") == null, "SERIAL_NUMBER upper case");

        String[] unknown = {"unknown", "", "serial", "key"};
        for (String name : unknown) {
            try {
                ext.get(name);
                throw new RuntimeException("Expected IOException for name: " + name);
            } catch (IOException expected) {
                // expected
            }
        }
    }

    private static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Unexpected result for: " + what);
        }
    }
}
