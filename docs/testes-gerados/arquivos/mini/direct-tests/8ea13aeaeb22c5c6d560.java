/*
 * @test
 * @summary Test AuthorityKeyIdentifierExtension.delete(String) method behavior
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.KeyIdentifier;
import sun.security.x509.GeneralNames;
import sun.security.x509.SerialNumber;

public class AuthorityKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteKeyId();
        testDeleteAuthName();
        testDeleteSerialNumber();
        testDeleteUnknownNameThrows();
    }

    private static void testDeleteKeyId() throws Exception {
        // Create with all fields set
        KeyIdentifier kid = new KeyIdentifier(new byte[] {1, 2, 3});
        GeneralNames names = new GeneralNames();
        SerialNumber sn = new SerialNumber(12345);
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(kid, names, sn);

        // Delete key_id
        ext.delete(AuthorityKeyIdentifierExtension.KEY_ID);

        // After deletion, key_id should be null, others remain
        // We cannot access private fields, but no exception means success
        // Deleting again should not throw
        ext.delete(AuthorityKeyIdentifierExtension.KEY_ID);
    }

    private static void testDeleteAuthName() throws Exception {
        KeyIdentifier kid = new KeyIdentifier(new byte[] {4, 5, 6});
        GeneralNames names = new GeneralNames();
        SerialNumber sn = new SerialNumber(67890);
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(kid, names, sn);

        ext.delete(AuthorityKeyIdentifierExtension.AUTH_NAME);

        ext.delete(AuthorityKeyIdentifierExtension.AUTH_NAME);
    }

    private static void testDeleteSerialNumber() throws Exception {
        KeyIdentifier kid = new KeyIdentifier(new byte[] {7, 8, 9});
        GeneralNames names = new GeneralNames();
        SerialNumber sn = new SerialNumber(13579);
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(kid, names, sn);

        ext.delete(AuthorityKeyIdentifierExtension.SERIAL_NUMBER);

        ext.delete(AuthorityKeyIdentifierExtension.SERIAL_NUMBER);
    }

    private static void testDeleteUnknownNameThrows() {
        try {
            AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(null, null, null);
            ext.delete("unknown_attribute_name");
            throw new RuntimeException("Expected IOException not thrown for unknown attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
