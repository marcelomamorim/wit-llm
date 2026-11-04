/* @test    @summary Test AuthorityKeyIdentifierExtension.get(String) method for various attribute names.    @run main AuthorityKeyIdentifierExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetMethodWithValidNames();
        testGetMethodWithInvalidName();
    }

    private static void testGetMethodWithValidNames() throws IOException {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        Object resultId = ext.get("key_id");
        Object resultNames = ext.get("auth_name");
        Object resultSerial = ext.get("serial_number");
        assert resultId != null : "Expected non-null for key_id";
        assert resultNames != null : "Expected non-null for auth_name";
        assert resultSerial != null : "Expected non-null for serial_number";
    }

    private static void testGetMethodWithInvalidName() {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        try {
            ext.get("invalid_name");
            assert false : "Expected IOException for unrecognized attribute name";
        } catch (IOException e) {
            assert e.getMessage().contains("Attribute name not recognized") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
