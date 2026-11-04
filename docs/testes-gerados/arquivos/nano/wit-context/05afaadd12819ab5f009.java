/* @test    @summary Test for AuthorityKeyIdentifierExtension.getEncodedKeyIdentifier() method
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.util.DerOutputStream;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEncodedKeyIdentifierWithId();
        testGetEncodedKeyIdentifierNull();
    }

    private static void testGetEncodedKeyIdentifierWithId() throws IOException {
        // Setup: create an AuthorityKeyIdentifierExtension with a dummy KeyIdentifier
        byte[] dummyKeyId = {0x01, 0x02, 0x03, 0x04};
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(
            new sun.security.util.KeyIdentifier(dummyKeyId), null, null);
        byte[] result = ext.getEncodedKeyIdentifier();
        if (result == null || result.length == 0) {
            throw new AssertionError("Expected non-null, non-empty byte array");
        }
        // Verify that the result contains the dummy key id
        boolean contains = false;
        for (byte b : result) {
            for (byte db : dummyKeyId) {
                if (b == db) {
                    contains = true;
                    break;
                }
            }
        }
        if (!contains) {
            throw new AssertionError("Result does not contain expected key identifier bytes");
        }
    }

    private static void testGetEncodedKeyIdentifierNull() throws IOException {
        // Setup: create an AuthorityKeyIdentifierExtension with null id
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(
            (Boolean) null, null);
        byte[] result = ext.getEncodedKeyIdentifier();
        if (result != null) {
            throw new AssertionError("Expected null byte array when id is null");
        }
    }
}
