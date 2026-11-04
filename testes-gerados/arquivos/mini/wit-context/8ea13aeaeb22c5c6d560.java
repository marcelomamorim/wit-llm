/*
 * @test
 * @summary Test sun.security.x509.AuthorityKeyIdentifierExtension.delete(String) method
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteValidNames();
        testDeleteInvalidNameThrows();
    }

    private static void testDeleteValidNames() throws Exception {
        // Create with all fields set
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(Boolean.FALSE, new byte[] {1,2,3});

        // Set fields via delete with valid names
        ext.delete(AuthorityKeyIdentifierExtension.KEY_ID);
        ext.delete(AuthorityKeyIdentifierExtension.AUTH_NAME);
        ext.delete(AuthorityKeyIdentifierExtension.SERIAL_NUMBER);
        // If no exception thrown, test passes for valid names
    }

    private static void testDeleteInvalidNameThrows() {
        AuthorityKeyIdentifierExtension ext = null;
        try {
            ext = new AuthorityKeyIdentifierExtension(Boolean.FALSE, new byte[] {1,2,3});
        } catch (IOException e) {
            throw new RuntimeException("Setup failed", e);
        }

        try {
            ext.delete("invalid_name");
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
            // Expected exception caught
        }
    }
}
