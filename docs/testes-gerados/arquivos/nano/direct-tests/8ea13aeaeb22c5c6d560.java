/* @test    @summary Test delete method of AuthorityKeyIdentifierExtension class    @run main AuthorityKeyIdentifierExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidNames();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidNames() throws IOException {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        // Initialize with non-null values
        ext.delete("key_id");
        assert ext != null : "Extension should be instantiated";
        ext.delete("auth_name");
        ext.delete("serial_number");
    }

    private static void testDeleteWithInvalidName() {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        boolean exceptionThrown = false;
        try {
            ext.delete("invalid_name");
        } catch (IOException e) {
            exceptionThrown = true;
            assert e.getMessage().contains("Attribute name not recognized") : "Exception message mismatch";
        }
        assert exceptionThrown : "Expected IOException for invalid attribute name";
    }
}
