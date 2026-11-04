/*
 * @test
 * @summary Witup-generated checks for AuthorityKeyIdentifierExtension.delete(String)
 * @modules java.base/sun.security.x509
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;

import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] der = {0x30, 0x06, (byte) 0x80, 0x04, 1, 2, 3, 4};
        AuthorityKeyIdentifierExtension ext =
                new AuthorityKeyIdentifierExtension(Boolean.FALSE, der);

        boolean thrown = false;
        try {
            ext.delete("bogus");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for unknown attribute name");
        }

        ext.delete("key_id");
        ext.delete("KEY_ID");
        ext.delete("auth_name");
        ext.delete("Auth_Name");
        ext.delete("serial_number");
        ext.delete("SERIAL_NUMBER");
    }
}
